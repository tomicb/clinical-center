package com.ftn.clinicCentre.controller;

import com.ftn.clinicCentre.dto.UserDTO;
import com.ftn.clinicCentre.entity.EUserStatus;
import com.ftn.clinicCentre.entity.User;
import com.ftn.clinicCentre.security.token.ConfirmationToken;
import com.ftn.clinicCentre.service.ConfirmationTokenService;
import com.ftn.clinicCentre.service.EmailSenderService;
import com.ftn.clinicCentre.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin
@RestController
@RequestMapping(value = "api/users")
public class UserController {
	
	@Autowired
	private ConfirmationTokenService confirmationTokenService;
	
	@Autowired
	private EmailSenderService emailSenderService;

    @Autowired
    private UserService userService;
	

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }


    @PreAuthorize("hasRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
    @GetMapping(value = "/requests")
    public ResponseEntity<List<UserDTO>> findUsersRequests(){

        List<User> users = userService.findUsersByStatus(EUserStatus.PENDING);

        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user: users) {
            userDTOs.add(new UserDTO(user));
        }

        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
    @PutMapping(value = "/requests/approve")
    public ResponseEntity<UserDTO> approveUserRequest(@RequestBody UserDTO userDTO) {

        User user = userService.findUserById(userDTO.getId());
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(user.getStatus() != EUserStatus.PENDING) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        ConfirmationToken ct = confirmationTokenService.findByUser(user);
        System.out.println(ct);
        
        String link = "https://localhost:8080/api/patients/confirm?token=" + ct.getToken();
		emailSenderService.send(userDTO.getEmail(), emailSenderService.registerEmail(userDTO.getFirstName(), link), "Verify your email");
	

        user.setStatus(EUserStatus.NOT_VERIFIED);
        userDTO.setStatus(EUserStatus.NOT_VERIFIED);
        userService.save(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
    @PutMapping(value = "/requests/reject")
    public ResponseEntity<UserDTO> rejectUserRequest(@RequestBody UserDTO userDTO) {

        User user = userService.findUserById(userDTO.getId());
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(user.getStatus() != EUserStatus.PENDING) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setStatus(EUserStatus.REJECTED);
        userDTO.setStatus(EUserStatus.REJECTED);
        userService.save(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR', 'ROLE_CLINIC_ADMINISTRATOR')")
    @PutMapping(value = "/block/{jmbg}")
    public ResponseEntity<UserDTO> blockUser(@PathVariable("jmbg") String jmbg){
    	
    	User user = userService.findUserByJmbg(jmbg);
    	if(user == null) {
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        }
    	user.setStatus(EUserStatus.BLOCKED);
    	userService.save(user);
    	return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK);
    	
    }
}

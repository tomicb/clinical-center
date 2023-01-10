package com.ftn.clinicCentre.controller;

import com.ftn.clinicCentre.entity.EUserStatus;
import com.ftn.clinicCentre.entity.User;
import com.ftn.clinicCentre.security.request.JwtAuthenticationRequest;
import com.ftn.clinicCentre.security.token.PasswordlessToken;
import com.ftn.clinicCentre.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ftn.clinicCentre.dto.ChangePasswordDTO;
import com.ftn.clinicCentre.dto.PatientRegistrationDTO;
import com.ftn.clinicCentre.security.token.TokenUtils;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private PasswordlessTokenService passwordlessTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody JwtAuthenticationRequest jwtAuthenticationRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                jwtAuthenticationRequest.getEmail(), jwtAuthenticationRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthenticationRequest.getEmail());
            User user = userService.findUserByEmail(jwtAuthenticationRequest.getEmail());

            Map<String, String> responseData = new HashMap<>();
            if(user.getStatus() != EUserStatus.APPROVED && user.getStatus() != EUserStatus.FIRST_LOGGIN) {
                responseData.put("status", user.getStatus().toString());
                return new ResponseEntity<>(responseData, HttpStatus.FORBIDDEN);
            }

            String accessToken = tokenUtils.generateAccessToken(userDetails);
            String refreshToken = tokenUtils.generateRefreshToken(userDetails);
            responseData.put("accessToken", accessToken);
            responseData.put("refreshToken", refreshToken);

            if(user.getStatus() == EUserStatus.FIRST_LOGGIN)
                return new ResponseEntity<>(responseData, HttpStatus.NOT_ACCEPTABLE);

            return new ResponseEntity<>(responseData, HttpStatus.CREATED);

        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DisabledException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/login/passwordless")
    public ResponseEntity<Map<String, String>> passwordlessLogin(@RequestBody JwtAuthenticationRequest jwtAuthenticationRequest) {

        String email = jwtAuthenticationRequest.getEmail();

        User user = userService.findUserByEmail(email);
        if(user != null) {
            PasswordlessToken passwordlessToken = passwordlessTokenService.findUnexpiredByUser(user);
            if(passwordlessToken == null) {
                try {
                    UserDetails userDetails =  userDetailsService.loadUserByUsername(email);

                    Map<String, String> responseData = new HashMap<>();
                    if(user.getStatus() != EUserStatus.APPROVED && user.getStatus() != EUserStatus.FIRST_LOGGIN) {
                        responseData.put("status", user.getStatus().toString());
                        return new ResponseEntity<>(responseData, HttpStatus.FORBIDDEN);
                    }

                    String passwordlessLoginToken = tokenUtils.generatePasswordlessLoginToken(userDetails);
                    passwordlessToken = new PasswordlessToken(passwordlessLoginToken, new Date(), user);
                    passwordlessTokenService.save(passwordlessToken);

                    String link = "http://localhost:3000/clinic/login/confirm?token=" + passwordlessLoginToken;
                    String message = emailSenderService.passwordlessLoginEmail(user.getFirstName(), link);
                    emailSenderService.send(email, message, "Login without password");

                    return new ResponseEntity<>(HttpStatus.CREATED);
                } catch (UsernameNotFoundException e) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/login/passwordless")
    public ResponseEntity<Map<String, String>> confirmLogin(@RequestParam(value = "token") String token) {

        PasswordlessToken passwordlessToken = passwordlessTokenService.findByToken(token);

        if(passwordlessToken != null && passwordlessToken.getConfirmedAt() == null) {
            try {
                UserDetails userDetails =  userDetailsService.loadUserByUsername(passwordlessToken.getUser().getEmail());

                if(tokenUtils.validateToken(token, userDetails)) {
                    User user = userService.findUserByEmail(passwordlessToken.getUser().getEmail());
                    if(user.getStatus() != EUserStatus.APPROVED && user.getStatus() != EUserStatus.FIRST_LOGGIN)
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

                    passwordlessToken.setConfirmedAt(new Date());
                    passwordlessTokenService.save(passwordlessToken);

                    String accessToken = tokenUtils.generateAccessToken(userDetails);
                    String refreshToken = tokenUtils.generateRefreshToken(userDetails);

                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("accessToken", accessToken);
                    tokens.put("refreshToken", refreshToken);

                    if(user.getStatus() == EUserStatus.FIRST_LOGGIN)
                        return new ResponseEntity<>(tokens, HttpStatus.NOT_ACCEPTABLE);
                    return new ResponseEntity<>(tokens, HttpStatus.CREATED);
                }
            } catch (UsernameNotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (DisabledException e) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } catch (BadCredentialsException e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody String refreshToken) {

        refreshToken = refreshToken.substring(0, refreshToken.length() - 1);
        String email = tokenUtils.getEmailFromToken(refreshToken);

        if (email != null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

            if (tokenUtils.validateToken(refreshToken, userDetails) && userDetails.isAccountNonExpired() && userDetails.isAccountNonLocked()) {

                String accessToken = tokenUtils.generateAccessToken(userDetails);

                return new ResponseEntity<>(accessToken, HttpStatus.CREATED);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/register")
    public String register(@RequestBody PatientRegistrationDTO patientRegistrationDTO){
        System.out.println(patientRegistrationDTO);
        return registrationService.register(patientRegistrationDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC_ADMINISTRATOR', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR', 'ROLE_DOCTOR', 'ROLE_NURSE', 'ROLE_PATIENT')")
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){

        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(changePasswordDTO.getNewPassword().length() < 6 || changePasswordDTO.getRepeatedPassword().length() < 6) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getRepeatedPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), changePasswordDTO.getOldPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        if(user.getStatus() == EUserStatus.FIRST_LOGGIN)
            user.setStatus(EUserStatus.APPROVED);
        userService.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

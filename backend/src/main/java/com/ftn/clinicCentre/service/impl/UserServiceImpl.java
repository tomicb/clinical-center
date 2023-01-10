package com.ftn.clinicCentre.service.impl;

import com.ftn.clinicCentre.entity.Authority;
import com.ftn.clinicCentre.entity.EUserStatus;
import com.ftn.clinicCentre.entity.User;
import com.ftn.clinicCentre.repository.UserRepository;
import com.ftn.clinicCentre.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }

    @Override
    public List<User> findUsersByStatus(EUserStatus userStatus) {
        return userRepository.findUsersByStatus(userStatus);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = this.findUserByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        } else {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            for (Authority authority: user.getAuthorities()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
            }

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    grantedAuthorities);
        }
    }

	@Override
	public User findUserByJmbg(String jmbg) {
		// TODO Auto-generated method stub
		return userRepository.findUserByJmbg(jmbg);
	}
}

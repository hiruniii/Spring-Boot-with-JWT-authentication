package com.service;

import java.util.ArrayList;

import com.dto.UserDto;
import com.entity.User;
import com.repository.UserRepository;
import com.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    public String save(UserDto user) {

        if(userRepo.existsByEmail(user.getEmail())){
            return VarList.RSP_DUPLICATED;
        }
        else {
            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            userRepo.save(newUser);
            return VarList.RSP_SUCCESS;
        }
    }

}
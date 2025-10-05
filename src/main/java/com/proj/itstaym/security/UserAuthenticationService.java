package com.proj.itstaym.security;

import com.proj.itstaym.service.api.UserService;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthenticationService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @SuppressWarnings("NullableProblems")
    @Override
    public UserDetails loadUserByUsername(@NonNull  String email) throws UsernameNotFoundException {

        var user = userService.find(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(
                user.email(),
                user.password(),
                List.of(new SimpleGrantedAuthority(user.role().name()))
        );
    }
}
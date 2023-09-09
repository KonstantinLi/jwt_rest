package com.kostyali.security_jwt.services;

import com.kostyali.security_jwt.dto.JwtRequest;
import com.kostyali.security_jwt.dto.JwtResponse;
import com.kostyali.security_jwt.dto.RegistrationUser;
import com.kostyali.security_jwt.dto.UserInfo;
import com.kostyali.security_jwt.exceptions.AppException;
import com.kostyali.security_jwt.model.User;
import com.kostyali.security_jwt.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(
                    new AppException(HttpStatus.UNAUTHORIZED.value(), "Wrong username or password"),
                    HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> registration(@RequestBody RegistrationUser registrationUser) {
        if (!registrationUser.getPassword().equals(registrationUser.getConfirmPassword())) {
            return new ResponseEntity<>(
                    new AppException(HttpStatus.BAD_REQUEST.value(), "Passwords don't match"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userService.findByName(registrationUser.getUsername()) != null) {
            return new ResponseEntity<>(
                    new AppException(HttpStatus.BAD_REQUEST.value(), "User already exists"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = userService.save(registrationUser);
        return ResponseEntity.ok(new UserInfo(user.getId(), user.getName(), user.getEmail()));
    }
}

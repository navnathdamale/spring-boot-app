package com.springsecurity.controller;

import com.springsecurity.datatransferobject.JWTRequestDTO;
import com.springsecurity.datatransferobject.JWTResponseDTO;
import com.springsecurity.security.JWTUserDetailsService;
import com.springsecurity.util.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Expose a POST API /authenticate using the AuthenticationController.
 * The POST API gets username and password in the body- Using Spring Authentication Manager we authenticate the username and password.
 * If the credentials are valid, a JWT token is created using the JWTTokenUtil and provided to the client.
 */
@ConditionalOnProperty(
        value = "jwt.enable",
        havingValue = "true",
        matchIfMissing = true)
@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JWTRequestDTO authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JWTResponseDTO(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }
}
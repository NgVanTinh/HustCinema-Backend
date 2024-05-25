package com.hustcinema.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hustcinema.backend.dto.request.AuthenticationRequest;
import com.hustcinema.backend.dto.request.LogoutRequest;
import com.hustcinema.backend.dto.respond.AuthenticationRespond;
import com.hustcinema.backend.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import jakarta.mail.internet.ParseException;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationController {
    
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthenticationRespond login(@RequestBody AuthenticationRequest request) {
        return authenticationService.authenticated(request);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException, java.text.ParseException {
        authenticationService.logout(request);
    }

}

package com.example.QuickBuy.controllers;

import com.example.QuickBuy.models.owner.AuthenticationResponse;
import com.example.QuickBuy.models.owner.LoginDto;
import com.example.QuickBuy.models.owner.Owner;
import com.example.QuickBuy.models.owner.OwnerDto;
import com.example.QuickBuy.services.owner.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class OwnerController {
    private final AuthenticationService authenticationService;

    public OwnerController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Owner req) {
        Object response = authenticationService.register(req);

        if(response instanceof AuthenticationResponse) {
            return ResponseEntity.ok(response);
        } else if(response instanceof String && response.equals("User already exists")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unexpected error occurred");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginDto req) {
        AuthenticationResponse tokenObject = authenticationService.authenticate(req);

        // Directly return the AuthenticationResponse from the service
        return ResponseEntity.ok(tokenObject);
    }

    @PostMapping(path = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> refreshToken(@RequestParam(name = "token") String refreshToken) {
        // Call the service to refresh the token
        String token = authenticationService.refreshToken(refreshToken);

        // Create a response map
        Map<String, String> response = new HashMap<>();
        response.put("accessToken", token);

        // Return the response as JSON
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authHeader) {
        System.out.println("Header: " + authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Trim any extra spaces and retrieve the token
            String token = authHeader.substring(7);


            // Call the service to retrieve the user
            OwnerDto owner = authenticationService.getUser(token);

            // Check if the user was found
            if (owner != null) {
                return ResponseEntity.ok(owner);
            } else {
                return ResponseEntity.status(404).body("User not found");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid Authorization header");
        }
    }

}

package com.example.QuickBuy.services.owner;

import com.example.QuickBuy.models.owner.AuthenticationResponse;
import com.example.QuickBuy.models.owner.LoginDto;
import com.example.QuickBuy.models.owner.Owner;
import com.example.QuickBuy.models.owner.OwnerDto;
import com.example.QuickBuy.repositories.OwnerRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final OwnerRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(OwnerRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public Object register(Owner req) {
        Optional<Owner> existingUser = repository.findUserByEmail(req.getEmail());

        if(existingUser.isPresent()) {
            return "User already exist";
        }

        Owner owner = new Owner();

        owner.setFirstName(req.getFirstName());
        owner.setLastName(req.getLastName());
        owner.setEmail(req.getEmail());
        owner.setPassword(passwordEncoder.encode(req.getPassword()));
        owner.setSexe(req.getSexe());

        owner = repository.save(owner);

        String accessToken = jwtService.generateAccessToken(owner);
        String refreshToken = jwtService.generateRefreshToken(owner);

        return new AuthenticationResponse("Account Created Successfully", accessToken, refreshToken);
    }

    public AuthenticationResponse authenticate(LoginDto req) {
        Optional<Owner> optionalOwner = repository.findUserByEmail(req.getEmail());
        if (optionalOwner.isEmpty()) {
            return new AuthenticationResponse("User with the provided email not found", null, null);
        }

        Owner owner = optionalOwner.get();

        if (!passwordEncoder.matches(req.getPassword(), owner.getPassword())) {
            return new AuthenticationResponse("Invalid password", null, null);
        }

        String accessToken = jwtService.generateAccessToken(owner);
        String refreshToken = jwtService.generateRefreshToken(owner);

        return new AuthenticationResponse("Authentication successful", accessToken, refreshToken);
    }

    public String refreshToken(String refreshToken) {
        String id = jwtService.extractUserId(refreshToken);
        Owner user = repository.findById(id).orElseThrow();

        return jwtService.generateAccessToken(user);
    }

    public OwnerDto getUser(String token) {
            String id = jwtService.extractUserId(token);
            return repository.findById(id).map((user -> new OwnerDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getSexe()))).orElseThrow();
    }
}

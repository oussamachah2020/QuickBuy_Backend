package com.example.QuickBuy.services.owner;

import com.example.QuickBuy.models.owner.Owner;
import com.example.QuickBuy.repositories.OwnerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImplementation implements UserDetailsService {
    private final OwnerRepository repository;


    public UserDetailsImplementation(OwnerRepository repository) {
        this.repository = repository;
    }

    public Owner loadUserById(String id) throws UsernameNotFoundException {
        return repository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}

package org.example.individualwork.service.auth;

import org.example.individualwork.repository.SotuvchiRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    final private SotuvchiRepository sotuvchiRepository;

    public AuthenticationService(SotuvchiRepository sotuvchiRepository) {
        this.sotuvchiRepository = sotuvchiRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return sotuvchiRepository.findByUsername(username);
    }
}

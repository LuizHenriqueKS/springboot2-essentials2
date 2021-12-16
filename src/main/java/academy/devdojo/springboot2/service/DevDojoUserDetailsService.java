package academy.devdojo.springboot2.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import academy.devdojo.springboot2.domain.DevDojoUser;
import academy.devdojo.springboot2.repository.DevDojoUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DevDojoUserDetailsService implements UserDetailsService {

    private final DevDojoUserRepository devDojoUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        DevDojoUser user = devDojoUserRepository.findByUsername(username);
        return Optional
                .ofNullable(user)
                .orElseThrow(() -> new UsernameNotFoundException("DevDojo User not found"));
    }

}

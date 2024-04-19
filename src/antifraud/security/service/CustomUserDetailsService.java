package antifraud.security.service;

import antifraud.domain.User;
import antifraud.repository.UserRepository;
import antifraud.security.adapter.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsernameIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        return new UserAdapter(user);
    }
}

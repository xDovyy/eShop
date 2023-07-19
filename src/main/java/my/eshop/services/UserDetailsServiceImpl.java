package my.eshop.services;

import my.eshop.enumerators.Role;
import my.eshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        my.eshop.entities.User user = repo.findByEmail(email);
        if (user != null) {
            return new User(user.getEmail(), user.getPassword(), buildSimpleGrantedAuthorities(user.getRole()));
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }

    private static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(Role role) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

        return authorities;
    }

}
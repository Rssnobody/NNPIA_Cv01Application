package cz.upce.cv01.services;

import cz.upce.cv01.domain.AppUser;
import cz.upce.cv01.repositories.IAppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final IAppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUserName(userName);

        if (appUser == null) throw new UsernameNotFoundException("User not found with username: " + userName);

        return new User(appUser.getUserName(), appUser.getPassword(), Collections.emptyList());
    }
}

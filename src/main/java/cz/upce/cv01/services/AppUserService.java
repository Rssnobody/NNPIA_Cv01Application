package cz.upce.cv01.services;

import cz.upce.cv01.domain.AppUser;
import cz.upce.cv01.domain.Role;
import cz.upce.cv01.repositories.IAppUserRepository;
import cz.upce.cv01.repositories.IRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AppUserService {
    private final IAppUserRepository appUserRepository;
    private final IRoleRepository roleRepository;

    public AppUser findUserById(Long id) throws ResourceNotFoundException {
        var result = appUserRepository.findById(id);

        if (result.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return result.get();
    }

    public List<AppUser> findAllByActive(boolean active) {
        return appUserRepository.findAllByActive(active);
    }

    public AppUser findByUserName(String userName) { return appUserRepository.findByUserName(userName); }

    public List<AppUser> findAllByRole(String roleName) throws ResourceNotFoundException {
        Role role = roleRepository.findByName(roleName);

        if (role == null) {
            throw new ResourceNotFoundException();
        }

        return appUserRepository.findAllBySpecifiedRole(role);
    }

    public AppUser create(final AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    public AppUser update(final AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    public void delete(final Long id) {
        appUserRepository.deleteById(id);
    }
}

package cz.upce.cv01.services;

import cz.upce.cv01.domain.AppUser;
import cz.upce.cv01.domain.Role;
import cz.upce.cv01.repositories.IAppUserRepository;
import cz.upce.cv01.repositories.IRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AppUserService {
    private final IAppUserRepository appUserRepository;
    private final IRoleRepository roleRepository;

    @Transactional(readOnly = true)
    public AppUser findUserById(Long id) throws ResourceNotFoundException {
        var result = appUserRepository.findById(id);

        if (result.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return result.get();
    }

    @Transactional
    public List<AppUser> findAllByActive(boolean active) {
        return appUserRepository.findAllByActive(active);
    }

    @Transactional
    public AppUser findByUserName(String userName) { return appUserRepository.findByUserName(userName); }

    @Transactional
    public List<AppUser> findAllByRole(String roleName) throws ResourceNotFoundException {
        Role role = roleRepository.findByName(roleName);

        if (role == null) {
            throw new ResourceNotFoundException();
        }

        return appUserRepository.findAllBySpecifiedRole(role);
    }

    @Transactional
    public AppUser create(final AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Transactional
    public AppUser update(final AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Transactional
    public void delete(final Long id) {
        appUserRepository.deleteById(id);
    }
}

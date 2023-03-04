package cz.upce.cv01.controllers;

import cz.upce.cv01.DAOs.AppUser;
import cz.upce.cv01.DAOs.Role;
import cz.upce.cv01.repositories.IAppUserRepository;
import cz.upce.cv01.repositories.IRoleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppUserController {
    private final IAppUserRepository appUserRepository;
    private final IRoleRepository roleRepository;

    public AppUserController(IAppUserRepository appUserRepository, IRoleRepository roleRepository) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/appUser/find")
    public List<AppUser> findAllByActive() {
        return appUserRepository.findAllByActive(true);
    }

    @GetMapping(path="/appUser/findByRole/{roleName}")
    public List<AppUser> findAllByRole(@PathVariable String roleName) {
        Role role = roleRepository.findByName(roleName);

        return role == null ? null : appUserRepository.findAllBySpecifiedRole(role);
    }
}

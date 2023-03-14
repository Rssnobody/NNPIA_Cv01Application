package cz.upce.cv01.controllers;

import cz.upce.cv01.domain.AppUser;
import cz.upce.cv01.domain.AuthenticationRequest;
import cz.upce.cv01.domain.AuthenticationResponse;
import cz.upce.cv01.dto.AppUserInputDto;
import cz.upce.cv01.dto.AppUserOutputDto;
import cz.upce.cv01.security.JwtTokenProvider;
import cz.upce.cv01.services.AppUserDetailsService;
import cz.upce.cv01.services.AppUserService;
import cz.upce.cv01.services.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/app-user")
@RestController
@AllArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;
    private AppUserDetailsService appUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AppUserInputDto inputUser) {
        inputUser.setPassword(new BCryptPasswordEncoder().encode(inputUser.getPassword()));
        var result = appUserService.create(toEntity(inputUser));

        final UserDetails userDetails = appUserDetailsService.loadUserByUsername(inputUser.getUserName());
        final String jwt = jwtProvider.createToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUserName(),
                            authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid username or password.", e);
        }

        final UserDetails userDetails = appUserDetailsService.loadUserByUsername(authRequest.getUserName());
        final String jwt = jwtProvider.createToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<AppUserOutputDto> findUserById(@PathVariable final Long id) throws ResourceNotFoundException {
        var result = appUserService.findUserById(id);

        return ResponseEntity.ok(toDto(result));
    }

    @GetMapping("/find")
    public ResponseEntity<List<AppUserOutputDto>> findAllByActive() {
        var result = appUserService.findAllByActive(true);

        return ResponseEntity.ok(result
                .stream()
                .map(AppUserController::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping(path="/find/{userName}")
    public ResponseEntity<AppUserOutputDto> findByUserName(@PathVariable String userName) {
        var result = appUserService.findByUserName(userName);

        return ResponseEntity.ok(toDto(result));
    }

    @GetMapping(path="/findByRole/{roleName}")
    public ResponseEntity<List<AppUserOutputDto>> findAllByRole(@PathVariable String roleName) throws ResourceNotFoundException {
        var result = appUserService.findAllByRole(roleName);

        return ResponseEntity.ok(result
                .stream()
                .map(AppUserController::toDto)
                .collect(Collectors.toList()));
    }

    @PostMapping("")
    public ResponseEntity<AppUserOutputDto> create(@RequestBody @Validated final AppUserInputDto inputAppUser) {
        inputAppUser.setPassword(new BCryptPasswordEncoder().encode(inputAppUser.getPassword()));
        var result = appUserService.create(toEntity(inputAppUser));

        return ResponseEntity.ok(toDto(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUserOutputDto> update(@PathVariable final Long id,
                                                   @RequestBody final AppUserInputDto inputAppUser) {
        inputAppUser.setPassword(new BCryptPasswordEncoder().encode(inputAppUser.getPassword()));
        var result = appUserService.update(toEntity(id, inputAppUser));

        return ResponseEntity.ok(toDto(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        appUserService.delete(id);
        return ResponseEntity.ok(null);
    }

    private static AppUserOutputDto toDto(final AppUser appUser) {
        return new AppUserOutputDto(
                appUser.getId(),
                appUser.getUserName(),
                appUser.getPassword(),
                appUser.getActive(),
                appUser.getCreationDate(),
                appUser.getUpdateDate(),
                appUser.getRoles(),
                appUser.getTasks()
        );
    }

    private static AppUser toEntity(final AppUserInputDto inputAppUser) {
        return new AppUser(
                inputAppUser.getUserName(),
                inputAppUser.getPassword(),
                inputAppUser.getActive(),
                inputAppUser.getCreationDate(),
                inputAppUser.getUpdateDate()
        );
    }

    private static AppUser toEntity(final Long id, final AppUserInputDto inputAppUser) {
        return new AppUser(
                id,
                inputAppUser.getUserName(),
                inputAppUser.getPassword(),
                inputAppUser.getActive(),
                inputAppUser.getCreationDate(),
                inputAppUser.getUpdateDate()
        );
    }
}

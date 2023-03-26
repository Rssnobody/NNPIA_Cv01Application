package cz.upce.cv01.service;

import cz.upce.cv01.domain.AppUser;
import cz.upce.cv01.repositories.IAppUserRepository;
import cz.upce.cv01.services.AppUserService;
import cz.upce.cv01.services.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static cz.upce.cv01.Examples.APP_USER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AppUserServiceTest {
    private AppUser existing = null;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private IAppUserRepository appUserRepository;

    @BeforeEach
    void setUp() {
        existing = appUserRepository.save(APP_USER);
    }

    @AfterEach
    void tearDown() {
        appUserRepository.deleteAll();
    }

    /*
    * Pro označení testu, který testuje chybový stav v Java Spring Boot lze použít
    * anotaci @Test(expected=Exception.class). Tento způsob lze použít pouze do verze JUnit 4, v JUnit 5 již neexistuje
    *  a byla nahrazena metodou assertThrows.
    */
    @Test
    void findById() throws ResourceNotFoundException {
        var actual = appUserService.findUserById(existing.getId());

        assertEquals(existing, actual);
    }
}

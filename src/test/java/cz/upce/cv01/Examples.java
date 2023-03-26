package cz.upce.cv01;

import cz.upce.cv01.domain.AppUser;

import java.time.LocalDateTime;

public final class Examples {
    public static final AppUser APP_USER = new AppUser(100L, "st60981", "tajneHeslo", true,
            LocalDateTime.now(), LocalDateTime.now());

    private Examples() {
    }
}

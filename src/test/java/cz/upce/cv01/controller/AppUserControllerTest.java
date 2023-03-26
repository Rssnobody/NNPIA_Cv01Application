package cz.upce.cv01.controller;

import cz.upce.cv01.domain.AppUser;
import cz.upce.cv01.services.AppUserService;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static cz.upce.cv01.Examples.APP_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppUserControllerTest {
    private CloseableHttpClient client;
    private HttpHost host;
    private AppUser existingUser = null;
    @LocalServerPort
    private int port;
    @Autowired
    private AppUserService appUserService;

    @BeforeEach
    void setUp() {
        client = HttpClientBuilder.create().build();
        host = new HttpHost("localhost", port);
        existingUser = appUserService.create(APP_USER);
    }

    @AfterEach
    void tearDown() throws IOException {
        appUserService.delete(existingUser.getId());
        client.close();
    }

    @Test
    void existingUserTest() throws IOException {
        HttpGet request = new HttpGet("/api/v1/app-user/" + existingUser.getId());
        HttpResponse response = client.execute(host, request);
        assertEquals(HttpStatus.OK.value(), response.getStatusLine().getStatusCode());
    }

    @Test
    void nonexistentUserTest() throws IOException {
        HttpGet request = new HttpGet("/api/v1/app-user/999");
        HttpResponse response = client.execute(host, request);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusLine().getStatusCode());
    }
}

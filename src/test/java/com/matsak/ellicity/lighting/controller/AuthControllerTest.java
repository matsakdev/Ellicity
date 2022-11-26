package com.matsak.ellicity.lighting.controller;

import com.matsak.ellicity.lighting.config.AppProperties;
import com.matsak.ellicity.lighting.util.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.MOCK,
        classes = AuthController.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:local.properties")
class AuthControllerTest {

    @Value("${host}")
    private String host;

    @Autowired
    private AppProperties appProperties;

    @Test
    void authenticateUserUriCorrectnessTest() throws IOException {
        String name = StringUtils.randomAlphabetic(8);
        HttpUriRequest request = new HttpGet(host + "/auth/login");
        HttpUriRequest request1 = new HttpPost();

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(request);

        assertThat(httpResponse.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    void registerUserTest() {
    }

    @Test
    void registerManagerTest() {
    }
}
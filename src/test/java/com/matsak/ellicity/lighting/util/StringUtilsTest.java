package com.matsak.ellicity.lighting.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void randomAlphabetic() {
        String randomString = StringUtils.randomAlphabetic(8);

        assertEquals(randomString.length(), 8);
    }
}
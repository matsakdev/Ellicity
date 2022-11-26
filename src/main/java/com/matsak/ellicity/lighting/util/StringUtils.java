package com.matsak.ellicity.lighting.util;

import java.util.Random;

public class StringUtils {
    private static String[] symbols = {"a", "b", "c", "d", "e", "f",
                                "g", "h", "i", "j", "k", "l",
                                "m", "n", "o", "p", "q", "r",
                                "s", "t", "u", "v", "w", "x",
                                "y", "z",
                                "A", "B", "C", "D", "E", "F",
                                "G", "H", "I", "J", "K", "L",
                                "M", "N", "O", "P", "Q", "R",
                                "S", "T", "U", "V", "W", "X",
                                "Y", "Z"};

    public static String randomAlphabetic(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int position = (int)Math.floor(Math.random() * symbols.length);
            stringBuilder.append(symbols[position]);
        }
        return stringBuilder.toString();
    }
}

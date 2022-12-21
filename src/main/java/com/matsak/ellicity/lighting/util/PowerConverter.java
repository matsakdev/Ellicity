package com.matsak.ellicity.lighting.util;

public final class PowerConverter {
    public static String of(Double power) {
        String postfix = "W⋅h";
        if (power / 1000 >= 1) {
            postfix = "kW⋅h";
            return String.format("%.2f", power/1000) + " " + postfix;
        }
        if (power / 1000_000 >= 1) {
            postfix = "MW⋅h";
            return String.format("%.2f", power/1_000_000) + " " + postfix;
        }
        if (power / 1000_000_000 >= 1) {
            postfix = "GW⋅h";
            return String.format("%.2f", power/1_000_000_000) + " " + postfix;
        }
        return power + " " + postfix;
    }
}

package com.matsak.ellicity.lighting.payload.auth;

import com.matsak.ellicity.lighting.security.*;

public class SignUpModeratorRequest extends SignUpRequest{
    private Authority authority;

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }
}

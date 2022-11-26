package com.matsak.ellicity.lighting.payload.auth;

import com.matsak.ellicity.lighting.security.Authority;

public class SignUpManagerRequest extends SignUpRequest {
    Authority authority;

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }
}

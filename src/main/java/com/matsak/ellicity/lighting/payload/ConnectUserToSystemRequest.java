package com.matsak.ellicity.lighting.payload;

import java.sql.Connection;

public class ConnectUserToSystemRequest {
    private String systemName;
    private String passKey;

    public ConnectUserToSystemRequest(){}

    public ConnectUserToSystemRequest(String systemName, String passKey) {
        this.systemName = systemName;
        this.passKey = passKey;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getPassKey() {
        return passKey;
    }

    public void setPassKey(String passKey) {
        this.passKey = passKey;
    }
}

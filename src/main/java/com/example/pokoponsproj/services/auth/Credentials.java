package com.example.pokoponsproj.services.auth;

import com.example.pokoponsproj.enums.ClientType;

public class Credentials {
    private String email;
    private String password;
    private ClientType type;

    public Credentials(ClientType clientType, String username, String password) {
        this.email = username;
        this.password = password;
        this.type = clientType;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public ClientType getClientType() {
        return type;
    }

    public void setClientType(ClientType clientType) {

        this.type = clientType;
    }
}


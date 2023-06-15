package com.example.pokoponsproj.services.auth;

import com.example.pokoponsproj.enums.ClientType;

public class Credentials {
    private String email;
    private String password;
    private ClientType clientType;

    public Credentials(String username, String password, ClientType clientType) {
        this.email = username;
        this.password = password;
        this.clientType = clientType;
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
        return clientType;
    }

    public void setClientType(ClientType clientType) {

        this.clientType = clientType;
    }
}


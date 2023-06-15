package com.example.pokoponsproj.services.auth;

import com.example.pokoponsproj.services.facades.ClientFacade;

public class Session {

    private String token;
    private long lastActivity;
    private final ClientFacade facade;

    public Session(String token, ClientFacade facade) {
        this.token = token;
        this.lastActivity = System.currentTimeMillis();
        this.facade = facade;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(long lastActivity) {
        this.lastActivity = lastActivity;
    }

    public ClientFacade getFacade() {
        return facade;
    }
}
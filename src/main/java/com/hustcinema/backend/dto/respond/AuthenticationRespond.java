package com.hustcinema.backend.dto.respond;

public class AuthenticationRespond {
    
    private boolean isAuthenticated;
    private String token;
    
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    
}

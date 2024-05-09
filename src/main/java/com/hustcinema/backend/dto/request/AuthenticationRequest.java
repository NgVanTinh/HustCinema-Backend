package com.hustcinema.backend.dto.request;

public class AuthenticationRequest {
    
    private String username;
    private String password;
    public String getUserName() {
        return username;
    }
    public void setUserName(String userName) {
        this.username = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
}

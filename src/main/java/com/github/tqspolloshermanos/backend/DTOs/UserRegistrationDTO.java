package com.github.tqspolloshermanos.backend.DTOs;

public class UserRegistrationDTO {
    private String email;
    private String password;

    // Constructors
    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
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
}

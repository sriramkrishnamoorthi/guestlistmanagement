package com.example.myapplication;
public class Guest {
    private String name;
    private String email;
    private String status;

    private String response;

    public Guest(String name, String email, String status) {
        this.name = name;
        this.email = email;
        this.status = status;
        this.response = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}

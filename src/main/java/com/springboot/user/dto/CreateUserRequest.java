package com.springboot.user.dto;

import com.springboot.user.data.model.UserDetails;

public class CreateUserRequest implements UserDetails {
    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String email;
    private String name;
    private String password;
    private String userName;

    @Override
    public String toString(){
        return String.format("name: %s, username: %s, email: %s, password: %s", name, userName, email, password);
    }
}

package com.springboot.user.dto;

import com.springboot.user.data.model.UserDetails;
import com.springboot.user.data.model.UserIdDetails;

public class User implements UserDetails {
    private static final String passwordPlaceholder = "********";

    public static User fromUserIdentity(UserIdDetails user){
        if(user == null)
            return null;

        return new User(user.getId(), user.getName(), user.getUserName(), user.getEmail());
    }

    private String email;
    private String name;
    private String id;
    private String userName;

    public User(Long id, String name, String userName, String email){
        this.id = id.toString();
        this.name = name;
        this.userName = userName;
        this.email = email;
    }

    public String getId() {
        return id;
    }

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
        return passwordPlaceholder;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return String.format("User{id=%s, name=%s, email=%s, userName=%s}",
                id, name, email, userName);
    }

}

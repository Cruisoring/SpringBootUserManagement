package com.springboot.user.data.model;

import javax.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class UserModel extends BaseModel implements UserIdDetails {

    public static UserModel fromUserDetails(UserDetails userDetails){
        UserModel newUserModel = new UserModel();
        return updateUserDetails(newUserModel, userDetails);
    }

    public static UserModel updateUserDetails(UserModel userModel, UserDetails userDetails){
        if(userModel == null){
            return null;
        } else if(userDetails == null){
            return userModel;
        }

        userModel.setName(userDetails.getName());
        userModel.setUserName(userDetails.getUserName());
        userModel.setEmail(userDetails.getEmail());
        userModel.setPassword(userDetails.getPassword());
        return userModel;
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
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + "*********" + '\'' +
                ", userName=" + userName +
                '}';
    }

}

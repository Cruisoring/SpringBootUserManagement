package com.springboot.user.data.model;

public interface UserDetails {
    String getEmail();

    String getName();

    String getUserName();

    String getPassword();

    default UserModel asUserModel(){
        UserModel newUserModel = new UserModel();
        return updateModel(newUserModel);
    }

    default UserModel updateModel(UserModel userModel){
        if(userModel == null){
            return null;
        }

        userModel.setName(getName());
        userModel.setUserName(getUserName());
        userModel.setEmail(getEmail());
        userModel.setPassword(getPassword());
        return userModel;
    }

    default boolean isEqualTo(UserDetails another){
        if(another == null)
            return false;

        return this.getEmail().equals(another.getEmail())
                && this.getName().equals(another.getName())
                && this.getUserName().equals(another.getUserName());
    }
}

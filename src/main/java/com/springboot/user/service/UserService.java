package com.springboot.user.service;

import com.springboot.user.data.model.UserModel;
import com.springboot.user.data.repository.UserRepository;
import com.springboot.user.dto.CreateUserRequest;
import com.springboot.user.dto.UpdateUserRequest;
import com.springboot.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> get(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        List<UserModel> users = userRepository.findAll(pageRequest).getContent();
        List<User> dtos = users.stream()
                .map(user -> new User(user.getId(), user.getName(), user.getUserName(), user.getEmail()))
                .collect(Collectors.toList());
        return dtos;
    }

    public User get(String idString){
        try {
            Long id = Long.valueOf(idString);
            return User.fromUserIdentity(userRepository.findById(id).get());
        }catch (Exception ex){
            return null;
        }
    }

    public User create(CreateUserRequest userRequest){
        UserModel userModel = UserModel.fromUserDetails(userRequest);
        userModel = userRepository.save(userModel);
        return User.fromUserIdentity(userModel);
    }

    public User update(String idString, UpdateUserRequest userRequest){
        try {
            Long id = Long.valueOf(idString);
            UserModel userModel = userRepository.findById(id).get();
            UserModel.updateUserDetails(userModel, userRequest);
            userModel = userRepository.save(userModel);

            return User.fromUserIdentity(userModel);
        }catch (Exception ex){
            return null;
        }
    }

//    @Transactional()
    public User delete(String idString){
        try {
            Long id = Long.valueOf(idString);
            UserModel userModel = userRepository.findById(id).orElse(null);
            User userToBeDelete = User.fromUserIdentity(userModel);
            userRepository.delete(userModel);
            return userToBeDelete;
        }catch (Exception ex){
            return null;
        }
    }
}

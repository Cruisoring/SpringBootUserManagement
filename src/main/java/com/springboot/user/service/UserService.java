package com.springboot.user.service;

import com.springboot.user.data.model.UserModel;
import com.springboot.user.data.repository.UserRepository;
import com.springboot.user.dto.CreateUserRequest;
import com.springboot.user.dto.UpdateUserRequest;
import com.springboot.user.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);

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

    public User get(String idString)
            throws IllegalArgumentException, NumberFormatException {
        Long id = Long.valueOf(idString);
        Optional<UserModel> userModel = userRepository.findById(id);
        if(userModel.isPresent()) {
            User user = User.fromUserModel(userModel.get());
            return user;
        } else {
            throw new IllegalArgumentException("Illegal Argument: " + idString);
        }
    }

    public User create(CreateUserRequest userRequest){
        UserModel userModel = userRequest.asUserModel();
        userModel = userRepository.save(userModel);
        return User.fromUserModel(userModel);
    }

    public User update(String idString, UpdateUserRequest userRequest)
            throws IllegalArgumentException, NumberFormatException {
        Long id = Long.valueOf(idString);
        Optional<UserModel> userModel = userRepository.findById(id);
        if(userModel.isPresent()) {
            UserModel model = userModel.get();
            userRequest.updateModel(model);
            model = userRepository.save(model);
            return User.fromUserModel(model);
        } else {
            throw new IllegalArgumentException("Illegal Argument: " + idString);
        }
    }

    public User delete(String idString)
            throws IllegalArgumentException, NumberFormatException{
        Long id = Long.valueOf(idString);
        Optional<UserModel> userModel = userRepository.findById(id);
        if(userModel.isPresent()) {
            UserModel model = userModel.get();
            userRepository.delete(model);
            return User.fromUserModel(model);
        } else {
            throw new IllegalArgumentException("Illegal Argument: " + idString);
        }
    }
}

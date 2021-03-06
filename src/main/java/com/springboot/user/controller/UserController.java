package com.springboot.user.controller;

import com.springboot.user.dto.CreateUserRequest;
import com.springboot.user.dto.UpdateUserRequest;
import com.springboot.user.dto.User;
import com.springboot.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * List all users
     *
     * @return return all users as a list.
     */
    @GetMapping(value = "")
    public List<User> list(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "0") int size
    ) {
        List<User> users = userService.list(page, size);
        return users;
    }

    @PutMapping(value = "")
    public User createUser(@RequestBody @Valid CreateUserRequest user) {

        return userService.create(user);
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable("id") String id)
            throws IllegalArgumentException {
        return userService.get(id);
    }

    @PostMapping(value = "/{id}")
    public User updateUser(@PathVariable("id") String id,
                           @RequestBody @Valid UpdateUserRequest update)
            throws IllegalArgumentException, NumberFormatException {
        return userService.update(id, update);
    }

    @DeleteMapping(value = "/{id}")
    public User deleteUser(@PathVariable("id") String id)
            throws IllegalArgumentException, NumberFormatException {
        return userService.delete(id);
    }

}

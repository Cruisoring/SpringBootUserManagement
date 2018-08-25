package com.springboot.user.controller;

import com.springboot.user.dto.CreateUserRequest;
import com.springboot.user.dto.UpdateUserRequest;
import com.springboot.user.dto.User;
import com.springboot.user.properties.PagingProperties;
import com.springboot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PagingProperties pagingProperties;

    @Autowired
    private UserService userService;

    /**
     * List all users
     * @return return all users as a list.
     */
    @GetMapping(value="")
    public List<User> list(
            @RequestParam(value="page", required = false, defaultValue = "0") int page,
            @RequestParam(value="size", required = false, defaultValue = "0") int size
    ){
        if(size == 0) {
            size = pagingProperties.getSize();
        }
        List<User> users = userService.get(page, size);
        return users;
    }

    @PutMapping(value="")
    public User createUser(@RequestBody CreateUserRequest user){

        return userService.create(user);
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable("id") String id){
        return userService.get(id);
    }

    @PostMapping(value = "/{id}")
    public User updateUser(@PathVariable("id") String id,
                           @RequestBody UpdateUserRequest update){
        return userService.update(id, update);
    }

    @DeleteMapping(value = "/{id}")
    public User deleteUser(@PathVariable("id") String id) {
        return userService.delete(id);
    }

}

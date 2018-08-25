package com.springboot.user.service;

import com.springboot.user.data.model.UserModel;
import com.springboot.user.data.repository.UserRepository;
import com.springboot.user.dto.CreateUserRequest;
import com.springboot.user.dto.UpdateUserRequest;
import com.springboot.user.dto.User;
import com.springboot.user.properties.PagingProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    private static final int minUserCount = 20;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PagingProperties pagingProperties;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    int userCount = 0;
    int defaultSize = 0;
    @Before
    public void before() {
        userCount = (int) userRepository.count();
        defaultSize = pagingProperties.getSize();
        if(userCount >= minUserCount)
            return;

        List<String> activeProfiles = Arrays.asList(applicationContext.getEnvironment().getActiveProfiles());
        if( ! activeProfiles.contains("test"))
            fail("Double check application and test context to make sure it is safe!");

        List<UserModel> fakeUsers = UserGenerator.getFakeUsers(minUserCount+1);
        userRepository.saveAll(fakeUsers);
        userCount = (int) userRepository.count();
    }

    @Test
    public void list() {
        int defaultTotalPage = (userCount + defaultSize -1) / defaultSize;

        List<User> users = userService.list(defaultTotalPage, -1);
        assertEquals(0, users.size());

        users = userService.list(defaultTotalPage-1, -1);
        assertEquals(userCount % defaultSize, users.size() % defaultSize);

        users = userService.list(0, -1);
        assertEquals(defaultSize, users.size());

        users = userService.list(0, 19);
        assertEquals(19, users.size());
    }

    @Test
    public void get() {
        try {
            String illegalId = "abc";
            userService.get(illegalId);
            fail("Shall be bypassed if NumberFormatException is thrown");
        }catch (NumberFormatException e){
        }

        try {
            String negativeId = "-111";
            userService.get(negativeId);
            fail("Shall be bypassed if IllegalArgumentException is thrown");
        }catch (IllegalArgumentException e){
        }

        Long firstId = findFirstExistingId();
        User user = userService.get(firstId.toString());
        assertEquals(firstId.toString(), user.getId());
    }

    private Long findFirstExistingId() {
        Long firstId = LongStream.range(1, 1000)
                .filter(id -> userRepository.existsById(id))
                .findFirst().orElse(-1L);
        if(firstId.equals(-1L)){
            fail("Why cannot find first user?");
        }
        return firstId;
    }

    @Test
    public void create() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@ttt.com");
        request.setName("tesstdsd d d");
        request.setUserName("Create User");
        request.setPassword("MyPassword");

        User created = userService.create(request);
        assertTrue(created.isEqualTo(request));
        assertTrue(created.getId() != null);
    }

    @Test
    public void update() {
        Long firstId = findFirstExistingId();
        UserModel existingUser = userRepository.findById(firstId).orElse(null);
        UpdateUserRequest request = new UpdateUserRequest();
        request.setEmail("www@disis.com");
        request.setName("Tom Croxes");
        request.setUserName("Update User");
        request.setPassword("UpdatedPassword");
        User updatedUser = userService.update(existingUser.getId().toString(), request);
        assertTrue(updatedUser.isEqualTo(request));
        assertEquals(existingUser.getId().toString(), updatedUser.getId());
    }

    @Test
    public void delete() {
        Long firstId = findFirstExistingId();
        UserModel existingUser = userRepository.findById(firstId).orElse(null);
        User deleted = userService.delete(firstId.toString());
        assertFalse(userRepository.existsById(firstId));
    }
}
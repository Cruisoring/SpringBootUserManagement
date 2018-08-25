package com.springboot.user.service;

import com.springboot.user.data.model.UserModel;
import com.springboot.user.dto.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserGeneratorTest {

    @Test
    public void getFakeUsers() {
        List<UserModel> users = UserGenerator.getFakeUsers(-1);
        assertTrue(users.size() == 0);

        users = UserGenerator.getFakeUsers( 1000);
        assertEquals(UserGenerator.maxFakeNumber, users.size());
    }
}
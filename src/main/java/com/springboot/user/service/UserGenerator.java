package com.springboot.user.service;

import com.springboot.user.data.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@Profile("test")
@Service
public class UserGenerator {
    private final static int maxFakeNumber = 100;
    private final static Random random = new Random();
    private final static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private final static int alphabetLength = alphabet.length();

    private static String getRandomName(int length){

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(alphabet.charAt(random.nextInt(alphabetLength)));
        }
        return sb.toString();
    }

    public List<UserModel> getFakeUsers(int number){
        if(number < 1){
            return new ArrayList<>();
        } else if (number > maxFakeNumber){
            number = maxFakeNumber;
        }

        List<UserModel> users = IntStream.range(0, number)
                .mapToObj(i -> getFakeUser())
                .collect(Collectors.toList());
        return users;
    }

    public UserModel getFakeUser(){
        UserModel userModel = new UserModel();
        String username = getRandomName(2 + random.nextInt(5));
        userModel.setUserName(username);
        String name = getRandomName(random.nextInt(3) + 2);
        userModel.setName(username + " " + name);
        String email = String.format("%s.%s@%s.com", username, name, getRandomName(5));
        userModel.setEmail(email);
        userModel.setPassword(getRandomName(15));
        return userModel;
    }
}

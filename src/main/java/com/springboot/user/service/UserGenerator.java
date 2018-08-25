package com.springboot.user.service;

import com.springboot.user.data.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserGenerator {

    public final static int maxFakeNumber = 100;
    private final static Random random = new Random();
    private final static Character characterA = 'a';
    private final static int alphabetLength = 26;

    private static String getRandomName(int length){

        String randomName = IntStream.range(0, length)
                .mapToObj(i -> String.valueOf((char)(characterA + random.nextInt(alphabetLength))))
                .reduce("", (sentence, ch) -> sentence+ch);

        return randomName;
    }

    public static List<UserModel> getFakeUsers(int number){
        if(number < 1){
            return new ArrayList<>();
        } else if (number > maxFakeNumber){
            number = maxFakeNumber;
        }

        List<UserModel> users = IntStream.range(0, number).parallel()
                .mapToObj(i -> getFakeUser())
                .collect(Collectors.toList());
        return users;
    }

    public static UserModel getFakeUser(){
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

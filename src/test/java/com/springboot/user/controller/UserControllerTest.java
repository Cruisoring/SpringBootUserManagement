package com.springboot.user.controller;

import com.springboot.user.data.model.UserModel;
import com.springboot.user.data.repository.UserRepository;
import com.springboot.user.dto.User;
import com.springboot.user.properties.PagingProperties;
import com.springboot.user.service.UserGenerator;
import com.springboot.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FlashMap;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

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
    public void list() throws Exception {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockHttpSession session = new MockHttpSession();

        mvc.perform(MockMvcRequestBuilders.get("/users")
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createUser() throws Exception {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockHttpSession session = new MockHttpSession();
        String json="{\n" +
                "\"name\": \"william\",\n" +
                "\"username\": \"me\",\n" +
                "\"email\": \"dds@add.com\",\n" +
                "\"password\": \"pass\"\n" +
                "}";
        mvc.perform(MockMvcRequestBuilders.put("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("william"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value( "dds@add.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(User.passwordPlaceholder))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getUser() throws Exception {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockHttpSession session = new MockHttpSession();
        mvc.perform(MockMvcRequestBuilders.get("/users/2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateUser() throws Exception {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockHttpSession session = new MockHttpSession();
        String json="{\n" +
                "\"name\": \"tester\",\n" +
                "\"username\": \"him\",\n" +
                "\"email\": \"abc@add.com\",\n" +
                "\"password\": \"password\"\n" +
                "}";
        mvc.perform(MockMvcRequestBuilders.post("/users/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("tester"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value( "abc@add.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(User.passwordPlaceholder))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteUser() throws Exception {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockHttpSession session = new MockHttpSession();
        mvc.perform(MockMvcRequestBuilders.delete("/users/3")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("3"))
                .andDo(MockMvcResultHandlers.print());
    }
}
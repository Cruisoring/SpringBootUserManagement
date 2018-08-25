package com.springboot.user;

import com.springboot.user.data.model.UserModel;
import com.springboot.user.data.repository.UserRepository;
import com.springboot.user.service.UserGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class UserApplication {

	Logger logger = LoggerFactory.getLogger(UserApplication.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void init(){
		String activeProfile = System.getProperty("injectFakeUsers");
		if(!"true".equalsIgnoreCase(activeProfile))
			return;

		List<String> activeProfiles = Arrays.asList(applicationContext.getEnvironment().getActiveProfiles());
		if(activeProfiles.contains("test") && userRepository.count() < 50) {
			List<UserModel> fakeUsers = UserGenerator.getFakeUsers(10);
			userRepository.saveAll(fakeUsers);
		}
	}

	public static void main(String[] args) {
		//*/
		SpringApplication.run(UserApplication.class, args);

		/*/
		ApplicationContext applicationContext = SpringApplication
				.run(UserApplication.class, args);

		for (String name : applicationContext.getBeanDefinitionNames()) {
			System.out.println(name);
		}
		//*/
	}
}

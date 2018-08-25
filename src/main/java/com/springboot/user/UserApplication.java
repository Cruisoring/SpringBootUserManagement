package com.springboot.user;

import com.springboot.user.data.model.UserModel;
import com.springboot.user.data.repository.UserRepository;
import com.springboot.user.service.UserGenerator;
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

//	@Value("${spring.profiles.active}")
//	private String activeProfiles;

	@Profile("test")
	@Bean
	public String testBean() {
		return "test";
	}

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private UserGenerator userGenerator;

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void init(){
		String activeProfile = System.getProperty("injectFakeUsers");
		if(!"true".equalsIgnoreCase(activeProfile))
			return;

		List<String> activeProfiles = Arrays.asList(applicationContext.getEnvironment().getActiveProfiles());
		if(activeProfiles.contains("test") && userRepository.count() < 30) {
			List<UserModel> fakeUsers = userGenerator.getFakeUsers(30);
			userRepository.saveAll(fakeUsers);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);

//		ApplicationContext applicationContext = SpringApplication
//				.run(UserApplication.class, args);
//
//		for (String name : applicationContext.getBeanDefinitionNames()) {
//			System.out.println(name);
//		}
	}
}

package com.security.jwt.medium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
import org.zalando.logbook.Logbook;

//import com.security.jwt.medium.logging.BodyReaderFilter;

@SpringBootApplication()
public class RoleBasedJwt2Application {

	public static void main(String[] args) {
		SpringApplication.run(RoleBasedJwt2Application.class, args);
	}
}

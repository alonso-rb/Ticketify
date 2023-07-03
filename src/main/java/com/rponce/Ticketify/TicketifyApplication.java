package com.rponce.Ticketify;

import java.security.SecureRandom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TicketifyApplication {
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(5, new SecureRandom());
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TicketifyApplication.class, args);
	}

}

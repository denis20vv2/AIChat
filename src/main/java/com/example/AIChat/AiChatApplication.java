package com.example.AIChat;

import com.example.AIChat.Group.Service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class AiChatApplication {

	private static final Logger logger = LoggerFactory.getLogger(AiChatApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AiChatApplication.class, args);

		logger.info("start");
	}

}

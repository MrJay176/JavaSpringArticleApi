package com.article.articleapi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EntityScan("com.article.articleapi.Models")
public class ArticleApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticleApiApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

}

//Client(Mobile app Flutter or React Web)
//Api Layer(Get,Post,Put,Delete)
//Service Layer(Business Logic)
//Data Access Layer (Connecting to any database)


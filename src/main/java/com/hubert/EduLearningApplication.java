package com.hubert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class EduLearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduLearningApplication.class, args);
	}

}

package com.file.ops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FileOpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileOpsApplication.class, args);
	}
}

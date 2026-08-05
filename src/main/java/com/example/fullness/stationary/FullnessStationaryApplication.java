package com.example.fullness.stationary;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.fullness.stationary.mapper")
public class FullnessStationaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullnessStationaryApplication.class, args);
	}

}

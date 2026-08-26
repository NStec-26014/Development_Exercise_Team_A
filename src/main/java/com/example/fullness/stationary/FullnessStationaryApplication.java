package com.example.fullness.stationary;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot アプリケーションの起動クラス。
 */
@SpringBootApplication
@MapperScan("com.example.fullness.stationary.mapper")
public class FullnessStationaryApplication {

	/**
	 * アプリケーションを起動する。
	 *
	 * @param args 起動引数
	 */
	public static void main(String[] args) {
		SpringApplication.run(FullnessStationaryApplication.class, args);
	}

}

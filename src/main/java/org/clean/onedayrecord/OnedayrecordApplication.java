package org.clean.onedayrecord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class OnedayrecordApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnedayrecordApplication.class, args);
	}

}

package com.project.study.websearch;

import com.project.study.websearch.utils.PropertiesUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebsearchApplication {

	public static void main(String[] args) {
		PropertiesUtils.loadByDotEnv();
		SpringApplication.run(WebsearchApplication.class, args);
	}

}

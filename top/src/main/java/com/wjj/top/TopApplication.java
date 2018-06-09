package com.wjj.top;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
@RestController
public class TopApplication { //extends SpringBootServletInitializer
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(TopApplication.class);
//	}

//	@GetMapping("/")
//	public String index(){
//		return "index";
//	}

	public static void main(String[] args) throws IOException {
//		Properties properties = new Properties();
//		InputStream in = Application.class.getClassLoader().getResourceAsStream("application-test.properties");
//		properties.load(in);
//		SpringApplication app = new SpringApplication(TopApplication.class);
//		app.setDefaultProperties(properties);
//		app.run(args);
		SpringApplication.run(TopApplication.class, args);
	}
}

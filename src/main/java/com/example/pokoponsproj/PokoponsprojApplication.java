package com.example.pokoponsproj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PokoponsprojApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(PokoponsprojApplication.class, args);
		Test test = ctx.getBean(Test.class);
		test.pokoponSysTest();
	}

}

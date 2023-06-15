package com.example.pokoponsproj;

import com.example.pokoponsproj.repositories.CouponRepository;
import com.example.pokoponsproj.services.auth.Session;
import com.example.pokoponsproj.services.threads.CouponExpirationDailyJob;
import com.example.pokoponsproj.services.threads.SessionExpirationJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class PokoponsprojApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(PokoponsprojApplication.class, args);
//		Test test = ctx.getBean(Test.class);
//		test.pokoponSysTest();

//		Thread sessionExpirationJob = new Thread(new SessionExpirationJob());
//		Thread couponExpirationJob = new Thread(new CouponExpirationDailyJob());

		Thread sessionExpirationJob = new Thread(ctx.getBean(SessionExpirationJob.class));
		sessionExpirationJob.start();
		Thread couponExpirationJob = new Thread((Runnable) ctx.getBean(CouponExpirationDailyJob.class));
		couponExpirationJob.start();

	}

	@Bean
	public Map<String, Session> sessions(){

		return new HashMap<String, Session>();
	}


	//
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

}

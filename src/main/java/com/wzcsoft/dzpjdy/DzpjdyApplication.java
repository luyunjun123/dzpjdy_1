package com.wzcsoft.dzpjdy;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.wzcsoft.dzpjdy.dao"})
public class DzpjdyApplication {

	public static void main(String[] args) {
//		SpringApplication springApplication = new SpringApplication(DzpjdyApplication.class);
//		springApplication.addListeners(new ApplicationStartup());
//		springApplication.run(args);
		SpringApplication.run(DzpjdyApplication.class,args);
	}

}


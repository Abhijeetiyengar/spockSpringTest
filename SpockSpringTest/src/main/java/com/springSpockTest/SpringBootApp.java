package com.springSpockTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;




@SpringBootApplication
//@ContextConfiguration(loader = SpringApplicationContextLoader.class)
public class SpringBootApp {
	
	public static void main(String args[])
	{
		SpringApplication.run(SpringBootApp.class, args);
	}

}

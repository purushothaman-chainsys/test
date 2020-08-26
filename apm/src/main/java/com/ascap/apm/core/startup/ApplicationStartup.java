package com.ascap.apm.core.startup;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Class ApplicationStartup.
 */
@SpringBootApplication
public class ApplicationStartup {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ApplicationContextConfig.class);
		application.run(args);
	}

}

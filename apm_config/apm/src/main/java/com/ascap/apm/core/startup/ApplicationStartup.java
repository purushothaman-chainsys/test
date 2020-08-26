package com.ascap.apm.core.startup;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ascap.apm.core.utils.EncryptionUtils;

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
		
		
		/*
		 * try { System.out.println(EncryptionUtils.encrypt("njqa"));
		 * System.out.println(EncryptionUtils.decrypt("KZMplNyfRw4=")); } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
		 
		
	}

}

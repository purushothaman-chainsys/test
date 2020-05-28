package com.ascap.apm.core.startup;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ascap.apm.common.exception.PrepSystemException;
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
	 * @throws PrepSystemException 
	 */
	public static void main(String[] args) throws PrepSystemException {
		
		  SpringApplication application = new
		  SpringApplication(ApplicationContextConfig.class); application.run(args);
		 
		
		/*
		 * System.out.println(EncryptionUtils.encrypt("devAPMONLINE853"));
		 * System.out.println(EncryptionUtils.decrypt("Fm4jS6RUeyKnqgDKCruphA=="));
		 */
	}

}

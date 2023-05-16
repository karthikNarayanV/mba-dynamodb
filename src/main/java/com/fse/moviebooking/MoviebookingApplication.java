package com.fse.moviebooking;



import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class MoviebookingApplication {

	private static final Logger log=Logger.getLogger(MoviebookingApplication.class);
	public static void main(String[] args) {
		
		SpringApplication.run(MoviebookingApplication.class, args);
		BasicConfigurator.configure();
		log.log(Level.INFO, "Main");
		
	}

}

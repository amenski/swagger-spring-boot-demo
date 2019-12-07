package dzone.aman.restapidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootApplication
public class RestApiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiDemoApplication.class, args);
	}
	
	@Bean
	ObjectMapper defaultMapper() { 
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDefaultPropertyInclusion(Include.NON_EMPTY);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}
}

package dzone.aman.restapidemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
//@EnableWebMvc // https://dzone.com/articles/spring-boot-enablewebmvc-and-common-use-cases
@ComponentScan(basePackages = {"dzone.aman"})
public class AppConfig implements WebMvcConfigurer {

	private int maxUploadSizeInMb = 2 * 1024 * 1024; // 2 MB

	@Bean
	ObjectMapper defaultMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDefaultPropertyInclusion(Include.NON_EMPTY);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}

	@Bean("multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver cmr = new CommonsMultipartResolver();
		cmr.setMaxUploadSize(maxUploadSizeInMb * 2); //sum size of all files/parts of a file. Since, a file may be partitioned
		cmr.setMaxUploadSizePerFile(maxUploadSizeInMb);//maximum file size of each file
		return cmr;

	}
}

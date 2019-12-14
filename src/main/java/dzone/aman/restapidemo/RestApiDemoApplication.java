package dzone.aman.restapidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration( exclude = {MultipartAutoConfiguration.class})
public class RestApiDemoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RestApiDemoApplication.class, args);
	}
	
//	@Bean
//	public CommandLineRunner run(ApplicationContext appContext) {
//		return args -> {
//
//			String[] beans = appContext.getBeanDefinitionNames();
//			Arrays.stream(beans).sorted().forEach(System.out::println);
//
//		};
//	}
}

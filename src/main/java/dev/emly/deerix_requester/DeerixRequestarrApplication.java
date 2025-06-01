package dev.emly.deerix_requester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeerixRequestarrApplication /*implements WebMvcConfigurer*/ {

//	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
//			"classpath:/resources/", "classpath:/static/", "classpath:/public/" };

	public static void main(String[] args) {
		SpringApplication.run(DeerixRequestarrApplication.class, args);
	}

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
//	}

}

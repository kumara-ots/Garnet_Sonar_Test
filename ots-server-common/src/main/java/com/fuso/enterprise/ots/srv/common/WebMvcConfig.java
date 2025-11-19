package com.fuso.enterprise.ots.srv.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/2.1.3/");

		registry.addResourceHandler("/etaarana/**").addResourceLocations("file:/home/shreekant_ots/jetty_dev/image/");

		registry.addResourceHandler("/bomchildparts/**").addResourceLocations("file:///C:/otsfiles/bomchildparts/");

		super.addResourceHandlers(registry);
	}
}
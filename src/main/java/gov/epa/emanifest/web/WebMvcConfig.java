package gov.epa.emanifest.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	@Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }
	
	@Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**").addResourceLocations("/, classpath:/META-INF/web-resources/");
  }
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON)
			.favorPathExtension(false).favorParameter(false);
	}
	
	@Bean
	public ViewResolver getViewResolver() {
		UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
		urlBasedViewResolver.setViewClass(JstlView.class);
		urlBasedViewResolver.setPrefix("/WEB-INF/views/");
		urlBasedViewResolver.setSuffix(".html");
		return urlBasedViewResolver;
	}
	
	@Bean
	public GenericHttpMessageConverter<Object> getJsonConverter() {
		MappingJackson2HttpMessageConverter messageConverter =
			new MappingJackson2HttpMessageConverter();
		messageConverter.setSupportedMediaTypes(new ArrayList(Arrays.asList(MediaType.APPLICATION_JSON)));
		return messageConverter;
	}
	
	@Bean
	public ContentNegotiatingViewResolver getContentNegotiatingViewResolver() {
		List<View> defaultViews = new ArrayList(Arrays.asList(new MappingJackson2JsonView()));
		ContentNegotiatingViewResolver contentNegotiatingViewResolver =
			new ContentNegotiatingViewResolver();
		contentNegotiatingViewResolver.setDefaultViews(defaultViews);
		return contentNegotiatingViewResolver;
	}
}

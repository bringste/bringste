package com.bringste.app.config;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;
import net.eusashead.hateoas.hal.http.converter.HalHttpMessageConverter;
import net.eusashead.hateoas.hal.http.converter.module.HalHttpMessageConverterModule;
import net.eusashead.hateoas.hal.http.converter.module.impl.AnnotationAdapterModuleImpl;
import net.eusashead.hateoas.hal.response.impl.HalResponseBuilderHandlerMethodArgumentResolver;
import net.eusashead.hateoas.response.argumentresolver.ResponseBuilderMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

  /**
   * Create a {@link HalHttpMessageConverter}
   * using the configured {@link RepresentationFactory}
   * @return
   */
  @Bean
  public HttpMessageConverter<Object> halConverter() {
    RepresentationFactory factory = representationFactory();
    HalHttpMessageConverter converter = new HalHttpMessageConverter(factory);
    HalHttpMessageConverterModule module = new AnnotationAdapterModuleImpl();
    converter.addModule(module);
    return converter;
  }

  /**
   * Create a {@link RepresentationFactory}
   * @return
   */
  @Bean
  public RepresentationFactory representationFactory() {
    return new JsonRepresentationFactory();
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new ResponseBuilderMethodArgumentResolver());
    argumentResolvers.add(new HalResponseBuilderHandlerMethodArgumentResolver(representationFactory()));
    super.addArgumentResolvers(argumentResolvers);
  }
}

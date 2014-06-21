package com.bringste.app.web.rest.dto;

import net.eusashead.hateoas.hal.adapter.RepresentationWriter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

public abstract class DefaultRepresentationWriter<T> implements RepresentationWriter<T> {
  private static final String restApiPrefix = "/app";

  String methodUrl(Class<?> resourceClass, String methodName, Class... types) {
    try {
      String[] mappings = AnnotationUtils.getAnnotation(resourceClass.getMethod(methodName, types), RequestMapping.class).value();
      return restApiPrefix + mappings[0];
    } catch (NoSuchMethodException e) {
      throw new RuntimeException();
    }
  }
}

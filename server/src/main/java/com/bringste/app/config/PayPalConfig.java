package com.bringste.app.config;

import com.paypal.api.payments.Payment;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class PayPalConfig {

  public static final String CLIENT_ID = "clientID";
  public static final String CLIENT_SECRET = "clientSecret";

  @Bean
  APIContext apiContext() {
    Properties paypalProperties = getPayPalProperties();
    Payment.initConfig(paypalProperties);
    try {
      String accessToken = new OAuthTokenCredential(
        paypalProperties.getProperty(CLIENT_ID),
        paypalProperties.getProperty(CLIENT_SECRET)
      ).getAccessToken();
      return new APIContext(accessToken);
    } catch (PayPalRESTException e) {
      return null;
    }
  }

  private Properties getPayPalProperties() {
    InputStream propertiesStream = getClass().getClassLoader().getResourceAsStream("paypal.properties");
    try {
      Properties properties = new Properties();
      properties.load(propertiesStream);

      if (!properties.containsKey("paypal.clientID")) {
        properties.put(CLIENT_ID, getGetenvOrDefault("paypal.clientID", "id"));
      }

      if (!properties.containsKey("paypal.clientSecret")) {
        properties.put(CLIENT_ID, getGetenvOrDefault("paypal.clientSecret", "secret"));
      }

      if (!properties.containsKey("service.EndPoint")) {
        properties.put("service.EndPoint", getGetenvOrDefault("paypal.service.EndPoint", "endpoint"));
      }

      propertiesStream.close();
      return properties;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String getGetenvOrDefault(String key , String defaultValue) {
    if(System.getenv(key) != null) {
      return System.getenv(key);
    }
    return defaultValue;
  }
}

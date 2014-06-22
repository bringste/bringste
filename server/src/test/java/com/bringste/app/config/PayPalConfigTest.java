package com.bringste.app.config;

import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.PayPalRESTException;
import org.junit.Ignore;
import org.junit.Test;

public class PayPalConfigTest {
  @Test
  @Ignore
  public void paymentShouldWork() throws PayPalRESTException {
    APIContext apiContext = new PayPalConfig().apiContext();
    Payer payer = new Payer();
    payer.setPaymentMethod("paypal");
    RedirectUrls urls = new RedirectUrls();
    urls.setReturnUrl("http://www.bringste.berlin/payment_done");
    urls.setCancelUrl("http://www.bringste.berlin/payment_canceled");

    Payment payment = new Payment();
    payment.setIntent("sale");
    payment.setPayer(payer);
    payment.setRedirectUrls(urls);
    payment.create(apiContext);

    System.out.println("DONE");
  }
}
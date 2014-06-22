package com.bringste.app.config;

import com.paypal.api.payments.*;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.PayPalRESTException;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PayPalConfigTest {
  @Test
  @Ignore("is an IT")
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

    List<Transaction> transactions = new ArrayList<>();

    Amount amount = new Amount();
    amount.setCurrency("EUR");
    amount.setTotal("1.00");

    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transactions.add(transaction);

    payment.setTransactions(transactions);

    Payment newPayment = payment.create(apiContext);
    System.out.println("DONE");
  }
}
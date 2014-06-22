package com.bringste.app.service;

import com.bringste.app.domain.User;
import com.paypal.api.payments.*;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayPalService {
  @Autowired
  APIContext apiContext;

  public PaymentResult createPayment(User payerUser, User payeeUser, BigDecimal moneyAmount) {
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
    amount.setTotal(bigDecimalToString(moneyAmount));

    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setDescription("Tip for bringste delivery");
    transactions.add(transaction);

    Payee payee = new Payee();
    payee.setEmail(payee.getEmail());
    transaction.setPayee(payee);
    payment.setTransactions(transactions);

    try {
      Payment newPayment = payment.create(apiContext);
      for (Link link : newPayment.getLinks()) {
        link.getRel();
        if (link.getRel().equalsIgnoreCase("approval_url")) {
          return new PaymentResult(link.getHref());
        }
      }
      throw new IllegalStateException("could not find correct link for redirect");
    } catch (PayPalRESTException e) {
      throw new RuntimeException(e);
    }
  }

  String bigDecimalToString(BigDecimal bigDecimal) {
    bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
    DecimalFormat df = new DecimalFormat();
    df.setMaximumFractionDigits(2);
    df.setMinimumFractionDigits(0);
    df.setGroupingUsed(false);
    return df.format(bigDecimal);
  }
}

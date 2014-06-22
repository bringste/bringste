package com.bringste.app.service;

/**
 * Created by andreas on 22.06.14.
 */
public class PaymentResult {
  String redirectUrl;

  public PaymentResult(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }

  public PaymentResult() {
  }

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }
}

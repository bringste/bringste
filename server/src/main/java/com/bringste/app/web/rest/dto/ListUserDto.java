package com.bringste.app.web.rest.dto;

public class ListUserDto {
  String name;
  String login;
  String avatarUrl;

  public String getName() {
    return name;
  }

  public ListUserDto withName(String name) {
    this.name = name;
    return this;
  }

  public String getLogin() {
    return login;
  }

  public ListUserDto withLogin(String login) {
    this.login = login;
    return this;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public ListUserDto withAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
    return this;
  }
}

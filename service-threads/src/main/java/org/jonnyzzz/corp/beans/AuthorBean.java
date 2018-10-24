package org.jonnyzzz.corp.beans;

import java.util.Objects;

public class AuthorBean {
  private String name;
  private String twitter;

  public AuthorBean(String name, String twitter) {
    this.name = name;
    this.twitter = twitter;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTwitter() {
    return twitter;
  }

  public void setTwitter(String twitter) {
    this.twitter = twitter;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AuthorBean author = (AuthorBean) o;
    return Objects.equals(name, author.name) &&
            Objects.equals(twitter, author.twitter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, twitter);
  }

  @Override
  public String toString() {
    return "AuthorBean{" +
            "name='" + name + '\'' +
            ", twitter='" + twitter + '\'' +
            '}';
  }
}

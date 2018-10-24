package org.jonnyzzz.corp.beans;


import java.util.Objects;

public class LicenseBean {
  private final String license;

  public LicenseBean() {
    license = "error";
  }

  public LicenseBean(String license) {
    this.license = license;
  }

  public String getLicense() {
    return license;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LicenseBean that = (LicenseBean) o;
    return Objects.equals(license, that.license);
  }

  @Override
  public int hashCode() {
    return Objects.hash(license);
  }

  @Override
  public String toString() {
    return "LicenseBean{" +
            "license='" + license + '\'' +
            '}';
  }
}
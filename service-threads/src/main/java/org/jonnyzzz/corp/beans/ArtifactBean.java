package org.jonnyzzz.corp.beans;

import java.util.Objects;

public class ArtifactBean {
  private AuthorBean author;
  private LicenseBean license;

  public ArtifactBean(AuthorBean author, LicenseBean license) {
    this.author = author;
    this.license = license;
  }

  public AuthorBean getAuthor() {
    return author;
  }

  public void setAuthor(AuthorBean author) {
    this.author = author;
  }

  public LicenseBean getLicense() {
    return license;
  }

  public void setLicense(LicenseBean license) {
    this.license = license;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ArtifactBean that = (ArtifactBean) o;
    return Objects.equals(author, that.author) &&
            Objects.equals(license, that.license);
  }

  @Override
  public int hashCode() {
    return Objects.hash(author, license);
  }

  @Override
  public String toString() {
    return "ArtifactBean{" +
            "author=" + author +
            ", license=" + license +
            '}';
  }
}

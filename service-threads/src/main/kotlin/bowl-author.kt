package org.jonnyzzz.threads

import org.jonnyzzz.corp.beans.ArtifactBean
import org.jonnyzzz.corp.beans.AuthorBean
import org.jonnyzzz.corp.beans.LicenseBean

fun main(args: Array<String>) {
  val author = AuthorBean("Eugene Petrenko", "@jonnyzzz")
  val license = LicenseBean("MIT")

  val slides = ArtifactBean(author, license)




}
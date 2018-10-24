package org.jonnyzzz.threads

data class KotlinUser(val name: String, val twitter: String)

fun main() {
  val w1 = KotlinUser(name = "Eugene", twitter = "@jonnyzzz")
  val w2 = KotlinUser(twitter = "@jonnyzzz", name = "Eugene")

  println(w1)
  println(w2)


  val (a,b) = w2

  println("a = $a, b = $b")
}




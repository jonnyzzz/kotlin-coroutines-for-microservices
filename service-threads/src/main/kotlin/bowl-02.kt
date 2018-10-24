package org.jonnyzzz.threads

fun main(args: Array<String>) {


  val immutable = "this variable is immutable"

  var mutable = "you can"
  mutable = "change it's value :)"




  //also delegation and lazy values
  val lazyValue by lazy { fibonacci().elementAt(555) }


  val map = mutableMapOf("eugene" to "@jonnyzzz")
  var valueFromMap  by map
}


package org.jonnyzzz.threads


fun main(args: Array<String>) {

//fibonacci
  val seq = sequence {
    var f0 = 0
    var f1 = 1
    while (true) {
      val fN = f0 + f1
      f0 = f1
      f1 = fN

      println("yield - $fN")
      yield(fN)
    }
  }.take(10).forEach {
    println("got   - $it")
  }

}


fun fibonacci() = sequence {
  var f0 = 0
  var f1 = 1
  while (true) {
    val fN = f0 + f1
    f0 = f1
    f1 = fN

    println("yield - $fN")
    yield(fN)
  }
}


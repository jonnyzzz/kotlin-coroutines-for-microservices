package org.jonnyzzz.threads

open class SuspendImpl {
  suspend fun serviceA(msg: String): String = msg
  suspend fun serviceB(msg: String): String = msg
  suspend fun serviceC(msg: String): String = msg


  suspend fun example() {
    val resultA = serviceA("A")
    val resultB = serviceB("B")
    val resultC = serviceC("C")

    println("Result: $resultA + $resultB + $resultC")
  }
}


fun main(args: Array<String>) {
  SuspendImpl()
}
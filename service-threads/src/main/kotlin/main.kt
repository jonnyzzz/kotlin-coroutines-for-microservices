package org.jonnyzzz.threads

import java.util.concurrent.CyclicBarrier

fun main(args: Array<String>) {
  println("Threads example")

  val threads = 10_000
  val sem = CyclicBarrier(threads + 1)
  List(threads) {
    Thread(Runnable { sem.await() }).apply { start() }
  }

  sem.await()
  println("All threads are there!")
}

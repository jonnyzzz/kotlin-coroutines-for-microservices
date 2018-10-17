package org.jonnyzzz.threads

import java.util.concurrent.CyclicBarrier

fun main() {
  println("Threads example")

  val threads = 1_000_000
  val barrier = CyclicBarrier(threads + 1)
  repeat(threads) {
    Thread {
      barrier.await()
    }.start()
  }

  barrier.await()
  println("All threads are there!")
}

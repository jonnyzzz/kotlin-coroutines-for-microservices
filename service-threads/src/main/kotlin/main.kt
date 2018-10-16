package org.jonnyzzz.threads

import java.util.concurrent.CyclicBarrier

fun main(args: Array<String>) {
  println("Threads example")

  val threads = 10_000
  val barrier = CyclicBarrier(threads + 1)
  repeat(threads) {
    Thread{
      barrier.await()
    }.start()
  }

  barrier.await()
  println("All threads are there!")
}



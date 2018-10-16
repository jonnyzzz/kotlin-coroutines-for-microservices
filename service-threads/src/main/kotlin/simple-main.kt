package org.jonnyzzz.threads

import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Semaphore

fun main() {
  println("Threads example")

  val threads = 1_000_000
  val barrier = CyclicBarrier(threads + 1)
  val sem = Semaphore(0)
  repeat(threads) {
    Thread{
      barrier.await()
      sem.acquire()
    }.start()
  }

  barrier.await()
  println("All threads are there!")
}

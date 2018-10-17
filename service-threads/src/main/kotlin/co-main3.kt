package org.jonnyzzz.threads

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun main() = coroutineScope {
  println("Coroutines example")

  val threads = 1_000_000
  val barrier = MutexBarrier(threads + 1)
  repeat(threads) {
    launch {
      barrier.await()
    }.start()
  }

  barrier.await()
  println("All threads are there!")
}

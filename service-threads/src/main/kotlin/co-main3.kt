package org.jonnyzzz.threads

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun main() = coroutineScope {
  println("Coroutines example")

  val threads = 1_000_000
  val barrier = MutexBarrier(threads + 1)
  repeat(threads) {
    launch { //starts a coroutine
      barrier.await() //non-blocking suspend
    }
  }

  barrier.await()
  println("All threads are there!")
}


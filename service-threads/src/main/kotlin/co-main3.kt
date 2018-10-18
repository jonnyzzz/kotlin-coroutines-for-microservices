package org.jonnyzzz.threads

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

suspend fun main() = coroutineScope {
  println("Coroutines example")

  coroutineScope {

    val coroutines = 1_000_000
    val barrier = MutexBarrier(coroutines + 1)
    repeat(coroutines) {
      launch {           //starts a coroutine
        barrier.await()  //non-blocking suspend
      }
    }

    barrier.await()

  }


  println("All threads are there!")
}


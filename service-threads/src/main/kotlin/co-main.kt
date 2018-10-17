package org.jonnyzzz.threads

import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume


suspend fun main() = coroutineScope {

  usedRam()

  val coroutines = 1_000_000
  val barrier = MutexBarrier(coroutines + 1)
  repeat(coroutines) {
    launch {
      barrier.await()
      suspendEternal()
    }
  }

  barrier.await()
  usedRam()

  println("All coroutines are here")
  coroutineContext.cancel()

  Unit
}


suspend fun suspendEternal() = suspendCancellableCoroutine<Unit> { }


fun usedRam() {
  repeat(5) { Runtime.getRuntime().gc() }
  val mem = Runtime.getRuntime().totalMemory() / 1024 / 1024
  println("Total memory is $mem Mb")
}


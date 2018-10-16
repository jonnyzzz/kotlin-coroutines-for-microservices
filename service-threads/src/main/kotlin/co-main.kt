package org.jonnyzzz.threads

import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume


suspend fun main() = coroutineScope {

  usedRam()

  val coroutines = 1_000_000
  val barrier = CyclicBarrier(coroutines + 1)
  repeat(coroutines) {
    launch {
      barrier.await()
      suspendEternal()
    }
  }

  barrier.await()
  usedRam()

  println("All coroutines are here")
  delay(400)
  coroutineContext.cancel()

  Unit
}


class CyclicBarrier(val count: Int) {
  private val callbacks = mutableListOf<Continuation<Unit>>()
  suspend fun await() = suspendCancellableCoroutine<Unit> { cont ->
    val size = synchronized(callbacks) {
      callbacks += cont
      callbacks.size
    }

    if (size >= count) {
      val result = synchronized(callbacks) {
        val result = callbacks.toList()
        callbacks.clear()
        result
      }

      result.forEach { it.resume(Unit) }
    }
  }
}

suspend fun suspendEternal() = suspendCancellableCoroutine<Unit> {  }


fun usedRam() {
  repeat(5) { Runtime.getRuntime().gc() }
  val mem = Runtime.getRuntime().totalMemory() / 1024 / 1024
  println("Total memory is $mem Mb")
}


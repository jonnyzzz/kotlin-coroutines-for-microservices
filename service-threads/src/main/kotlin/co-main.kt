package org.jonnyzzz.threads

import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
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
  coroutineContext.cancel()

  Unit
}


private class CyclicBarrier(private val count: Int) {
  private val callbacks = mutableListOf<Continuation<Unit>>()

  suspend fun await() = suspendCancellableCoroutine<Unit> { cont ->
    synchronized(callbacks) {
      callbacks += cont
      if (callbacks.size >= count) {
        val result = callbacks.toList()
        callbacks.clear()
        result
      } else {
        emptyList()
      }
    }.forEach { it.resume(Unit) }
  }
}

suspend fun suspendEternal() = suspendCancellableCoroutine<Unit> { }


fun usedRam() {
  repeat(5) { Runtime.getRuntime().gc() }
  val mem = Runtime.getRuntime().totalMemory() / 1024 / 1024
  println("Total memory is $mem Mb")
}


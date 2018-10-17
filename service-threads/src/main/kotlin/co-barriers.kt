package org.jonnyzzz.threads

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume


class CyclicBarrier(private val count: Int) {
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

class MutexBarrier(private val count: Int) {
  private val barrier = Mutex(locked = true)
  private var waiting = AtomicInteger(0)

  suspend fun await() {
    val waiting = waiting.incrementAndGet()
    if (waiting == count) barrier.unlock()

    barrier.lock()
    barrier.unlock()
  }
}

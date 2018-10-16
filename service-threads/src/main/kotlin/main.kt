package org.jonnyzzz.threads

import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Semaphore
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
  println("Threads example")

  val threads = 1_000_000
  val barrier = CyclicBarrier(threads + 1)
  val sem = Semaphore(0)

  val chunks = 20
  (1..threads).chunked(threads / chunks).forEachIndexed { index, it ->
    println("Chunk ${index+1} or $chunks, threads: ${it[0]-1}")

    val millis = measureTimeMillis {
      it.forEach {
        try {
          Thread {
            barrier.await()
            sem.acquire()
          }.start()
        } catch (t: Throwable) {
          println("Failed to create thread #$it")
          throw t
        }
      }
    }

    println("    created in $millis ms")
  }

  barrier.await()
  println("All $threads threads are created!")

}




fun simpleMain() {
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

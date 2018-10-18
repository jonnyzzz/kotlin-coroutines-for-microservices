package org.jonnyzzz.threads

import java.io.File
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Semaphore
import kotlin.concurrent.thread

fun main(args: Array<String>) {

  val threads = 4_000

  val mutex = Semaphore(1, true)

  val counters = mutableMapOf<Int, Long>()
  val waitForAll = CyclicBarrier(threads + 1)
  repeat(threads) { threadId ->
    thread(isDaemon = true) {
      waitForAll.await()

      while (true) {
        //mutex here is on purpose to make thread scheduling harder
        mutex.withLock {
          counters[threadId] = counters.getOrDefault(threadId, 0) + 1
        }
      }
    }
  }

  println("Waiting for all threads to start")
  waitForAll.await()

  println("Waiting for a warm up")
  //let the system warm up
  Thread.sleep(10_212)

  println("Warm up complete. Measuring")
  //warm up phase => done
  mutex.withLock {
    counters.clear()
  }

  Thread.sleep(32_212)
  //make all threads stop working!
  mutex.acquire()

  println("Measuring completed")
  println()

  val counts = counters.values.sorted().reversed()

  println("Result of the experiment: sorted counters:")
  for (counter in counts.take(10)) {
    println("  $counter")
  }
  println("  ...")
  for (counter in counts.takeLast(10)) {
    println("  $counter")
  }

  println("   ")
  println("   ")
  println("   ")
  File("report-${System.currentTimeMillis()}.csv").writeText(counts.joinToString("\n"))
}


fun <T> Semaphore.withLock(a : () -> T) : T {
  try {
    acquire()
    return a()
  } finally {
    release()
  }
}
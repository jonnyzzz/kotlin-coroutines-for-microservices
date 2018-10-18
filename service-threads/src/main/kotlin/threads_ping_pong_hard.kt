package org.jonnyzzz.threads

import java.util.concurrent.CyclicBarrier
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread

fun main(args: Array<String>) {

  val threads = 4_000

  val counters = AtomicReference(LongArray(threads))
  val waitForAll = CyclicBarrier(threads + 1)
  repeat(threads) { threadId ->
    thread(isDaemon = true) {
      waitForAll.await()

      while (Thread.currentThread().isAlive) {
        //mutex here is on purpose to make thread scheduling harder
          counters.get()[threadId]++
      }
    }
  }

  println("Waiting for all threads to start")
  waitForAll.await()

  println("Waiting for a warm up")
  //let the system warm up
  Thread.sleep(2_212)

  counters.getAndSet(LongArray(threads))
  println("Warm up complete. Measuring")
  Thread.sleep(32_212)

  println("Measuring completed")
  println()
  //warm up phase => done
  val counts = counters.getAndSet(LongArray(threads)).sorted().reversed()

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
//  File("report-${System.currentTimeMillis()}.csv").writeText(counts.joinToString("\n"))
}

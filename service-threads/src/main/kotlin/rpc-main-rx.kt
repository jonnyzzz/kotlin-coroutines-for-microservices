package org.jonnyzzz.threads

import io.reactivex.Observable
import io.reactivex.functions.Function3
import org.jonnyzzz.grpc.GRPCUtil
import org.jonnyzzz.grpc.generated.ServiceAGrpc
import org.jonnyzzz.grpc.generated.ServiceBGrpc
import org.jonnyzzz.grpc.generated.ServiceCGrpc
import org.jonnyzzz.grpc.generated.StringMessage
import java.util.concurrent.Semaphore


fun main(args: Array<String>) {
  val channel = GRPCUtil.client()
  val serviceA = ServiceAGrpc.newFutureStub(channel)
  val serviceB = ServiceBGrpc.newFutureStub(channel)
  val serviceC = ServiceCGrpc.newFutureStub(channel)

  val completedSemaphore = Semaphore(0)

  val observableA = Observable.fromFuture(serviceA.call(msg("A")))
  val observableB = Observable.fromFuture(serviceB.call(msg("B")))
  val observableC = Observable.fromFuture(serviceC.call(msg("C")))

  Observable.zip<StringMessage, StringMessage, StringMessage, String>(
          observableA,
          observableB,
          observableC,
          Function3 { a, b, c -> "Result: $a + $b + $c" }
  ).subscribe { println(it) }

  //make main thread wait for the results
  completedSemaphore.acquire()
}

private fun msg(text: String) = StringMessage.newBuilder().setText("text $text").build()

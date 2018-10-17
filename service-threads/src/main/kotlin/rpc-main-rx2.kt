package org.jonnyzzz.threads

import io.grpc.stub.StreamObserver
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.Function3
import org.jonnyzzz.grpc.GRPCUtil
import org.jonnyzzz.grpc.generated.ServiceAGrpc
import org.jonnyzzz.grpc.generated.ServiceBGrpc
import org.jonnyzzz.grpc.generated.ServiceCGrpc
import org.jonnyzzz.grpc.generated.StringMessage
import java.util.concurrent.Semaphore


private fun wrap(observer: Observer<in String>): StreamObserver<StringMessage> {
  return object : StreamObserver<StringMessage> {
    override fun onNext(value: StringMessage) {
      observer.onNext(value.text)
    }

    override fun onError(t: Throwable) {
      observer.onError(t)
    }

    override fun onCompleted() {
      observer.onComplete()
    }
  }
}

fun main(args: Array<String>) {
  val channel = GRPCUtil.client()
  val serviceA = ServiceAGrpc.newStub(channel)
  val serviceB = ServiceBGrpc.newStub(channel)
  val serviceC = ServiceCGrpc.newStub(channel)

  val completedSemaphore = Semaphore(0)

  val observableA = object : Observable<String>() {
    override fun subscribeActual(observer: Observer<in String>) {
      serviceA.call(msg("A"), wrap(observer))
    }
  }
  val observableB = object : Observable<String>() {
    override fun subscribeActual(observer: Observer<in String>) {
      serviceB.call(msg("B"), wrap(observer))
    }
  }
  val observableC = object : Observable<String>() {
    override fun subscribeActual(observer: Observer<in String>) {
      serviceC.call(msg("C"), wrap(observer))
    }
  }

  Observable.zip<String, String, String, String>(
          observableA,
          observableB,
          observableC,
          Function3 { a, b, c -> "Result: $a + $b + $c" }
  ).subscribe { println(it) }

  //make main thread wait for the results
  completedSemaphore.acquire()
}

private fun msg(text: String) = StringMessage.newBuilder().setText("text $text").build()

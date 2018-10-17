package org.jonnyzzz.threads

import io.grpc.stub.StreamObserver
import org.jonnyzzz.grpc.GRPCUtil
import org.jonnyzzz.grpc.generated.ServiceAGrpc
import org.jonnyzzz.grpc.generated.ServiceBGrpc
import org.jonnyzzz.grpc.generated.ServiceCGrpc
import org.jonnyzzz.grpc.generated.StringMessage
import java.util.concurrent.Semaphore


fun main(args: Array<String>) {
  val channel = GRPCUtil.client()
  val serviceA = ServiceAGrpc.newStub(channel)
  val serviceB = ServiceBGrpc.newStub(channel)
  val serviceC = ServiceCGrpc.newStub(channel)

  val completedSemaphore = Semaphore(0)
  serviceA.call(msg("A"), object : StreamObserver<StringMessage> {
    lateinit var resultA: StringMessage

    override fun onNext(value: StringMessage) {
      resultA = value
    }

    override fun onError(t: Throwable?) {
      println("Error A: ${t?.message}")
      t?.printStackTrace()
    }

    override fun onCompleted() {
      serviceB.call(msg("B"), object : StreamObserver<StringMessage> {
        lateinit var resultB: StringMessage

        override fun onNext(value: StringMessage) {
          resultB = value
        }

        override fun onError(t: Throwable?) {
          println("Error B: ${t?.message}")
          t?.printStackTrace()
        }

        override fun onCompleted() {
          serviceC.call(msg("C"), object : StreamObserver<StringMessage> {
            lateinit var resultC: StringMessage

            override fun onNext(value: StringMessage) {
              resultC = value
            }

            override fun onError(t: Throwable?) {
              println("Error C: ${t?.message}")
              t?.printStackTrace()
            }

            override fun onCompleted() {
              println("Result: $resultA + $resultB + $resultC")
              completedSemaphore.release()
            }
          })
        }
      })
    }
  })

  //make main thread wait for the results
  completedSemaphore.acquire()
}

private fun msg(text: String) = StringMessage.newBuilder().setText("text $text").build()


fun serviceA(ƒ: (String) -> Unit) = Unit
fun serviceB(ƒ: (String) -> Unit) = Unit
fun serviceC(ƒ: (String) -> Unit) = Unit

fun example() {

  serviceA { a ->
    serviceB { b ->
      serviceC { c ->
        println("Result: $a + $b + $c")
      }
    }
  }
}


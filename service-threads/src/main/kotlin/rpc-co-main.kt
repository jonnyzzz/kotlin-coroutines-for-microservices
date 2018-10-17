package org.jonnyzzz.threads

import io.grpc.stub.ClientCallStreamObserver
import io.grpc.stub.ClientResponseObserver
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.suspendCancellableCoroutine
import org.jonnyzzz.grpc.GRPCUtil
import org.jonnyzzz.grpc.generated.ServiceAGrpc
import org.jonnyzzz.grpc.generated.ServiceBGrpc
import org.jonnyzzz.grpc.generated.ServiceCGrpc
import org.jonnyzzz.grpc.generated.StringMessage
import java.lang.IllegalStateException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


private fun wrap(cont: CancellableContinuation<StringMessage>): StreamObserver<StringMessage> {
  return object : ClientResponseObserver<StringMessage, StringMessage> {
    var result: StringMessage? = null

    override fun beforeStart(requestStream: ClientCallStreamObserver<StringMessage>) {
      cont.invokeOnCancellation { ex ->
        requestStream.cancel("cancelled", ex)
      }
    }

    override fun onNext(value: StringMessage?) {
      result = value
    }

    override fun onError(t: Throwable?) {
      cont.resumeWithException(t ?: IllegalStateException("Failed"))
    }

    override fun onCompleted() {
      val res = result
      if (res != null) {
        cont.resume(res)
      } else {
        cont.resumeWithException(IllegalStateException("Result was not set"))
      }
    }
  }
}

suspend fun ServiceAGrpc.ServiceAStub.call(msg: StringMessage) : StringMessage = suspendCancellableCoroutine { cont ->
  call(msg, wrap(cont))
}

suspend fun ServiceBGrpc.ServiceBStub.call(msg: StringMessage) : StringMessage = suspendCancellableCoroutine { cont ->
  call(msg, wrap(cont))
}

typealias Stub = ServiceCGrpc.ServiceCStub
typealias Message = StringMessage

  suspend fun Stub.call(msg: Message) : Message {
    return suspendCoroutine { cont ->
      call(msg, object : StreamObserver<Message> {
        lateinit var result: Message

        override fun onNext(value: Message) {
          result = value
        }

        override fun onCompleted() = cont.resume(result)

        override fun onError(t: Throwable) {
          cont.resumeWithException(t)
        }
      })
    }
  }

suspend fun main(args: Array<String>) = coroutineScope {
  val channel = GRPCUtil.client()
  val serviceA = ServiceAGrpc.newStub(channel)
  val serviceB = ServiceBGrpc.newStub(channel)
  val serviceC = ServiceCGrpc.newStub(channel)

  val resultA = serviceA.call(msg("A"))
  val resultB = serviceB.call(msg("B"))
  val resultC = serviceC.call(msg("C"))

  println("Result: $resultA + $resultB + $resultC")

}

private fun msg(text: String) = StringMessage.newBuilder().setText("text $text").build()


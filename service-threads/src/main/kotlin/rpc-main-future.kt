package org.jonnyzzz.threads

import org.jonnyzzz.grpc.GRPCUtil
import org.jonnyzzz.grpc.generated.ServiceAGrpc
import org.jonnyzzz.grpc.generated.ServiceBGrpc
import org.jonnyzzz.grpc.generated.ServiceCGrpc
import org.jonnyzzz.grpc.generated.StringMessage


fun main(args: Array<String>) {
  val channel = GRPCUtil.client()
  val serviceA = ServiceAGrpc.newFutureStub(channel)
  val serviceB = ServiceBGrpc.newFutureStub(channel)
  val serviceC = ServiceCGrpc.newFutureStub(channel)

  val resultA = serviceA.call(msg("A")).get()
  val resultB = serviceB.call(msg("B")).get()
  val resultC = serviceC.call(msg("C")).get()

  println("Result: $resultA + $resultB + $resultC")
}

private fun msg(text: String) = StringMessage.newBuilder().setText("text $text").build()


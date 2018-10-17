package org.jonnyzzz.threads

import org.jonnyzzz.grpc.GRPCUtil
import org.jonnyzzz.grpc.generated.ServiceAGrpc
import org.jonnyzzz.grpc.generated.ServiceBGrpc
import org.jonnyzzz.grpc.generated.ServiceCGrpc
import org.jonnyzzz.grpc.generated.StringMessage


fun main(args: Array<String>) {
  val channel = GRPCUtil.client()
  val serviceA = ServiceAGrpc.newBlockingStub(channel)
  val serviceB = ServiceBGrpc.newBlockingStub(channel)
  val serviceC = ServiceCGrpc.newBlockingStub(channel)

  val resultA = serviceA.call(msg("A"))
  val resultB = serviceB.call(msg("B"))
  val resultC = serviceC.call(msg("C"))

  println("Result: $resultA + $resultB + $resultC")
}

private fun msg(text: String) = StringMessage.newBuilder().setText("text $text").build()


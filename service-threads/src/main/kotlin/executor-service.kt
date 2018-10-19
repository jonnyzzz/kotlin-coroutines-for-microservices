package org.jonnyzzz.threads

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jonnyzzz.grpc.GRPCUtil
import org.jonnyzzz.grpc.generated.ServiceAGrpc
import org.jonnyzzz.grpc.generated.ServiceBGrpc
import org.jonnyzzz.grpc.generated.ServiceCGrpc


private val channel by lazy { GRPCUtil.client() }
private val serviceA by lazy { ServiceAGrpc.newStub(channel) }
private val serviceB by lazy { ServiceBGrpc.newStub(channel) }
private val serviceC by lazy { ServiceCGrpc.newStub(channel) }



suspend fun main() = coroutineScope {
  val a = async { serviceA.call("A") }
  val b = async { serviceB.call("B") }
  val c = async { serviceC.call("C") }

  println("""Result: ${a.await()}
                   + ${b.await()}
                   + ${c.await()}""")
}


private fun ServiceAGrpc.ServiceAStub.call(msg: String) {}
private fun ServiceBGrpc.ServiceBStub.call(msg: String) {}
private fun ServiceCGrpc.ServiceCStub.call(msg: String) {}


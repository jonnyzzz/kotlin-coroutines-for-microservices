package org.jonnyzzz.grpc

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.ServerBuilder
import io.grpc.util.RoundRobinLoadBalancerFactory


object GRPCUtil {
  private val defaultPort = 27777

  fun server(serverPort: Int = defaultPort) {

    val serverBuilder = ServerBuilder
            .forPort(serverPort)

    serverBuilder.build().also {
      try {
        it.start()
      } catch (t: Throwable) {
        throw Error("Failed to start server on port $serverPort", t)
      }
    }
  }

  fun client(serverAddress: String = "localhost:$defaultPort"): ManagedChannel {
    return ManagedChannelBuilder
            .forTarget(serverAddress)
            .loadBalancerFactory(RoundRobinLoadBalancerFactory.getInstance())
            .usePlaintext()
            .build()
  }
}


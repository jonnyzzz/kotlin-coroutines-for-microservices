@file:Suppress("unused")

package org.jonnyzzz.threads

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.take
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

object ExampleScope {


  suspend fun main() = coroutineScope {

    val channel = Channel<String>()

    launch { channel.send(serviceA.call("A")) }
    launch { channel.send(serviceB.call("B")) }
    launch { channel.send(serviceC.call("C")) }

    println("Result: ${channel.take(3).toList().joinToString("+")}")
  }


  object BaseService {
    suspend fun call(msg: String): String = msg
  }

  val serviceA = BaseService
  val serviceB = BaseService
  val serviceC = BaseService


}


fun CoroutineScope.countActor(channel: ReceiveChannel<Int>) {
  //this is our Actor
  launch {
    //state is isolated and thread-safe
    var sum = 0
    for (add in channel) {
      sum += add
      println("sum - $sum")
    }
  }
}

fun CoroutineScope.countActorWithReply(
        sumChannel: ReceiveChannel<Int>,
        resetChannel: ReceiveChannel<Unit>) {
  //this is our Actor
  launch {
    //state is isolated and thread-safe
    var sum = 0
    while (isActive) {
      select<Unit> {
        sumChannel.onReceive { add ->
          sum += add
          println("sum - $sum")
        }
        resetChannel.onReceive {
          sum = 0
          println("reset!")
        }
      }
    }
  }
}
val N_WORKERS = 3

  fun CoroutineScope.workerPool(
          data: ReceiveChannel<Int>,
          result: SendChannel<Int>
  ) = repeat(N_WORKERS) {
    launch {
      for (add in data) {
        //do some work
        val work = fibonacci()
                .elementAt(add)
        //send it
        result.send(work)
      }
    }
  }



fun CoroutineScope.generateData(channel: SendChannel<Int>) {
  repeat(5) {
    launch {
      channel.send(random())
    }
  }
}

suspend fun countActor() = coroutineScope {
  val channel = Channel<Int>()
  //start the actor in a coroutine
  countActor(channel)

  //supply data into it
  dataFromServiceA(channel)
  dataFromServiceB(channel)
  dataFromServiceC(channel)
}


suspend fun main() {
  countActor()
}

fun dataFromServiceA(x: Any) = 0
fun dataFromServiceB(x: Any) = 0
fun dataFromServiceC(x: Any) = 0

fun random() = (Math.random() * 1000).toInt()
@file:Suppress("UNUSED_ANONYMOUS_PARAMETER", "unused", "UNUSED_PARAMETER", "RedundantSuspendModifier")

package org.jonnyzzz.threads

import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine


suspend fun main() {
  call()


}

class Msg
class Res
class ServiceA

suspend fun ServiceA.call(msg: Msg): Res

= Res()


class A {
  suspend fun call(msg: Msg): Msg {
    return suspendCoroutine { continuation ->
      // run operation and report
      // results or error
      // via continuation object
    }
  }

}

class B {


  fun call(msg: Msg, callback: Continuation<Res>): Any?


          = null

}



suspend fun call() {
  println(0)

  suspendCoroutine<Unit> {
    println("1")
  }

  println(2)

  suspendCoroutine<Unit> {
    println(3)
  }

}
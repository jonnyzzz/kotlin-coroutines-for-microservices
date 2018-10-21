@file:Suppress("UNUSED_PARAMETER", "unused")

package org.jonnyzzz.threads

object SuspendImplImpl {

  fun callServiceA(msg: String, c: Continuation): Any = msg
  fun callServiceB(msg: String, c: Continuation): Any = msg
  fun callServiceC(msg: String, c: Continuation): Any = msg

  interface Continuation
  abstract class ContinuationImpl(a: Any, b: Any) : Continuation


  fun execute(c: Continuation) {
    //state of the coroutine
    class Machine : ContinuationImpl(this, c) {
      var state: Int = 0
      // and all local variables used between resumes
    }

    //is it a resume or a new call?
    val machine = if (c is Machine) c else Machine()

    //execute next block of code
    when (machine.state) {
      42 -> callServiceA("A", machine)
      // and more states here
    }
  }


  fun execute2(c: Continuation) {
    //state of the coroutine
    class State(c: Continuation) : ContinuationImpl(this, c) {
      var state: Int = 0
      // and all local variables used between resumes
    }

    val inner = c as? State ?: State(c)
    when (inner.state) {
      //executes next block of code
      0 -> {
        //update state
        inner.state = 1
        //do the block before
        callServiceA("A", inner)
      }
      1 -> {
        inner.state = 2
        callServiceB("B", inner)
      }
      2 -> {
        inner.state = 4
        callServiceC("C", inner)
      }
      3 ->
        //label 4
        println("...")
    }
  }
}
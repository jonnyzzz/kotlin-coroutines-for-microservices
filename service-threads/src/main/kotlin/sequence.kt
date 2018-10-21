@file:Suppress("unused")

package org.jonnyzzz.threads


fun main(args: Array<String>) {

//fibonacci
  val seq = sequence {
    var f0 = 0
    var f1 = 1
    while (true) {
      val fN = f0 + f1
      f0 = f1
      f1 = fN

      println("yield - $fN")
      yield(fN)
    }
  }.take(10).forEach {
    println("got   - $it")
  }

}


fun fibonacci() = iterator {
  var f0 = 0
  var f1 = 1
  while (true) {
    val fN = f0 + f1
    f0 = f1
    f1 = fN

    println("yield - $fN")
    yield(fN)
  }
}


interface ExampleSequenceFib<T> {

  /**
   * Yields a value to the [Iterator] being built.
   */
  suspend fun yield(value: T)

  /**
   * Builds a [Sequence] lazily yielding values one by one.
   */
  fun <T> sequence(block: suspend SequenceScope<T>.() -> Unit): Sequence<T>





}

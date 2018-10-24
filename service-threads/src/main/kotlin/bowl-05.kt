package org.jonnyzzz.threads

class Button


fun Button.onClick(handler: () -> Unit) {
  toString()
}


fun main(args: Array<String>) {

  val b = Button()

  b.onClick {
    //event handler
  }

}

/// - add parameter
/// - explain lambda with receiver


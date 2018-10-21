@file:Suppress("unused", "UNUSED_PARAMETER", "MemberVisibilityCanBePrivate", "MoveLambdaOutsideParentheses")

package org.jonnyzzz.threads

class OpenURLExample1 {
  fun open(url: String): String = url
  fun show(text: String) {}


  fun process() {
    val text = open("https://jonnyzzz.com")
    //the 'open' function blocks the thread
    show(text)
  }

}


class OpenURLExample2 {
  suspend fun open(url: String): String = url
  suspend fun show(text: String) {}


  suspend fun process() {
    val text = open("https://jonnyzzz.com")
    //suspend execution, resume later, when ready
    show(text)
  }

}


class OpenURLExample3 {
  fun open(url: String,text: (String) -> Unit) {}
  fun show(text: String) {}


  fun process() {
    open("https://jonnyzzz.com", { text ->
      //let them call us, when text is there
      show(text)
    })
  }

}


@file:Suppress("unused", "UNUSED_PARAMETER", "MemberVisibilityCanBePrivate", "MoveLambdaOutsideParentheses")

package org.jonnyzzz.threads

import okhttp3.OkHttpClient
import java.lang.RuntimeException
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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


class OpenURLExample4 {
  fun onComplete(x: OpenURLExample4.(String) -> Unit) = CompletableFuture<String>()
  fun postTaskToOpenURL(url: String) = this

  suspend fun open(url: String): String {
    //start async page loading
    val future = postTaskToOpenURL(url)

    //specific function to suspend execution
    return suspendCoroutine { continuation ->
      future.onComplete {
        //report error result
        continuation.resumeWithException(RuntimeException(it))
      }
    }
  }

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


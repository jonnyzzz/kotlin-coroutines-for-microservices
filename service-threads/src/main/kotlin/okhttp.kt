@file:Suppress("UNUSED_PARAMETER", "unused")

package org.jonnyzzz.threads

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SuspendingCallExample {


        suspend fun await() =
          suspendCoroutine<String> { continuation ->
            //start async process

            //report successful result
            continuation.resume("result")
            //OR make it fail with an exception
            continuation.resumeWithException(RuntimeException())
          }



  val client = OkHttpClient()

  suspend fun useOkHttp(request: Request) {


  client.newCall(request).await()



  }

}



  suspend fun okSuspendingCall(client: OkHttpClient, request: Request): String {
    return suspendCoroutine { continuation ->
      client
        .newCall(request)
        .enqueue(object : Callback {
          override fun onFailure(call: Call, e: IOException) {
            continuation.resumeWithException(e)
          }
          override fun onResponse(call: Call, response: Response) {
            continuation.resume(response.body()?.string() ?: "")
          }
        })
    }
  }


  suspend fun Call.await(): String {
    return suspendCoroutine { continuation ->
      enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
          continuation.resumeWithException(e)
        }

        override fun onResponse(call: Call, response: Response) {
          continuation.resume(response.body()?.string() ?: "")
        }
      })
    }
  }




suspend fun main(args: Array<String>) {
  SuspendingCallExample::await.name
  ::okSuspendingCall.name
}

package org.jonnyzzz.threads

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

suspend fun main() = coroutineScope {
  val image = async { loadImage() }
  showImage(image.await())
}




fun loadImage() : String = "the image!"
fun showImage(image: String) {}


val UI = GlobalScope.coroutineContext
val Worker = GlobalScope.coroutineContext
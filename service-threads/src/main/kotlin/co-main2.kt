package org.jonnyzzz.threads

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main() = coroutineScope {
  val coroutines = 1_000_000
  repeat(coroutines) {
    launch {
      delay(100)
    }
  }
}

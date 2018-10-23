package org.jonnyzzz.threads

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

  suspend fun main() = coroutineScope {
    val coroutines = 1_000_000
    repeat(coroutines) {
      launch {        //starts a coroutine
        delay(30_000) //non-blocking delay
        print(".")
      }
    }
    //coroutineScope {..} waits launch {..}
  }

val x = CoroutineScope::launch

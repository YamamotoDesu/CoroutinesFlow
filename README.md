# CoroutinesFlow

## Asynchronous Flow
```kt
fun main() {
    runBlocking {
        println("Messaging prime number")
        sendPrimes().collect {
            println("Received prime number $it")
        }
        println("Finished receiving numbers")
        /*
        Messaging prime number
        Received prime number 2
        Received prime number 3
        Received prime number 5
        Received prime number 7
        Received prime number 11
        Received prime number 13
        Received prime number 17
        Received prime number 19
        Received prime number 23
        Received prime number 29
        Finished receiving numbers

        Process finished with exit code 0
         */
    }

}

fun sendPrimes(): Flow<Int> = flow {
    val primesList = listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29)
    primesList.forEach {
        delay(it + 100L)
        emit(it)
    }
}
```

## Creating Flow

```kt
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        sendNumbers().collect {
            println("Received $it")
        }
        /*
        Received 1
        Received 2
        Received 3
        Received 4
        Received 5
        Received 6
        Received 7
        Received 8
        Received 9
        Received 10
         */

        sendNumbersAsFlow().collect {
            println("Received $it")
            /*
            Received 1
            Received 2
            Received 3
             */
        }

        sendNumbersFlowOf().collect {
            println("Received $it")
            /*
            Received One
            Received Two
            Received Three
            Process finished with exit code 0
             */
        }
    }
}

fun sendNumbers()
        = flow {
            for (i in 1..10)
                emit(i)
}

fun sendNumbersAsFlow() = listOf(1, 2, 3).asFlow()

fun sendNumbersFlowOf() = flowOf("One", "Two", "Three")
```

## Coroutines is cold, cancellable

```kt
fun main() {
    runBlocking {
        val numbersFlow = sendNewNumbers()
        println("Flow hasn't started yet.")
        println("Staring flow now.")
        numbersFlow.collect { println(it) } // Flow is cold
        /*
        Flow hasn't started yet.
        Staring flow now.
        1
        2
        3
         */

        val cancellationFlow = sendCancellationNewNumbers()
        withTimeoutOrNull(1000L) {
            cancellationFlow.collect { println(it) }
        }
        /*
        1
        2

        Process finished with exit code 0
         */
    }
}

fun sendNewNumbers() = listOf(1, 2 ,3).asFlow()

fun sendCancellationNewNumbers() = flow {
    listOf(1, 2, 3).forEach {
        delay(400L)
        emit(it)
    }

```

## transformOperator, filterOperator, mapOperator
```kt
fun main() {
    runBlocking {
        mapOperator()
        /*
        mapping 1
        mapping 2
        mapping 3
        mapping 4
        mapping 5
        mapping 6
        mapping 7
        mapping 8
        mapping 9
        mapping 10
         */
        filterOperator()
        /*
        filtering 2
        filtering 4
        filtering 6
        filtering 8
        filtering 10
         */
        transformOperator()
        /*
        transforming Emitting string value 1
        transforming 1
        transforming Emitting string value 2
        transforming 2
        transforming Emitting string value 3
        transforming 3
        transforming Emitting string value 4
        transforming 4
        transforming Emitting string value 5
        transforming 5
        transforming Emitting string value 6
        transforming 6
        transforming Emitting string value 7
        transforming 7
        transforming Emitting string value 8
        transforming 8
        transforming Emitting string value 9
        transforming 9
        transforming Emitting string value 10
        transforming 10
         */
    }
}

suspend fun transformOperator() {
    (1..10).asFlow()
        .transform {
            emit("Emitting string value $it")
            emit(it)
        }
        .collect {
            println("transforming $it")
        }
}

suspend fun filterOperator() {
    (1..10).asFlow()
        .filter {
            it % 2 == 0
        }
        .collect {
            println("filtering $it")
        }
}

suspend fun mapOperator() {
    (1..10).asFlow()
        .map {
            delay(500L)
            "mapping $it"
        }
        .collect {
            println(it)
        }
}
```

## Buffering
```kt
fun main() {
    runBlocking {
        val time = measureTimeMillis {
            generate()
                .collect {
                    delay(300L)
                    println(it)
                }
        }
        println("Collected in $time time ms")
        /*
        1
        2
        3
        Collected in 1258 time ms
         */
        /*
        1
        2
        3
        Collected in 1127 ms
         */
        val bufferTime = measureTimeMillis {
            generate()
                .buffer()
                .collect {
                    delay(300L)
                    println(it)
                }
        }
        println("Collected in $bufferTime bufferTime ms")
        /*
        1
        2
        3
        Collected in 1127 ms
         */
    }
}

fun generate() = flow {
    for (i in 1..3) {
        delay(100L)
        emit(i)
    }
}
```

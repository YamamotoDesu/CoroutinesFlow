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

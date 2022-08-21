# CoroutinesFlow

## AsynchronousFlow
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

import kotlinx.coroutines.flow.asFlow
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
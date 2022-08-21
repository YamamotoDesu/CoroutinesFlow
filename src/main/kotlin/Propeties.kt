import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

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
}
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

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
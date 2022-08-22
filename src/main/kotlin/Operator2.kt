import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        takeOperator()
        /*
        1
        2
         */
        reduceOperator()
        /*
        Factorial of 10 is 3628800
         */
        flowOnOperator()
        /*
        1
        2
        3
        4
        5
        6
        7
        8
        9
        10
         */
    }
}

suspend fun flowOnOperator() {
    (1..10).asFlow()
        .flowOn(Dispatchers.IO)
        .collect {
            println(it)
        }
}

suspend fun reduceOperator() {
    val size = 10
    val factorial = (1..size).asFlow()
        .reduce { accumulator, value ->
            accumulator * value
        }
    println("Factorial of $size is $factorial")
}

suspend fun takeOperator() {
    (1..10).asFlow()
        .take(2)
        .collect {
            println(it)
        }
}
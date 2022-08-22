import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

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
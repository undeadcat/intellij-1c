package coreUtils

import java.time.Duration

data class RetryStrategy(private val init: () -> Unit,
                         private val continueCondition: () -> Boolean,
                         private val exceptions: List<Class<out Exception>>) {
    fun <T> execute(function: () -> T): T {
        var result: T? = null
        execute { result = function() }
        return result!!
    }

    fun execute(function: () -> (Unit)) {
        init()
        while (true) {
            try {
                function()
                return
            } catch(e: Exception) {
                if (exceptions.all { !it.isInstance(e) }
                        || !continueCondition())
                    throw e
            }
        }
    }

    fun byCount(count: Int): RetryStrategy {
        var currentCount = 0
        return copy(continueCondition = {
            currentCount++
            continueCondition() && currentCount < count
        })
    }

    fun exceptionType(clazz: Class<out Exception>): RetryStrategy {
        return copy(exceptions = exceptions + clazz)
    }

    fun withDelay(time: Duration): RetryStrategy {
        return copy(continueCondition = {
            Thread.sleep(time.toMillis())
            continueCondition()
        })
    }

    fun withTimeout(timeout: Duration): RetryStrategy {
        var startTime = 0L
        return copy(init = {
            init()
            startTime = System.currentTimeMillis()
        }, continueCondition = {
            continueCondition() && (System.currentTimeMillis() - startTime < timeout.toMillis())
        })
    }

    companion object Builder {
        fun Retry() = RetryStrategy({}, { true }, emptyList())
    }

}
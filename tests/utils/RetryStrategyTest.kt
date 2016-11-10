package utils

import coreUtils.RetryStrategy
import org.hamcrest.core.Is
import org.junit.Assert
import org.junit.Test

class RetryStrategyTest {
    @Test
    fun testSimple() {
        val strategy = RetryStrategy.Retry().byCount(5).exceptionType(TestException::class.java)
        var counter = 0
        val failNTimes = fun(n: Int): () -> Unit {
            return {
                if (counter++ < n)
                    throw TestException()
            }
        }

        strategy.execute(failNTimes(4))
        Assert.assertThat(counter, Is.`is`(5))
        counter = 0
        try {
            strategy.execute(failNTimes(5))
            Assert.fail("Expected exception")
        } catch (e: TestException) {
        }


    }

    class TestException : Exception() {

    }
}


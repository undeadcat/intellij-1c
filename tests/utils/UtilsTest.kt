package utils

import coreUtils.isEquivalentTo
import org.hamcrest.core.Is
import org.junit.Assert
import org.junit.Test

class UtilsTest {
    @Test
    fun isEquivalentTo() {
        Assert.assertThat(listOf(1, 2, 3).isEquivalentTo(listOf(3, 2, 1)), Is.`is`(true))
        Assert.assertThat(listOf(1, 2, 3).isEquivalentTo(listOf(3, 2)), Is.`is`(false))
        Assert.assertThat(listOf(1, 2, 3).isEquivalentTo(listOf(3, 2, 1,4)), Is.`is`(false))
    }

}
package utils

import com.simple1c.dataSources.PostgresConnectionString
import org.hamcrest.core.Is
import org.junit.Assert
import org.junit.Test

class PostgresConnectionStringTest {
    @Test
    fun validConnectionString() {
        val input = "host=localhost;port=5432;Database=db_name;Username=user_name;Password=strong_password"
        val connectionString = PostgresConnectionString.tryParse(input)
        Assert.assertNotNull(connectionString)
        Assert.assertThat(connectionString!!.format(), Is.`is`(input.toLowerCase()))
    }

    @Test
    fun allowEmptyComponents() {
        val input = "host=;port=5432;Database=;Username=;Password="
        val connectionString = PostgresConnectionString.tryParse(input)
        Assert.assertNotNull(connectionString)
        Assert.assertThat(connectionString!!.password, Is.`is`(""))
        Assert.assertThat(connectionString.host, Is.`is`(""))
    }

    @Test
    fun allComponentsMustBePresent() {
        val input = "port=5432;Database=db_name;Username=user_name;Password=strong_password"
        val connectionString = PostgresConnectionString.tryParse(input)
        Assert.assertNull(connectionString)
    }

    @Test
    fun invalidPort() {
        val input = "Host=localhost;port=not_a_port;Database=db_name;Username=user_name;Password=strong_password"
        val connectionString = PostgresConnectionString.tryParse(input)
        Assert.assertNull(connectionString)
    }

    @Test
    fun completelyInvalid() {
        val input = "###"
        val connectionString = PostgresConnectionString.tryParse(input)
        Assert.assertNull(connectionString)
    }

}
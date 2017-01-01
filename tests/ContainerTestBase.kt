import com.intellij.openapi.application.ApplicationManager
import com.simple1c.configuration.ProjectService
import com.simple1c.lang.ISchemaStore
import org.picocontainer.MutablePicoContainer

open class ContainerTestBase : com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase() {

    companion object {
        @JvmStatic
        protected val schemaStore = FakeSchemaStore()
    }

    override fun setUp() {
        super.setUp()
        registerImplementation(schemaStore, ISchemaStore::class.java)
        schemaStore.clear()
    }

    private fun <T> registerImplementation(implementation: T, interfaceClazz: Class<T>) {
        val container = getContainer(interfaceClazz)
        container.unregisterComponent(interfaceClazz)
        container.registerComponentInstance(interfaceClazz, implementation)
    }

    private fun <T> getContainer(interfaceClazz: Class<T>): MutablePicoContainer {
        val container = if (interfaceClazz.getAnnotationsByType(ProjectService::class.java).any())
            myFixture.project.picoContainer
        else ApplicationManager.getApplication().picoContainer
        return container as MutablePicoContainer
    }

}
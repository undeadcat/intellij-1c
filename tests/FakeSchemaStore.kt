import com.intellij.psi.PsiFile
import com.simple1c.lang.ISchemaStore
import com.simple1c.lang.PropertyInfo
import com.simple1c.lang.TableSchema

class FakeSchemaStore : ISchemaStore {
    private val tables = hashMapOf<String, TableSchema>()

    override fun getSchemaOrNull(file: PsiFile, tableName: String): TableSchema? {
        if (tableName.isEmpty())
            throw RuntimeException("Assertion failure. Should not query for empty table name")
        return tables[tableName.toLowerCase()]
    }

    override fun getTables(file: PsiFile): List<String> {
        return tables.values.map { it.name }
    }

    fun addColumns(tableName: String) {
        doAddColumns(tableName, emptyList<PropertyInfo>())
    }

    fun addColumns(tableName: String, vararg columns: PropertyInfo) {
        doAddColumns(tableName, columns.toList())
    }

    fun addColumns(tableName: String, vararg columns: String) {
        doAddColumns(tableName, columns.map { PropertyInfo(it, emptyList()) })
    }

    private fun doAddColumns(tableName: String, columns: List<PropertyInfo>) {
        tables.put(tableName.toLowerCase(), TableSchema(tableName, columns))
    }

    fun clear() {
        tables.clear()
    }
}
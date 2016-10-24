package com.simple1c.impl

interface ISchemaStore {
    fun getColumns(tableName: String?): Iterable<String>
    fun getTables(): Iterable<String>
}

class FakeSchemaStore : ISchemaStore {
    //    private val tables = hashMapOf<String, List<String>>()
    private val tables = hashMapOf(
            Pair("Справочник.Контрагенты", listOf("ИНН", "КПП", "Ссылка")),
            Pair("Справочник.Сотрудники", listOf("Фамилия", "Имя", "Отчетство", "Ссылка")),
            Pair("Документ.ПоступлениеНаРасчетныйСчет", listOf("Сумма", "Дата", "Номер", "Контрагент", "Ссылка"))
    )

    override fun getColumns(tableName: String?): Iterable<String> {
        if (tableName == null)
            return tables.flatMap { it.value }
        return tables.getOrElse(tableName, { emptyList<String>() })
    }


    override fun getTables(): Iterable<String> {
        return tables.keys
    }

    fun addColumns(tableName: String, vararg columns: String) {
        tables.put(tableName, columns.toList())
    }

    fun clear() {
        tables.clear()
    }

    companion object {
        var instance = FakeSchemaStore()
    }
}
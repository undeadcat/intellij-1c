package com.simple1c.impl

class SchemaStore : ISchemaStore {
    private val tables = hashMapOf(
            Pair("Справочник.Контрагенты", listOf("ИНН", "КПП")),
            Pair("Справочник.Сотрудники", listOf("Фамилия", "Имя", "Отчетство")),
            Pair("Документ.ПоступлениеНаРасчетныйСчет", listOf("Дата", "Сумма"))
    )

    override fun getTables(): Iterable<String> {
        return tables.keys;
    }

    override fun getColumns(tableName: String?): Iterable<String> {
        if (tableName == null)
            return tables.flatMap { it.value }
        return tables.getOrElse(tableName, { emptyList<String>() })
    }
}


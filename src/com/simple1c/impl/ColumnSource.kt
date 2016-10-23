package com.simple1c.impl

class ColumnSource {
    private val columns = hashMapOf(
            Pair("Справочник.Контрагенты", listOf("ИНН", "КПП")),
            Pair("Справочник.Сотрудники", listOf("Фамилия", "Имя", "Отчетство")),
            Pair("Документ.ПоступлениеНаРасчетныйСчет", listOf("Дата", "Сумма"))
    )

    fun getAll(tableName: String?): Iterable<String> {
        if (tableName == null)
            return columns.flatMap { it.value }
        return columns.getOrDefault(tableName, emptyList())
    }
}
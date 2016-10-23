package com.simple1c.impl

class TableSource {
    private val tables = listOf("Справочник.Контрагенты",
            "Справочник.Сотрудники",
            "Документ.ПоступлениеНаРасчетныйСчет")

    fun getAll(): Iterable<String> {
        return tables

    }
}


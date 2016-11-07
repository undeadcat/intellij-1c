package com.simple1c.remote


data class TranslationRequest(var connectionString: String, var query: String) {

}

data class TranslationResult(var result: String?, var error: String?) {

}

data class ExecuteQueryRequest(var connectionString: String, var query: String) {

}

data class QueryResult(var columns: List<Column>, var rows: List<Array<Any>>) {

    data class Column(var name: String, var maxLength: Int, var dataType: String) {

    }
}

package com.simple1c.dataSources

import com.intellij.psi.PsiFile
import java.util.*

class DataSourceAssociations {

    private val map = HashMap<String, UUID>()
    fun getOrNull(psiFile: PsiFile): DataSource? {
        val storage = DataSourceStorage.instance(psiFile.project)
        val allDataSources = storage.getAll()
        if (allDataSources.count() == 1)
            return allDataSources.first()
        val id = map[psiFile.virtualFile.path]
        return if (id == null) null else storage.getByIdOrNull(id)

    }

    fun save(file: PsiFile, dataSource: DataSource) {
        map[file.virtualFile.path] = dataSource.id
    }
}
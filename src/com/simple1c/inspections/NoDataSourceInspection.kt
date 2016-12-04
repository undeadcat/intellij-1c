package com.simple1c.inspections

import com.intellij.codeInspection.*
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.simple1c.dataSources.DataSourceStorage
import com.simple1c.ui.Actions.NewDataSourceAction


class NoDataSourceInspection(private val createDataSourceQuickFix: CreateDataSourceQuickFix) : LocalInspectionTool(), DumbAware {

    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<out ProblemDescriptor> {
        if (DataSourceStorage.instance(file.project).getAll().any())
            return ProblemDescriptor.EMPTY_ARRAY
        return arrayOf(manager.createProblemDescriptor(file, "No data sources configured in project", true, ProblemHighlightType.GENERIC_ERROR_OR_WARNING, isOnTheFly, createDataSourceQuickFix))
    }

    class CreateDataSourceQuickFix(val newDataSourceAction: NewDataSourceAction) : LocalQuickFix {
        override fun getFamilyName(): String {
            return "Create data source"
        }

        override fun applyFix(p0: Project, p1: ProblemDescriptor) {
            newDataSourceAction.run(p0)
        }

    }
}

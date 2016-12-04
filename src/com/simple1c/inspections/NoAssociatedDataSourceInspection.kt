package com.simple1c.inspections

import com.intellij.codeInspection.*
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.PopupStep
import com.intellij.openapi.ui.popup.util.BaseListPopupStep
import com.intellij.psi.PsiFile
import com.simple1c.dataSources.DataSource
import com.simple1c.dataSources.DataSourceAssociations
import com.simple1c.dataSources.DataSourceStorage
import coreUtils.isEmpty

class NoAssociatedDataSourceInspection(private val dataSourceAssociations: DataSourceAssociations) : LocalInspectionTool(), DumbAware {
    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<out ProblemDescriptor> {
        if (dataSourceAssociations.getOrNull(file) != null
                || DataSourceStorage.instance(file.project).getAll().isEmpty())
            return emptyArray()
        val message = "This file is not associated with a data source.\n" +
                "Select one to enable completion and query execution"
        val quickFix = SelectDataSourceQuickFix(dataSourceAssociations)
        return arrayOf(manager.createProblemDescriptor(file, message, true, ProblemHighlightType.GENERIC_ERROR_OR_WARNING, isOnTheFly, quickFix))
    }

    class SelectDataSourceQuickFix(private val dataSourceAssociations: DataSourceAssociations) : LocalQuickFix {
        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            val dataSources = DataSourceStorage.instance(project).getAll()
            val popup = JBPopupFactory.getInstance().createListPopup(object : BaseListPopupStep<DataSource>(
                    "Select data source", dataSources.toList()) {
                override fun getTextFor(value: DataSource): String {
                    return value.getName()
                }

                override fun isSpeedSearchEnabled(): Boolean {
                    return true
                }

                override fun onChosen(selectedValue: DataSource, finalChoice: Boolean): PopupStep<*>? {
                    dataSourceAssociations.save(descriptor.psiElement.containingFile, selectedValue)
                    return FINAL_CHOICE
                }
            })
            popup.showCenteredInCurrentWindow(project)
        }

        override fun getFamilyName(): String {
            return "Select a data source"
        }

    }
}



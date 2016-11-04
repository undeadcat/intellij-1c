package com.simple1c.lang

import com.intellij.codeInspection.InspectionManager
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.psi.PsiFile


class NoDataSourceInspection : LocalInspectionTool() {

    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<out ProblemDescriptor> {
        return arrayOf(manager.createProblemDescriptor(file, "No data source", true, ProblemHighlightType.GENERIC_ERROR_OR_WARNING, isOnTheFly))
    }
}
package com.simple1c.lang

import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class _1cHighlighterFactory : SyntaxHighlighterFactory() {

    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): com.intellij.openapi.fileTypes.SyntaxHighlighter {
        return SyntaxHighlighter()
    }
}
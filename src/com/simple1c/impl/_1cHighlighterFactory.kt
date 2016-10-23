package com.simple1c.impl

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class _1cHighlighterFactory : com.intellij.openapi.fileTypes.SyntaxHighlighterFactory() {

    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): com.intellij.openapi.fileTypes.SyntaxHighlighter {
        return SyntaxHighlighter()
    }
}
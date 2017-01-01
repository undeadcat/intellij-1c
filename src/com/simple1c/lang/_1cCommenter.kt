package com.simple1c.lang

import com.intellij.lang.Commenter

class _1cCommenter : Commenter {
    override fun getCommentedBlockCommentPrefix(): String? {
        return null
    }

    override fun getCommentedBlockCommentSuffix(): String? {
        return null
    }

    override fun getBlockCommentPrefix(): String? {
        return null
    }

    override fun getBlockCommentSuffix(): String? {
        return null
    }

    override fun getLineCommentPrefix(): String? {
        return "//"
    }
}

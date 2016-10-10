package com.simple1c.boilerplate;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class _1cFile extends PsiFileBase {
    public _1cFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, _1cLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return _1cFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "1c query File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}

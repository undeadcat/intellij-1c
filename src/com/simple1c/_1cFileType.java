package com.simple1c;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;


public class _1cFileType extends LanguageFileType {
    public static final _1cFileType INSTANCE = new _1cFileType();

    private _1cFileType() {
        super(_1cLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "1c file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "1c query language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "1c";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return _1cIcons.FILE;
    }
}


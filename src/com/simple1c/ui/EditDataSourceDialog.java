package com.simple1c.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.simple1c.dataSources.DataSource;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class EditDataSourceDialog extends DialogWrapper {
    public final DataSource dataSource;
    private JTextField nameField;
    private JRadioButton comRadioButton;
    private JRadioButton simple1cButton;
    private JTextField hostInput;
    private JTextField databaseInput;
    private JPanel content;

    public EditDataSourceDialog(@Nullable Project project, DataSource dataSource) {
        super(project, false, IdeModalityType.IDE);
        this.dataSource = dataSource;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return content;
    }

    public boolean go() {
        init();
        return showAndGet();
    }
}

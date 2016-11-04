package com.simple1c.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.simple1c.dataSources.DataSource;
import com.simple1c.dataSources.PostgresConnectionString;
import coreUtils.CoreUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class EditDataSourceDialog extends DialogWrapper {
    public DataSource dataSource;
    private JTextField nameTextInput;
    private JTextField hostInput;
    private JTextField databaseInput;
    private JPanel content;
    private JTextField passwordTextInput;
    private JTextField userTextInput;
    private JTextField portTextInput;
    private JTextField connectionStringInput;

    public EditDataSourceDialog(@Nullable Project project, DataSource dataSource) {
        super(project, false, IdeModalityType.IDE);
        this.dataSource = dataSource;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return content;
    }

    @Override
    protected void init() {
        super.init();
        content.setMinimumSize(new Dimension(600, 400));
        nameTextInput.setText(dataSource.getName());
        hostInput.setText(dataSource.getConnectionString().getHost());
        Integer port = dataSource.getConnectionString().getPort();
        portTextInput.setText(Integer.toString(port));
        userTextInput.setText(dataSource.getConnectionString().getUser());
        passwordTextInput.setText(dataSource.getConnectionString().getPassword());
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        ValidationInfo result = super.doValidate();
        if (CoreUtils.parseIntOrNull(portTextInput.getText()) == null)
            return new ValidationInfo("Port must be an integer", portTextInput);
        return result;
    }

    public boolean go() {
        init();
        boolean result = showAndGet();
        if (!result)
            return false;
        PostgresConnectionString connectionString = new PostgresConnectionString(hostInput.getText(),
                Integer.parseInt(portTextInput.getText()),
                databaseInput.getText(),
                userTextInput.getText(),
                passwordTextInput.getText());
        dataSource = dataSource.copy(dataSource.getId(), nameTextInput.getText(), connectionString);
        return true;
    }
}

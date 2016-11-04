package com.simple1c.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.util.containers.ContainerUtil;
import com.simple1c.dataSources.DataSource;
import com.simple1c.dataSources.PostgresConnectionString;
import coreUtils.CoreUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class EditDataSourceDialog extends DialogWrapper {
    public DataSource dataSource;
    private JTextField nameInput;
    private JTextField hostInput;
    private JTextField databaseInput;
    private JPanel content;
    private JTextField passwordInput;
    private JTextField userInput;
    private JTextField portInput;
    private JTextField connectionStringInput;
    private JButton testButton;
    private JLabel testResult;

    public EditDataSourceDialog(@Nullable Project project, DataSource dataSource) {
        super(project, false, IdeModalityType.IDE);
        this.dataSource = dataSource;
    }

    public boolean go() {
        init();
        boolean result = showAndGet();
        if (!result)
            return false;
        PostgresConnectionString connectionString = tryGatherConnectionString();
        if (connectionString == null)
            throw new RuntimeException("Assertion failure. expected connection string to be not null");
        dataSource = dataSource.copy(dataSource.getId(), nameInput.getText(), connectionString);
        return true;
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
        nameInput.setText(dataSource.getName());
        scatterConnectionString(dataSource.getConnectionString());
        connectionStringInput.setText(dataSource.getConnectionString().format());
        List<JTextField> partInputs = ContainerUtil.list(hostInput, portInput, databaseInput, userInput, passwordInput);
        partInputs.forEach(input -> addChangeListener(input, e -> {
            EventQueue.invokeLater(() -> {
                PostgresConnectionString connectionString = tryGatherConnectionString();
                if (connectionString != null) {
                    String formatted = connectionString.format();
                    String current = connectionStringInput.getText();
                    if (!Objects.equals(current, formatted))
                        connectionStringInput.setText(formatted);
                }
            });

        }));
        addChangeListener(connectionStringInput, e -> {
            EventQueue.invokeLater(() -> {
                PostgresConnectionString connectionString = PostgresConnectionString.tryParse(e);
                if (connectionString != null)
                    scatterConnectionString(connectionString);
            });
        });
        connectionStringInput.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleEnabled(false, partInputs);
                toggleEnabled(true, connectionStringInput);
            }
        });

        partInputs.forEach(field -> field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleEnabled(true, partInputs);
                toggleEnabled(false, connectionStringInput);
            }
        }));

        testButton.addActionListener(e -> {
            testResult.setVisible(true);
            testResult.setText("Ok");
        });
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        ValidationInfo result = super.doValidate();
        if (CoreUtils.parseIntOrNull(portInput.getText()) == null)
            return new ValidationInfo("Port must be an integer", portInput);
        return result;
    }

    private void toggleEnabled(boolean enabled, JTextField... inputs) {
        toggleEnabled(enabled, ContainerUtil.list(inputs));
    }

    private void toggleEnabled(boolean enabled, Collection<JTextField> inputs) {
        inputs.forEach(field -> field.setEnabled(enabled));
    }

    private void addChangeListener(JTextField textField, Consumer<String> listener) {
        Document document = textField.getDocument();
        document.addDocumentListener(new DocumentListener() {
            String lastValue = getText();

            @Override
            public void insertUpdate(DocumentEvent e) {
                fireListener();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fireListener();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                fireListener();
            }

            private void fireListener() {
                String newText = getText();
                if (Objects.equals(newText, lastValue))
                    return;
                lastValue = newText;
                listener.accept(newText);
            }

            private String getText() {
                try {
                    return document.getText(0, document.getLength());
                } catch (BadLocationException ignored) {
                    return "";
                }
            }
        });
    }

    private void scatterConnectionString(PostgresConnectionString myConnectionString) {
        hostInput.setText(myConnectionString.getHost());
        portInput.setText(Integer.toString(myConnectionString.getPort()));
        userInput.setText(myConnectionString.getUser());
        passwordInput.setText(myConnectionString.getPassword());
    }

    private PostgresConnectionString tryGatherConnectionString() {
        Integer port = CoreUtils.parseIntOrNull(portInput.getText());
        if (port == null)
            return null;
        return new PostgresConnectionString(hostInput.getText(),
                port,
                databaseInput.getText(),
                userInput.getText(),
                passwordInput.getText());
    }
}

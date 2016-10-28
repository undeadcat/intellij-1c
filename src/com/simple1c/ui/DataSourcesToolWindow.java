package com.simple1c.ui;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.ui.components.JBList;
import com.intellij.util.containers.ContainerUtil;
import com.simple1c.dataSources.DataSource;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;

public class DataSourcesToolWindow {
    public DefaultActionGroup actionGroup = new DefaultActionGroup();
    private JPanel content;
    private JBList dataSourcesList;

    public DataSourcesToolWindow() {
    }

    public void setDataSources(@NotNull Iterable<DataSource> dataSources) {
        List<String> dataSourcesDataList = ContainerUtil.map(dataSources, DataSource::getName);
        String[] result = new String[dataSourcesDataList.size()];
        dataSourcesDataList.toArray(result);
        (dataSourcesList).setListData(result);
    }

    public JComponent getContent() {
        return content;
    }

    public void initUi() {
        dataSourcesList.setEmptyText("Click 'Add' to create a data source");
        final ActionToolbar actionToolbar = ActionManager.getInstance().createActionToolbar("DataSourcesWindow", actionGroup, true);
        actionToolbar.setTargetComponent(this.content);
    }
}

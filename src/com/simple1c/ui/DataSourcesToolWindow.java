package com.simple1c.ui;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.PopupHandler;
import com.intellij.ui.components.JBList;
import com.intellij.util.containers.ContainerUtil;
import com.simple1c.dataSources.DataSource;
import com.simple1c.dataSources.DataSourceStorage;
import com.simple1c.ui.Actions.MyActionConstants;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public class DataSourcesToolWindow extends SimpleToolWindowPanel implements DataProvider {
    private final DataSourceStorage dataSourceStorage;
    private JPanel content;
    private JBList dataSourcesList;
    public static DataKey<DataSource> DataSourceKey = DataKey.create("SelectedDataSource");

    public DataSourcesToolWindow(DataSourceStorage dataSourceStorage) {
        super(true, true);
        this.dataSourceStorage = dataSourceStorage;
    }

    public void initContent() {
        dataSourcesList.setEmptyText("Click 'Add' to create a data source");
        setDataSources(dataSourceStorage.getAll());
        dataSourceStorage.addUpdateListener(this::setDataSources);
        ActionManager actionManager = ActionManager.getInstance();
        final ActionToolbar actionToolbar = actionManager
                .createActionToolbar(MyActionConstants.Places.DataSourcesToolbar,
                        (ActionGroup) actionManager.getAction(MyActionConstants.Groups.DataSourcesToolbar), true);
        actionToolbar.setTargetComponent(this.content);
        setContent(content);
        setToolbar(actionToolbar.getComponent());
        PopupHandler.installPopupHandler(dataSourcesList, MyActionConstants.Groups.DataSources,
                MyActionConstants.Places.DataSources);
    }

    private void setDataSources(@NotNull Iterable<DataSource> dataSources) {
        List<String> dataSourcesDataList = ContainerUtil.map(dataSources, DataSource::getName);
        String[] result = new String[dataSourcesDataList.size()];
        dataSourcesDataList.toArray(result);
        //noinspection unchecked
        dataSourcesList.setListData(result);
    }

    @Nullable
    @Override
    public Object getData(@NonNls String dataId) {
        if (DataSourceKey.is(dataId))
            return dataSourcesList.getModel().getElementAt(dataSourcesList.getSelectedIndex());
        return null;
    }
}

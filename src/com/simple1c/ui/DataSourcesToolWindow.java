package com.simple1c.ui;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.PopupHandler;
import com.intellij.ui.components.JBList;
import com.intellij.ui.content.ContentManager;
import com.intellij.util.containers.ContainerUtil;
import com.simple1c.dataSources.DataSource;
import com.simple1c.dataSources.DataSourceStorage;
import com.simple1c.ui.Actions.CreateQueryAction;
import com.simple1c.ui.Actions.EditDataSourceAction;
import com.simple1c.ui.Actions.MyActionConstants;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public class DataSourcesToolWindow
        //extend this because DataProvider implementation must be in the control tree
        extends SimpleToolWindowPanel implements DataProvider {
    private final DataSourceStorage dataSourceStorage;
    private JPanel content;
    private JBList dataSourcesList;
    public static DataKey<DataSource> DataSourceKey = DataKey.create("SelectedDataSource");

    public DataSourcesToolWindow(Project project) {
        super(true, true);
        this.dataSourceStorage = DataSourceStorage.instance(project);
    }

    public void initContent(@NotNull ToolWindow toolWindow) {
        ContentManager contentManager = toolWindow.getContentManager();
        JComponent component = createComponent(new EditDataSourceAction(), new CreateQueryAction());
        toolWindow.getContentManager().addContent(contentManager.getFactory().createContent(component, "", true));
    }

    private JComponent createComponent(AnAction... actions) {
        dataSourcesList.setEmptyText("Click 'Add' to create a data source");
        setDataSources(dataSourceStorage.getAll());
        dataSourceStorage.addUpdateListener(this::setDataSources);
        ActionManager actionManager = ActionManager.getInstance();
        final ActionToolbar actionToolbar = actionManager
                .createActionToolbar(ActionPlaces.UNKNOWN,
                        (ActionGroup) actionManager.getAction(MyActionConstants.Groups.DataSourcesToolbar), true);
        actionToolbar.setTargetComponent(this.content);
        setContent(content);
        setToolbar(actionToolbar.getComponent());
        DefaultActionGroup popupActionGroup = new DefaultActionGroup();
        popupActionGroup.addAll(actions);
        PopupHandler.installPopupHandler(dataSourcesList, popupActionGroup, ActionPlaces.UNKNOWN, actionManager);
        return getComponent();
    }

    private void setDataSources(@NotNull Iterable<DataSource> dataSources) {
        List<ListItem> listItems = ContainerUtil.map(dataSources, dataSource -> {
            ListItem listItem = new ListItem();
            listItem.DisplayName = dataSource.getName();
            listItem.DataSource = dataSource;
            return listItem;
        });
        ListItem[] result = new ListItem[listItems.size()];
        listItems.toArray(result);
        //noinspection unchecked
        dataSourcesList.setListData(result);
    }

    @Nullable
    @Override
    public Object getData(@NonNls String dataId) {
        if (DataSourceKey.is(dataId))
            return ((ListItem) dataSourcesList.getModel()
                    .getElementAt(dataSourcesList.getSelectedIndex())).DataSource;
        return null;
    }


    private class ListItem {
        String DisplayName;
        DataSource DataSource;

        @Override
        public String toString() {
            return DisplayName;
        }
    }
}

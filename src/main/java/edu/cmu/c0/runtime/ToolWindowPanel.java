package edu.cmu.c0.runtime;

import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.table.JBTable;

public class ToolWindowPanel extends SimpleToolWindowPanel {
    private final JBTable myTable;

    public ToolWindowPanel() {
        super(true);
        myTable = new JBTable(TableModel.getInstance());
        add(myTable);
    }
}

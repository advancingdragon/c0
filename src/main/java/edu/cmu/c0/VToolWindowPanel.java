package edu.cmu.c0;

import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.table.JBTable;

public class VToolWindowPanel extends SimpleToolWindowPanel {
    private final JBTable myTable;

    public VToolWindowPanel() {
        super(true);
        myTable = new JBTable(VTableModel.getInstance());
        add(myTable);
    }
}

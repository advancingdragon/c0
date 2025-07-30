package edu.cmu.c0;

import com.intellij.ui.components.JBList;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;

public class VTableCellRenderer extends JBList<String> implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {
        if (value instanceof String[] array) {
            setListData(array);
            final var rowHeight = array.length > 0 ? array.length * 20 : 20;
            table.setRowHeight(row, rowHeight);
        }
        return this;
    }
}

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
            // adjust row height based on font ascent
            final var fontMetrics = table.getFontMetrics(table.getFont());
            final var a = fontMetrics.getAscent() + 8;
            final var rowHeight = array.length > 0 ? array.length * a : a;
            table.setRowHeight(row, rowHeight);
        }
        return this;
    }
}

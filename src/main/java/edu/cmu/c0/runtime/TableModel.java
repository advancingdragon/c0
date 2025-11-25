package edu.cmu.c0.runtime;

import javax.swing.table.AbstractTableModel;

import org.jetbrains.annotations.NotNull;
import scala.collection.Seq;
import scala.collection.Seq$;
import viper.silicon.state.CheckInfo;
import viper.silver.ast.TranslatedPosition;

public class TableModel extends AbstractTableModel {
    private static TableModel SINGLETON_INSTANCE = null;
    @NotNull
    private Seq<CheckInfo> myCheckList;

    public static TableModel getInstance() {
        if (SINGLETON_INSTANCE == null) {
            SINGLETON_INSTANCE = new TableModel();
        }
        return SINGLETON_INSTANCE;
    }

    public TableModel() {
        myCheckList = (Seq<CheckInfo>) Seq$.MODULE$.empty();
    }

    public void setCheckList(@NotNull Seq<CheckInfo> checkList) {
        myCheckList = checkList;
    }

    @Override
    public int getRowCount() {
        return 1 + myCheckList.length();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex == 0) {
            return switch (columnIndex) {
                case 0 -> "Checks";
                case 1 -> "Branch Info";
                default -> "Context";
            };
        } else {
            switch (columnIndex) {
                case 0 -> {
                    return myCheckList.apply(rowIndex - 1).checks().toString();
                }
                case 1 -> {
                    return myCheckList.apply(rowIndex - 1).branchInfo().mkString(", ");
                }
                default -> {
                    final var x = myCheckList.apply(rowIndex - 1).context();
                    if (x.pos() instanceof TranslatedPosition pos) {
                        return x + " at " + pos;
                    } else {
                        return x.toString();
                    }
                }
            }
        }
    }
}

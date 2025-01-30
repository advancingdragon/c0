package edu.cmu.c0;

import org.jetbrains.annotations.NotNull;
import scala.Tuple2;
import scala.collection.Seq;
import scala.collection.Seq$;

import javax.swing.table.AbstractTableModel;

public class VTableModel extends AbstractTableModel {
    private static VTableModel SINGLETON_INSTANCE = null;
    private Seq<Tuple2<String, String>> myVariables;

    public VTableModel() {
        myVariables = (Seq<Tuple2<String, String>>) Seq$.MODULE$.empty();
    }

    @NotNull
    public static VTableModel getInstance() {
        if (SINGLETON_INSTANCE == null) {
            SINGLETON_INSTANCE = new VTableModel();
        }
        return SINGLETON_INSTANCE;
    }

    public void setVariables(Seq<Tuple2<String, String>> variables) {
        myVariables = variables;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public int getRowCount() {
        return myVariables.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> myVariables.apply(rowIndex)._1();
            case 1 -> myVariables.apply(rowIndex)._2();
            default -> "";
        };
    }
}

package edu.cmu.c0;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import viper.silicon.logger.SymbExLogger;
import viper.silicon.state.State;

import javax.swing.table.AbstractTableModel;

public class VTableModel extends AbstractTableModel {
    private static VTableModel SINGLETON_INSTANCE = null;
    @Nullable
    private State myState;

    public VTableModel() {
        myState = null;
    }

    @NotNull
    public static VTableModel getInstance() {
        if (SINGLETON_INSTANCE == null) {
            SINGLETON_INSTANCE = new VTableModel();
        }
        return SINGLETON_INSTANCE;
    }

    public void setState(State state) {
        myState = state;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public int getRowCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> rowIndex == 0 ? "Heap" : "Optimistic Heap";
            case 1 -> myState == null? "" :
                rowIndex == 0 ? SymbExLogger.formatChunks(myState.h().values().toSeq()).mkString() :
                    SymbExLogger.formatChunks(myState.optimisticHeap().values().toSeq()).mkString();
            default -> "";
        };
    }
}

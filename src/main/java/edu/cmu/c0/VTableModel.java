package edu.cmu.c0;

import org.jetbrains.annotations.NotNull;
import scala.collection.JavaConverters;
import scala.collection.immutable.ListSet;
import scala.collection.immutable.ListSet$;
import viper.silicon.logger.SymbExLogger;
import viper.silicon.state.State;
import viper.silicon.state.terms.Term;

import javax.swing.table.AbstractTableModel;

public class VTableModel extends AbstractTableModel {
    private static VTableModel SINGLETON_INSTANCE = null;
    @NotNull
    private State myState;
    @NotNull
    private ListSet<Term> myNewPCs;

    public VTableModel() {
        reset();
    }

    @NotNull
    public static VTableModel getInstance() {
        if (SINGLETON_INSTANCE == null) {
            SINGLETON_INSTANCE = new VTableModel();
        }
        return SINGLETON_INSTANCE;
    }

    public void reset() {
        myState = State.empty();
        myNewPCs = (ListSet<Term>) ListSet$.MODULE$.empty();
    }

    public void setState(State state) {
        myState = state;
    }

    public void setNewPCs(ListSet<Term> newPCs) {
        myNewPCs = newPCs;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public int getRowCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            final var seq = switch (rowIndex) {
                case 0 -> SymbExLogger.formatChunks(myState.h().values().toSeq());
                case 1 -> SymbExLogger.formatChunks(myState.optimisticHeap().values().toSeq());
                default -> SymbExLogger.formatPCs((ListSet<Term>) ListSet$.MODULE$.empty(), myNewPCs);
            };
            return JavaConverters.seqAsJavaList(seq).toArray(new String[]{});
        } else {
            return switch (rowIndex) {
                case 0 -> "Permissions";
                case 1 -> "\uD83D\uDE3A";
                default -> "Constraints";
            };
        }
    }
}

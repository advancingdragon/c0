package edu.cmu.c0;

import org.jetbrains.annotations.NotNull;
import scala.collection.JavaConverters;
import scala.collection.Seq;
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

    private static String[] toArray(Seq<String> seq) {
        return JavaConverters.seqAsJavaList(seq).toArray(new String[]{});
    }

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
        return 4 + myState.g().values().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            return switch (rowIndex) {
                case 0 -> toArray(SymbExLogger.formatChunks(myState.h().values().toSeq()));
                case 1 -> toArray(SymbExLogger.formatChunks(myState.optimisticHeap().values().toSeq()));
                case 2 -> toArray(SymbExLogger.formatPCs((ListSet<Term>) ListSet$.MODULE$.empty(), myNewPCs));
                case 3 -> new String[] { "------------------------------------------------" };
                default -> new String[] { SymbExLogger.formatStore(myState.g()).apply(rowIndex - 4)._2 };
            };
        } else {
            return switch (rowIndex) {
                case 0 -> "Permissions";
                case 1 -> "\uD83D\uDE3A";
                case 2 -> "Constraints";
                case 3 -> "------------------------------------------------";
                default -> SymbExLogger.formatStore(myState.g()).apply(rowIndex - 4)._1;
            };
        }
    }
}

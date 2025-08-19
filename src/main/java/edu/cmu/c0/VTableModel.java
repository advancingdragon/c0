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
import java.util.ArrayList;

public class VTableModel extends AbstractTableModel {
    public static final int MAX_LINE_LENGTH = 120;
    private static VTableModel SINGLETON_INSTANCE = null;
    @NotNull
    private State myState;
    @NotNull
    private ListSet<Term> myNewPCs;

    private static String[] toArray(Seq<String> seq) {
        // layout text in table
        final var oldList = JavaConverters.seqAsJavaList(seq);
        final var newList = new ArrayList<String>();
        newList.add("");
        for (final var s : oldList) {
            final var last = newList.size() - 1;
            if (newList.get(last).length() + s.length() < MAX_LINE_LENGTH) {
                newList.set(last, newList.get(last) + s);
            } else {
                newList.add(s);
            }
        }
        return newList.toArray(new String[]{});
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

    public void setState(@NotNull State state) {
        myState = state;
    }

    public void setNewPCs(@NotNull ListSet<Term> newPCs) {
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
            return switch (rowIndex) {
                case 0 -> toArray(SymbExLogger.formatChunks(myState.h().values().toSeq()));
                case 1 -> toArray(SymbExLogger.formatChunks(myState.optimisticHeap().values().toSeq()));
                default -> toArray(SymbExLogger.formatPCs((ListSet<Term>) ListSet$.MODULE$.empty(), myNewPCs));
            };
        } else {
            return switch (rowIndex) {
                case 0 -> "Permissions";
                case 1 -> "\uD83D\uDE3A";
                default -> "Constraints";
            };
        }
    }
}

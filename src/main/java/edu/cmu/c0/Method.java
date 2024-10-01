package edu.cmu.c0;

import com.intellij.openapi.editor.Editor;
import com.intellij.ui.JBColor;
import scala.collection.JavaConverters;
import scala.collection.Seq;
import viper.silicon.logger.SymbExLogger;
import viper.silicon.logger.records.SymbolicRecord;
import viper.silicon.logger.records.data.CommentRecord;
import viper.silicon.logger.records.data.ExecuteRecord;
import viper.silicon.logger.records.structural.BranchingRecord;
import viper.silver.ast.TranslatedPosition;

import java.util.*;

public class Method {
    // each path is uniquely identified by the set of branches that were taken
    // and the branches that were not taken
    // for convenience, path records also keep track of which statements
    // were encountered in this path
    public record Path(Map<BranchingRecord, Boolean> forks, Set<ExecuteRecord> statements) { }

    private final Seq<SymbolicRecord> myLog;
    private final List<Path> myPaths;
    private int myPathNumber;

    public Method(Seq<SymbolicRecord> records) {
        myLog = records;
        myPaths = new ArrayList<>();
        traverse(myLog, false, new HashMap<>(), new HashSet<>());
        myPathNumber = 0;
    }

    public List<Path> getPaths() { return myPaths; }
    public int getPathNumber() { return myPathNumber; }
    public void togglePathNumber() {
        myPathNumber = (myPathNumber + 1) % myPaths.size();
    }

    public void traverse(Seq<SymbolicRecord> records,
                         boolean ended,
                         Map<BranchingRecord, Boolean> forks,
                         Set<ExecuteRecord> statements) {
        for (final var record : JavaConverters.asJavaIterable(records)) {
            switch (record) {
                case BranchingRecord b -> {
                    final var forks0 = new HashMap<>(forks);
                    final var forks1 = new HashMap<>(forks);
                    forks0.put(b, true);
                    traverse(b.getBranches().apply(0), ended, forks0, new HashSet<>(statements));
                    forks1.put(b, false);
                    traverse(b.getBranches().apply(1), ended, forks1, new HashSet<>(statements));
                }
                case CommentRecord c &&
                        (c.comment().equals("End") || c.comment().equals("Failure")) ->
                    ended = true;
                case ExecuteRecord x &&
                        x.value().pos() instanceof TranslatedPosition ->
                    statements.add(x);
                default -> { }
            }
        }
        if (ended) {
            myPaths.add(new Path(forks, statements));
        }
    }

    public void renderInlays(Seq<SymbolicRecord> records, Editor editor) {
        final var document = editor.getDocument();
        final var inlayModel = editor.getInlayModel();
        for (final var record : JavaConverters.asJavaIterable(records)) {
            switch (record) {
                case BranchingRecord b &&
                        myPaths.get(myPathNumber).forks.containsKey(b) -> {
                    if (myPaths.get(myPathNumber).forks.get(b)) {
                        renderInlays(b.getBranches().apply(0), editor);
                    } else {
                        renderInlays(b.getBranches().apply(1), editor);
                    }
                }
                case ExecuteRecord x &&
                        x.value().pos() instanceof TranslatedPosition pos -> {
                    final var offset = document.getLineStartOffset(U.toIJ(pos.line()));
                    final var h = SymbExLogger.formatChunks(x.state().h().values());
                    final var opt = SymbExLogger.formatChunks(x.state().optimisticHeap().values());
                    final var renderer = new InlayRenderer(new JBColor(0xFF7000, 0xFF7000), h, opt);
                    inlayModel.addBlockElement(offset, false, true, 1, renderer);
                }
                default -> { }
            }
        }
    }

    public void renderInlays(Editor editor) {
        renderInlays(myLog, editor);
    }
}

package edu.cmu.c0;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.JBColor;
import scala.collection.Iterable;
import scala.collection.JavaConverters;
import scala.collection.Seq;
import scala.collection.Seq$;
import scala.collection.immutable.ListSet;
import scala.collection.immutable.ListSet$;
import viper.silicon.interfaces.state.Chunk;
import viper.silicon.logger.SymbExLogger;
import viper.silicon.logger.records.SymbolicRecord;
import viper.silicon.logger.records.data.*;
import viper.silicon.logger.records.structural.BranchingRecord;
import viper.silicon.state.terms.Term;
import viper.silver.ast.TranslatedPosition;
import viper.silver.ast.Not;

import java.awt.*;
import java.util.*;
import java.util.List;

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
        forLoop:
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
                case CommentRecord c && c.comment().equals("Failure") -> {
                    ended = true;
                    break forLoop;
                }
                case EndRecord ignored -> {
                    ended = true;
                    break forLoop;
                }
                case LoopOutRecord ignored -> {
                    ended = true;
                    break forLoop;
                }
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

    public void renderInlays(Seq<SymbolicRecord> records, Iterable<Chunk> oldChunks, ListSet<Term> oldPcs, Editor editor) {
        final var document = editor.getDocument();
        final var inlayModel = editor.getInlayModel();
        for (final var record : JavaConverters.asJavaIterable(records)) {
            switch (record) {
                case BranchingRecord b &&
                        myPaths.get(myPathNumber).forks.containsKey(b) -> {
                    if (myPaths.get(myPathNumber).forks.get(b)) {
                        renderInlays(b.getBranches().apply(0), oldChunks, oldPcs, editor);
                    } else {
                        renderInlays(b.getBranches().apply(1), oldChunks, oldPcs, editor);
                    }
                }
                case ConditionalEdgeRecord c &&
                        c.value().pos() instanceof TranslatedPosition pos -> {
                    final var markupModel = editor.getMarkupModel();
                    final var offset0 = document.getLineStartOffset(U.toIJ(pos.line()));
                    final var end = pos.end().get();
                    final var offset1 = document.getLineStartOffset(U.toIJ(end.line()));
                    var color = JBColor.GREEN;
                    if (c.value() instanceof Not not && not.pos().equals(c.value().pos())) {
                        color = JBColor.LIGHT_GRAY;
                    }
                    final var attr = new TextAttributes(JBColor.BLACK, color, color, EffectType.BOXED, Font.BOLD);
                    markupModel.addRangeHighlighter(offset0 + U.toIJ(pos.column()),
                            offset1 + U.toIJ(end.column()),
                            U.LAYER_CONDITIONAL, attr, HighlighterTargetArea.EXACT_RANGE);
                }
                case EndRecord e &&
                        e.value().pos() instanceof TranslatedPosition pos -> {
                    final var offset = document.getLineStartOffset(U.toIJ(pos.end().get().line()));
                    final var chunks$ = SymbExLogger.formatDiff(oldChunks, e.state().h().values());
                    final var pcs$ = SymbExLogger.formatPcs(oldPcs, e.pcs());
                    final var renderer = new InlayRenderer(new JBColor(0xFF7000, 0xFF7000), chunks$._1(), chunks$._2(), pcs$);
                    inlayModel.addBlockElement(offset, false, false, 1, renderer);
                }
                case ExecuteRecord x &&
                        x.value().pos() instanceof TranslatedPosition pos -> {
                    final var offset = document.getLineStartOffset(U.toIJ(pos.line()));
                    final var chunks$ = SymbExLogger.formatDiff(oldChunks, x.state().h().values());
                    final var pcs$ = SymbExLogger.formatPcs(oldPcs, x.pcs());
                    final var renderer = new InlayRenderer(new JBColor(0xFF7000, 0xFF7000), chunks$._1(), chunks$._2(), pcs$);
                    inlayModel.addBlockElement(offset, false, true, 1, renderer);
                    oldChunks = x.state().h().values();
                    oldPcs = x.pcs();
                }
                case LoopInRecord i && i.pos() instanceof TranslatedPosition pos -> {
                    final var offset = document.getLineStartOffset(U.toIJ(pos.line()));
                    final var chunks$ = SymbExLogger.formatDiff(oldChunks, i.state().h().values());
                    final var pcs$ = SymbExLogger.formatPcs(oldPcs, i.pcs());
                    final var renderer = new InlayRenderer(new JBColor(0x0070FF, 0x0070FF), chunks$._1(), chunks$._2(), pcs$);
                    inlayModel.addBlockElement(offset, false, true, 1, renderer);
                    oldChunks = i.state().h().values();
                    oldPcs = i.pcs();
                }
                case LoopOutRecord o && o.pos() instanceof TranslatedPosition pos -> {
                    final var offset = document.getLineStartOffset(U.toIJ(pos.line()));
                    final var chunks$ = SymbExLogger.formatDiff(oldChunks, o.state().h().values());
                    final var pcs$ = SymbExLogger.formatPcs(oldPcs, o.pcs());
                    final var renderer = new InlayRenderer(new JBColor(0x609000, 0x609000), chunks$._1(), chunks$._2(), pcs$);
                    inlayModel.addBlockElement(offset, false, false, 1, renderer);
                    oldChunks = o.state().h().values();
                    oldPcs = o.pcs();
                }
                default -> { }
            }
        }
    }

    public void renderInlays(Editor editor) {
        renderInlays(myLog, (Iterable<Chunk>) Seq$.MODULE$.empty(), (ListSet<Term>) ListSet$.MODULE$.empty(), editor);
    }
}

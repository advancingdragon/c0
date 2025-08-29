package edu.cmu.c0;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;
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
import viper.silver.ast.Not;
import viper.silver.ast.TranslatedPosition;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Method {
    // each path is uniquely identified by the set of branches that were taken
    // and the branches that were not taken
    // for convenience, path records also keep track of which statements
    // were encountered in this path
    public record Path(Map<BranchingRecord, Boolean> forks, Set<ExecuteRecord> statements) { }

    private final Seq<SymbolicRecord> myLog;
    private final TranslatedPosition myPos;
    private final List<Path> myPaths;
    private int myPathNumber;
    private int mySelectedLine;

    public Method(Seq<SymbolicRecord> records, TranslatedPosition pos) {
        myLog = records;
        myPos = pos;
        myPaths = new ArrayList<>();
        traverse(myLog, false, new HashMap<>(), new HashSet<>());
        myPathNumber = 0;
        mySelectedLine = U.toIJ(pos.line());
    }

    public List<Path> getPaths() { return myPaths; }
    public int getPathNumber() { return myPathNumber; }
    public void setPathNumber(int pathNumber) {
        if (0 <= pathNumber && pathNumber < myPaths.size()) {
            myPathNumber = pathNumber;
        }
    }

    public void setSelectedLine(int selectedLine) {
        mySelectedLine = selectedLine;
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
                case EndRecord ignored -> {
                    ended = true;
                    break forLoop;
                }
                case ErrorRecord ignored -> {
                    ended = true;
                    break forLoop;
                }
                case LoopExitRecord ignored -> {
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

    public void renderInlays(Seq<SymbolicRecord> records,
                             Seq<Chunk> oldChunks,
                             ListSet<Term> oldPCs,
                             @NotNull Editor editor) {
        final var document = editor.getDocument();
        final var inlayModel = editor.getInlayModel();
        final var markupModel = editor.getMarkupModel();
        for (final var record : JavaConverters.asJavaIterable(records)) {
            switch (record) {
                case BranchingRecord b &&
                        myPaths.get(myPathNumber).forks.containsKey(b) -> {
                    if (myPaths.get(myPathNumber).forks.get(b)) {
                        renderInlays(b.getBranches().apply(0), oldChunks, oldPCs, editor);
                    } else {
                        renderInlays(b.getBranches().apply(1), oldChunks, oldPCs, editor);
                    }
                }
                case ConditionalEdgeRecord c &&
                        c.value().pos() instanceof TranslatedPosition pos -> {
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
                case EndRecord e -> {
                    final var offset = document.getLineStartOffset(U.toIJ(myPos.end().get().line()));
                    final var renderer = new InlayBoxRenderer("end", oldChunks, oldPCs, e.state(), e.pcs());
                    inlayModel.addBlockElement(offset, false, false, 1, renderer);
                }
                case ErrorRecord r &&
                        r.error().pos() instanceof TranslatedPosition pos -> {
                    final var offset0 = document.getLineStartOffset(U.toIJ(pos.line()));
                    final var end = pos.end().get();
                    final var offset1 = document.getLineStartOffset(U.toIJ(end.line()));
                    markupModel.addRangeHighlighter(offset0 + U.toIJ(pos.column()),
                            offset1 + U.toIJ(end.column()),
                            U.LAYER_ERROR, U.BAD, HighlighterTargetArea.EXACT_RANGE);
                    inlayModel.addAfterLineEndElement(offset0, false,
                            new InlayRenderer(JBColor.RED, r.error().readableMessage()));
                    // Need to display state right before error happened as well
                    // This state is displayed below the error since the state
                    // of the last executed statement is displayed above
                    final var renderer = new InlayBoxRenderer("error", oldChunks, oldPCs, r.state(), r.pcs());
                    inlayModel.addBlockElement(offset0, false, false, 1, renderer);
                }
                case ExecuteRecord x &&
                        x.value().pos() instanceof TranslatedPosition pos -> {
                    final var offset = document.getLineStartOffset(U.toIJ(pos.line()));
                    final var renderer = new InlayBoxRenderer("", oldChunks, oldPCs, x.state(), x.pcs());
                    // inefficient, but must always allocate InlayBoxRenderer in order to populateSnaps
                    if (U.toIJ(pos.line()) == mySelectedLine) {
                        inlayModel.addBlockElement(offset, false, true, 1, renderer);
                    }
                    oldChunks = x.state().h().values().toSeq();
                    oldPCs = x.pcs();
                }
                case LoopExitRecord lex -> {
                    final var pos = (TranslatedPosition) SymbExLogger.whileLoops().get(lex.value()).get().pos();
                    final var offset = document.getLineStartOffset(U.toIJ(pos.end().get().line()));
                    final var renderer = new InlayBoxRenderer("leaving loop", oldChunks, oldPCs, lex.state(), lex.pcs());
                    inlayModel.addBlockElement(offset, false, false, 1, renderer);
                }
                case LoopInRecord i -> {
                    final var pos = (TranslatedPosition) SymbExLogger.whileLoops().get(i.value()).get().pos();
                    final var offset = document.getLineStartOffset(U.toIJ(pos.line()));
                    final var renderer = new InlayBoxRenderer("entering loop", oldChunks, oldPCs, i.state(), i.pcs());
                    inlayModel.addBlockElement(offset, false, true, 1, renderer);
                    oldChunks = i.state().h().values().toSeq();
                    oldPCs = i.pcs();
                }
                case LoopOutRecord o -> {
                    final var pos = (TranslatedPosition) SymbExLogger.whileLoops().get(o.value()).get().pos();
                    final var offset = document.getLineStartOffset(U.toIJ(pos.end().get().line()));
                    final var renderer = new InlayBoxRenderer("between iterations", oldChunks, oldPCs, o.state(), o.pcs());
                    inlayModel.addBlockElement(offset, false, true, 1, renderer);
                    oldChunks = o.state().h().values().toSeq();
                    oldPCs = o.pcs();
                }
                default -> { }
            }
        }
    }

    public void renderInlays(@NotNull Editor editor) {
        renderInlays(myLog,
                (Seq<Chunk>) Seq$.MODULE$.empty(),
                (ListSet<Term>) ListSet$.MODULE$.empty(),
                editor);
    }
}

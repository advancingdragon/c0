package edu.cmu.c0;

import com.intellij.openapi.editor.EditorCustomElementRenderer;
import com.intellij.openapi.editor.Inlay;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;
import scala.collection.JavaConverters;
import scala.collection.Seq;
import scala.collection.immutable.ListSet;
import viper.silicon.logger.SymbExLogger;
import viper.silicon.state.State;
import viper.silicon.state.terms.Term;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class InlayBoxRenderer implements EditorCustomElementRenderer {
    public static final int MAX_LINE_LENGTH = 80;
    public static final JBColor BG_CONSUMED_COLOR = new JBColor(0xFFECFF, 0xFFECFF);
    public static final JBColor BG_MAIN_COLOR = new JBColor(0xECFFFF, 0xECFFFF);
    public static final JBColor CONSUMED_COLOR = new JBColor(0x800060, 0x800060);
    public static final JBColor MAIN_COLOR = new JBColor(0x0000C0, 0x0000C0);

    private final String myLabel;
    private final int myLongest;
    private final State myState;
    private final ListSet<Term> myNewPCs;
    private final ArrayList<StringBuilder> myHeap;
    private final ArrayList<StringBuilder> myPCs;
    private boolean mySelected;

    private void addToList(ArrayList<StringBuilder> list, String prefix, Seq<String> strings) {
        for (final var string : JavaConverters.asJavaIterable(strings)) {
            if (list.isEmpty()) {
                list.add(new StringBuilder(prefix + string));
            } else if (list.get(list.size() - 1).length() + string.length() < MAX_LINE_LENGTH) {
                list.get(list.size() - 1).append(string);
            } else {
                list.add(new StringBuilder(prefix + string));
            }
        }
    }

    public InlayBoxRenderer(@NotNull String label,
                            int longest,
                            @NotNull State state,
                            @NotNull ListSet<Term> oldPCs,
                            @NotNull ListSet<Term> newPCs) {
        myLabel = label;
        myLongest = longest;
        myState = state;
        myNewPCs = newPCs;
        final var newChunks = state.h().values().toSeq();
        SymbExLogger.populateSnaps(newChunks);
        final var fieldAndPredicateChunks = SymbExLogger.partitionChunks(newChunks);
        final var fieldChunksWithSnap = SymbExLogger.filterFieldChunksWithSnap(fieldAndPredicateChunks._1());
        final var fieldChunks$ = SymbExLogger.formatChunksUniqueHack(fieldAndPredicateChunks._1());
        final var fieldChunksWithSnap$ = SymbExLogger.formatFieldChunksWithSnap(fieldChunksWithSnap);
        final var resourceChunks$ = SymbExLogger.formatChunks(fieldAndPredicateChunks._2());
        final var _PCs$ = SymbExLogger.formatPCs(oldPCs, newPCs);
        myHeap = new ArrayList<>();
        myPCs = new ArrayList<>();
        addToList(myHeap, "", fieldChunks$);
        addToList(myHeap, "", fieldChunksWithSnap$);
        addToList(myHeap, "", resourceChunks$);
        addToList(myPCs, "+ ", _PCs$);
        mySelected = false;
    }

    public State getState() {
        return myState;
    }

    public ListSet<Term> getNewPCs() {
        return myNewPCs;
    }

    public void select() {
        mySelected = true;
    }

    public void deselect() {
        mySelected = false;
    }

    @Override
    public int calcWidthInPixels(@NotNull Inlay inlay) {
        final var editor = inlay.getEditor();
        final var f = editor.getColorsScheme().getFont(EditorFontType.BOLD_ITALIC);
        final var fontMetrics = editor.getComponent().getFontMetrics(f);
        return fontMetrics.stringWidth(" ".repeat(myLongest + 2*MAX_LINE_LENGTH));
    }

    @Override
    public int calcHeightInPixels(@NotNull Inlay inlay) {
        final var lines = (!myLabel.isEmpty() ? 1 : 0) +
                Math.max(myHeap.size(), myPCs.size());
        return lines * inlay.getEditor().getLineHeight();
    }

    @Override
    public void paint(@NotNull Inlay inlay, @NotNull Graphics2D g,
                      @NotNull Rectangle2D r, @NotNull TextAttributes t) {
        final var editor = inlay.getEditor();
        final var f = editor.getColorsScheme().getFont(EditorFontType.BOLD_ITALIC);
        final var fontMetrics = editor.getComponent().getFontMetrics(f);
        final var width = fontMetrics.stringWidth(" ".repeat(MAX_LINE_LENGTH));
        final var x = ((int) r.getX()) + fontMetrics.stringWidth(" ".repeat(myLongest));
        var y = ((int) r.getY()) + editor.getAscent();
        g.setFont(f);

        if (!myLabel.isEmpty()) {
            g.setColor(JBColor.BLACK);
            g.fillRect(x, (int) r.getY(), 2*width, editor.getLineHeight());
            g.setColor(JBColor.WHITE);
            g.drawString(myLabel, x, y);
            y += editor.getLineHeight();
        }

        final var heapAndPCsY = y - editor.getAscent();
        g.setColor(mySelected ? CONSUMED_COLOR : BG_CONSUMED_COLOR);
        g.fillRect(x, heapAndPCsY, width, Math.max(myHeap.size(), myPCs.size()) * editor.getLineHeight());

        g.setColor(mySelected ? BG_CONSUMED_COLOR : CONSUMED_COLOR);
        for (final var stringBuilder : myHeap) {
            g.drawString(stringBuilder.toString(), x, y);
            y += editor.getLineHeight();
        }

        // reset y to top
        y = heapAndPCsY + editor.getAscent();
        g.setColor(mySelected ? MAIN_COLOR : BG_MAIN_COLOR);
        g.fillRect(x + width, heapAndPCsY, width, Math.max(myHeap.size(), myPCs.size()) * editor.getLineHeight());

        g.setColor(mySelected ? BG_MAIN_COLOR : MAIN_COLOR);
        for (final var stringBuilder : myPCs) {
            g.drawString(stringBuilder.toString(), x + width, y);
            y += editor.getLineHeight();
        }

        // frame the whole inlay
        g.setColor(JBColor.BLACK);
        g.drawRect(x, (int) r.getY(), 2*width, (int) r.getHeight());

        // draw line
        final var document = editor.getDocument();
        final var offset = inlay.getOffset();
        final var lineLength = fontMetrics.stringWidth(" ".repeat(document.getLineEndOffset(document.getLineNumber(offset)) - offset));
        g.drawLine(lineLength, (int) r.getY() + (int) r.getHeight(), x, (int) r.getY() + (int) r.getHeight());
    }
}

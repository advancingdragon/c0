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
    private final ListSet<Term> myPCs;
    private final ArrayList<StringBuilder> myHeapList;
    private final ArrayList<StringBuilder> myPCsList;
    private boolean mySelected;

    private void addToList(ArrayList<StringBuilder> list, Seq<String> strings) {
        for (final var string : JavaConverters.asJavaIterable(strings)) {
            if (list.isEmpty()) {
                list.add(new StringBuilder(string));
            } else if (list.get(list.size() - 1).length() + string.length() < MAX_LINE_LENGTH) {
                list.get(list.size() - 1).append(string);
            } else {
                list.add(new StringBuilder(string));
            }
        }
    }

    public InlayBoxRenderer(@NotNull String label,
                            int longest,
                            @NotNull State state,
                            @NotNull ListSet<Term> thePCs) {
        myLabel = label;
        myLongest = longest;
        myState = state;
        myPCs = thePCs;
        final var chunks = state.h().values().toSeq();
        final var optimisticChunks = state.optimisticHeap().values().toSeq();
        final var fieldAndPredicateChunks = SymbExLogger.partitionChunks(chunks);
        final var fieldChunks$ = SymbExLogger.formatChunks(fieldAndPredicateChunks._1(), state);
        final var predicateChunks$ = SymbExLogger.formatChunks(fieldAndPredicateChunks._2(), state);
        final var optimisticChunks$ = SymbExLogger.formatChunks(optimisticChunks, state);
        final var thePCs$ = SymbExLogger.formatPCs(thePCs, state);
        myHeapList = new ArrayList<>();
        myPCsList = new ArrayList<>();
        addToList(myHeapList, fieldChunks$);
        addToList(myHeapList, predicateChunks$);
        if (optimisticChunks.nonEmpty()) {
            myHeapList.add(new StringBuilder("\uD83E\uDD1E "));
            addToList(myHeapList, optimisticChunks$);
        }
        addToList(myPCsList, thePCs$);
        mySelected = false;
    }

    public State getState() {
        return myState;
    }

    public ListSet<Term> getPCs() {
        return myPCs;
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
        return fontMetrics.stringWidth(" ".repeat(myLongest + MAX_LINE_LENGTH));
    }

    @Override
    public int calcHeightInPixels(@NotNull Inlay inlay) {
        final var lines = (!myLabel.isEmpty() ? 1 : 0) +
                myHeapList.size() + myPCsList.size();
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
            g.fillRect(x, (int) r.getY(), width, editor.getLineHeight());
            g.setColor(JBColor.WHITE);
            g.drawString(myLabel, x, y);
            y += editor.getLineHeight();
        }

        final var heapY = y - editor.getAscent();
        g.setColor(mySelected ? CONSUMED_COLOR : BG_CONSUMED_COLOR);
        g.fillRect(x, heapY, width, myHeapList.size() * editor.getLineHeight());

        g.setColor(mySelected ? BG_CONSUMED_COLOR : CONSUMED_COLOR);
        for (final var stringBuilder : myHeapList) {
            g.drawString(stringBuilder.toString(), x, y);
            y += editor.getLineHeight();
        }

        // the correct way to calculate the vertical position is y - ascent
        final var mainY = y - editor.getAscent();
        g.setColor(mySelected ? MAIN_COLOR : BG_MAIN_COLOR);
        g.fillRect(x, mainY, width, myPCsList.size() * editor.getLineHeight());

        g.setColor(mySelected ? BG_MAIN_COLOR : MAIN_COLOR);
        for (final var stringBuilder : myPCsList) {
            g.drawString(stringBuilder.toString(), x, y);
            y += editor.getLineHeight();
        }

        // frame the whole inlay
        g.setColor(JBColor.BLACK);
        g.drawRect(x, (int) r.getY(), width, (int) r.getHeight());

        // draw line
        final var document = editor.getDocument();
        final var offset = inlay.getOffset();
        final var lineLength = fontMetrics.stringWidth(" ".repeat(document.getLineEndOffset(document.getLineNumber(offset)) - offset));
        g.drawLine(lineLength, (int) r.getY() + (int) r.getHeight(), x, (int) r.getY() + (int) r.getHeight());
    }
}

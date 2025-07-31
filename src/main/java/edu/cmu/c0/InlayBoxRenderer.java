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
import viper.silicon.interfaces.state.Chunk;
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

    private final State myState;
    private final ListSet<Term> myNewPCs;
    private final ArrayList<StringBuilder> myConsumedList;
    private final ArrayList<StringBuilder> myProducedNewList;
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

    public InlayBoxRenderer(Seq<Chunk> oldChunks, ListSet<Term> oldPCs,
                            State state, ListSet<Term> newPCs) {
        myState = state;
        myNewPCs = newPCs;
        final var newChunks = state.h().values().toSeq();
        SymbExLogger.populateSnaps(newChunks);
        final var diff$ = SymbExLogger.formatChunksDiff(oldChunks, newChunks);
        final var _PCs$ = SymbExLogger.formatPCs(oldPCs, newPCs);
        myConsumedList = new ArrayList<>();
        myProducedNewList = new ArrayList<>();
        addToList(myConsumedList, diff$._1());
        addToList(myProducedNewList, diff$._2());
        addToList(myProducedNewList, _PCs$);
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
        return fontMetrics.stringWidth(" ".repeat(MAX_LINE_LENGTH));
    }

    @Override
    public int calcHeightInPixels(@NotNull Inlay inlay) {
        return (myConsumedList.size() + myProducedNewList.size()) * inlay.getEditor().getLineHeight();
    }

    @Override
    public void paint(@NotNull Inlay inlay, @NotNull Graphics2D g,
                      @NotNull Rectangle2D r, @NotNull TextAttributes t) {
        final var editor = inlay.getEditor();
        var y = ((int) r.getY()) + editor.getAscent();
        final var f = editor.getColorsScheme().getFont(EditorFontType.BOLD_ITALIC);
        final var fontMetrics = editor.getComponent().getFontMetrics(f);
        final var width = fontMetrics.stringWidth(" ".repeat(MAX_LINE_LENGTH));
        g.setFont(f);
        g.setColor(mySelected ? CONSUMED_COLOR : BG_CONSUMED_COLOR);
        g.fillRect((int) r.getX(), (int) r.getY(), width, myConsumedList.size() * editor.getLineHeight());

        g.setColor(mySelected ? BG_CONSUMED_COLOR : CONSUMED_COLOR);
        for (final var stringBuilder : myConsumedList) {
            g.drawString(stringBuilder.toString(), (int) r.getX(), y);
            y += editor.getLineHeight();
        }

        // the correct way to calculate the vertical position is y - ascent
        final var mainY = y - editor.getAscent();
        g.setColor(mySelected ? MAIN_COLOR : BG_MAIN_COLOR);
        g.fillRect((int) r.getX(), mainY, width, myProducedNewList.size() * editor.getLineHeight());

        g.setColor(mySelected ? BG_MAIN_COLOR : MAIN_COLOR);
        for (final var stringBuilder : myProducedNewList) {
            g.drawString(stringBuilder.toString(), (int) r.getX(), y);
            y += editor.getLineHeight();
        }
    }
}

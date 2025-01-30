package edu.cmu.c0;

import com.intellij.openapi.editor.EditorCustomElementRenderer;
import com.intellij.openapi.editor.Inlay;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;
import scala.Tuple2;
import scala.collection.Seq;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class InlayBoxRenderer implements EditorCustomElementRenderer {
    public static final JBColor BG_COLOR = new JBColor(0xFFECFF, 0xFFECFF);
    public static final JBColor CONSUMED_COLOR = new JBColor(0x609000, 0x609000);
    public static final JBColor MAIN_COLOR = new JBColor(0xC08000, 0xC08000);
    public static final JBColor PCS_COLOR = new JBColor(0x0080C0, 0x0080C0);

    private final String myConsumed;
    private final String myProduced;
    private final String myHeap;
    private final String myNewPcs;
    private final Seq<Tuple2<String, String>> myStore;
    private boolean myToggle;

    public InlayBoxRenderer(String consumed, String produced, String heap,
                            String newPcs, Seq<Tuple2<String, String>> store) {
        myConsumed = consumed;
        myProduced = produced;
        myHeap = heap;
        myNewPcs = newPcs;
        myToggle = false;
        myStore = store;
    }

    public Seq<Tuple2<String, String>> getStore() {
        return myStore;
    }

    public void toggle() {
        myToggle = !myToggle;
    }

    @Override
    public int calcWidthInPixels(@NotNull Inlay inlay) {
        final var editor = inlay.getEditor();
        final var f = editor.getColorsScheme().getFont(EditorFontType.BOLD_ITALIC);
        final var fontMetrics = editor.getComponent().getFontMetrics(f);
        final var newPcsWidth = fontMetrics.stringWidth(myNewPcs);
        if (myToggle) {
            return Math.max(fontMetrics.stringWidth(myHeap), newPcsWidth);
        } else {
            return Math.max(fontMetrics.stringWidth(myConsumed + myProduced), newPcsWidth);
        }
    }

    @Override
    public int calcHeightInPixels(@NotNull Inlay inlay) {
        return inlay.getEditor().getLineHeight() * 2;
    }

    @Override
    public void paint(@NotNull Inlay inlay, @NotNull Graphics2D g,
                      @NotNull Rectangle2D r, @NotNull TextAttributes t) {
        final var editor = inlay.getEditor();
        final var y = ((int) r.getY()) + editor.getAscent();
        final var f = editor.getColorsScheme().getFont(EditorFontType.BOLD_ITALIC);
        final var fontMetrics = editor.getComponent().getFontMetrics(f);
        g.setFont(f);
        if (myToggle) {
            final var heapWidth = fontMetrics.stringWidth(myHeap);
            g.setColor(BG_COLOR);
            g.fillRect((int) r.getX(), (int) r.getY(), heapWidth, editor.getLineHeight());

            g.setColor(MAIN_COLOR);
            g.drawString(myHeap, (int) r.getX(), y);

            g.setColor(PCS_COLOR);
            g.drawString(myNewPcs, (int) r.getX(), editor.getLineHeight() + y);
        } else {
            g.setColor(CONSUMED_COLOR);
            g.drawString(myConsumed, (int) r.getX(), y);

            final var consumedWidth = fontMetrics.stringWidth(myConsumed);
            g.setColor(MAIN_COLOR);
            g.drawString(myProduced, (int) r.getX() + consumedWidth, y);

            g.setColor(PCS_COLOR);
            g.drawString(myNewPcs, (int) r.getX(), editor.getLineHeight() + y);
        }
    }
}

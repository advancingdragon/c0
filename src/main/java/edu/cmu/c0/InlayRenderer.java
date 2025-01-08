package edu.cmu.c0;

import com.intellij.openapi.editor.EditorCustomElementRenderer;
import com.intellij.openapi.editor.Inlay;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class InlayRenderer implements EditorCustomElementRenderer {
    private final JBColor myColor;
    private final String myLabel;
    private final String myLabel1;
    private final String myLabel2;
    public InlayRenderer(JBColor color, String label) {
        myColor = color;
        myLabel = label;
        myLabel1 = "";
        myLabel2 = "";
    }

    public InlayRenderer(JBColor color, String label, String label1, String label2) {
        myColor = color;
        myLabel = label;
        myLabel1 = label1;
        myLabel2 = label2;
    }

    @Override
    public int calcWidthInPixels(@NotNull Inlay inlay) {
        final var editor = inlay.getEditor();
        final var f = editor.getColorsScheme().getFont(EditorFontType.BOLD_ITALIC);
        final var fontMetrics = editor.getComponent().getFontMetrics(f);
        return fontMetrics.stringWidth(myLabel + myLabel1);
    }

    @Override
    public void paint(@NotNull Inlay inlay, @NotNull Graphics2D g,
                      @NotNull Rectangle2D r, @NotNull TextAttributes t) {
        final var editor = inlay.getEditor();
        final var y = ((int) r.getY()) + editor.getAscent();
        final var f = editor.getColorsScheme().getFont(EditorFontType.BOLD_ITALIC);
        final var fontMetrics = editor.getComponent().getFontMetrics(f);
        final var labelWidth = fontMetrics.stringWidth(myLabel);
        g.setFont(f);
        g.setColor(myColor);
        g.drawString(myLabel, (int) r.getX(), y);
        if (myLabel1.equals("")) {
            return;
        }
        g.setColor(new JBColor(0xC08000, 0xC08000));
        g.drawRect(((int) r.getX()) + labelWidth, (int) r.getY(),
                fontMetrics.stringWidth(myLabel1), (int) r.getHeight());
        g.drawString(myLabel1, (int) r.getX() + labelWidth, y);

        final var twoLabelsWidth = fontMetrics.stringWidth(myLabel + myLabel1);
        g.setColor(new JBColor(0x0080C0, 0x0080C0));
        g.drawString(myLabel2, (int) r.getX() + twoLabelsWidth, y);
    }
}

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
    private final String myOtherLabel;
    public InlayRenderer(JBColor color, String label) {
        myColor = color;
        myLabel = label;
        myOtherLabel = "";
    }

    public InlayRenderer(JBColor color, String label, String otherLabel) {
        myColor = color;
        myLabel = label;
        myOtherLabel = otherLabel;
    }

    @Override
    public int calcWidthInPixels(@NotNull Inlay inlay) {
        final var editor = inlay.getEditor();
        final var f = editor.getColorsScheme().getFont(EditorFontType.BOLD_ITALIC);
        final var fontMetrics = editor.getComponent().getFontMetrics(f);
        return fontMetrics.stringWidth(myLabel + myOtherLabel);
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
        if (myOtherLabel.equals("")) {
            return;
        }
        g.setColor(new JBColor(0xFFC000, 0xFFC000));
        g.drawRect(((int) r.getX()) + labelWidth, (int) r.getY(),
                fontMetrics.stringWidth(myOtherLabel), (int) r.getHeight());
        g.drawString(myOtherLabel, (int) r.getX() + labelWidth, y);
    }
}

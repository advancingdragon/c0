package edu.cmu.c0;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseEventArea;
import com.intellij.openapi.editor.event.EditorMouseListener;
import org.jetbrains.annotations.NotNull;
import viper.silicon.logger.SymbExLogger;
import viper.silicon.logger.SymbLog;
import viper.silicon.logger.records.data.MethodRecord;
import viper.silver.ast.TranslatedPosition;

import java.util.Map;

public class Controller implements EditorMouseListener {
    private final Map<SymbLog, Method> myMethods;

    public Controller(@NotNull Map<SymbLog, Method> methods) {
        myMethods = methods;
    }

    public void deselectBlockInlays(@NotNull Editor editor) {
        final var document = editor.getDocument();
        final var inlayModel = editor.getInlayModel();
        for (final var inlay : inlayModel.getBlockElementsInRange(0,
                document.getTextLength()-1, InlayBoxRenderer.class)) {
            inlay.getRenderer().deselect();
            inlay.repaint();
        }
    }

    public void renderMethods(@NotNull Editor editor) {
        for (final var method : myMethods.values()) {
            method.renderInlays(editor);
        }

        final var gutterProvider = new GutterProvider(editor.getDocument().getLineCount(), myMethods);
        editor.getGutter().registerTextAnnotation(gutterProvider);
    }

    @Override
    public void mouseClicked(@NotNull EditorMouseEvent event) {
        final var editor = event.getEditor();
        final var inlay = event.getInlay();
        if (event.getArea() == EditorMouseEventArea.EDITING_AREA &&
                inlay != null &&
                inlay.getRenderer() instanceof InlayBoxRenderer boxRenderer) {
            // deselect all block inlays first
            deselectBlockInlays(editor);
            // mark inlay as selected
            boxRenderer.select();
            inlay.repaint();
            // if symbolic state inlay clicked, update tool window
            final var instance = VTableModel.getInstance();
            instance.setState(boxRenderer.getState());
            instance.setPCs(boxRenderer.getPCs());
            instance.fireTableDataChanged();
            return;
        }

        if (event.getArea() != EditorMouseEventArea.ANNOTATIONS_AREA) {
            return;
        }

        final var scrollingModel = editor.getScrollingModel();
        final var h = scrollingModel.getHorizontalScrollOffset();
        final var v = scrollingModel.getVerticalScrollOffset();

        U.cleanUpAfterClick(editor);

        // find method line belongs to, toggle path number, and redraw inlays
        final var lineClicked = event.getLogicalPosition().line;
        for (final var symbLog : myMethods.keySet()) {
            if (SymbExLogger.m(symbLog) instanceof MethodRecord methodRecord &&
                    methodRecord.value().pos() instanceof TranslatedPosition pos) {
                // method should always have TranslatedPosition with end
                // object, except for methods in libraries
                final var startLine = U.toIJ(pos.line());
                final var endLine = U.toIJ(pos.end().get().line());
                if (startLine <= lineClicked && lineClicked <= endLine) {
                    // find out which path was clicked from the x coordinate
                    final var f = editor.getColorsScheme().getFont(EditorFontType.BOLD_ITALIC);
                    final var fontMetrics = editor.getComponent().getFontMetrics(f);
                    final var singleCharWidth = fontMetrics.stringWidth(" ");
                    final var lineCount = editor.getDocument().getLineCount();
                    final var x = event.getMouseEvent().getX();
                    final var pathNumber = x / singleCharWidth - (U.numberOfChars(lineCount) + 1);
                    myMethods.get(symbLog).setPathNumber(pathNumber);
                }
            }
        }

        renderMethods(editor);

        // this fixes a bug in the IntelliJ editor, where the editor scrolls
        // away from the caret after the inlays are deleted
        scrollingModel.disableAnimation();
        scrollingModel.scroll(h, v);
        scrollingModel.enableAnimation();
    }
}

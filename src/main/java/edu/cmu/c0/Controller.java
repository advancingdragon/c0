package edu.cmu.c0;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseListener;
import org.jetbrains.annotations.NotNull;
import viper.silicon.logger.SymbExLogger;
import viper.silicon.logger.SymbLog;
import viper.silicon.logger.records.data.MethodRecord;
import viper.silver.ast.TranslatedPosition;

import java.util.Map;

public class Controller implements EditorMouseListener {
    private final Map<SymbLog, Method> myMethods;

    public Controller(Map<SymbLog, Method> methods) {
        myMethods = methods;
    }

    public void renderMethods(Editor editor) {
        for (final var method : myMethods.values()) {
            method.renderInlays(editor);
        }

        editor.getGutter().closeAllAnnotations();
        final var gutterProvider = new GutterProvider(editor.getDocument().getLineCount(), myMethods);
        editor.getGutter().registerTextAnnotation(gutterProvider);
    }

    @Override
    public void mouseClicked(@NotNull EditorMouseEvent event) {
        final var editor = event.getEditor();
        final var scrollingModel = editor.getScrollingModel();
        final var h = scrollingModel.getHorizontalScrollOffset();
        final var v = scrollingModel.getVerticalScrollOffset();

        // dispose of all block inlays displaying heap state
        U.removeBlockInlays(editor);

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
                    myMethods.get(symbLog).togglePathNumber();
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
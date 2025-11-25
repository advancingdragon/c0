package edu.cmu.c0;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseEventArea;
import com.intellij.openapi.editor.event.EditorMouseListener;
import edu.cmu.c0.runtime.TableModel;
import org.jetbrains.annotations.NotNull;
import scala.collection.JavaConverters;
import viper.silicon.logger.SymbLog;
import viper.silicon.state.runtimeChecks;

import java.util.HashMap;
import java.util.Map;

public class Controller implements EditorMouseListener {
    private final Map<SymbLog, Method> myMethods;
    private final Map<Integer, Check> myRuntimeChecks;

    public Controller(@NotNull Map<SymbLog, Method> methods) {
        myMethods = methods;
        myRuntimeChecks = new HashMap<>();
    }

    public void renderChecks(@NotNull Editor editor) {
        final var checks = JavaConverters.mapAsJavaMap(runtimeChecks.getChecks());
        for (final var checkPosition : checks.keySet()) {
            final var check = new Check(checkPosition, checks.get(checkPosition));
            check.render(editor);
            for (var i = check.getOffset0(); i <= check.getOffset1(); i += 1) {
                myRuntimeChecks.put(i, check);
            }
        }
    }

    public void renderMethods(@NotNull Editor editor) {
        for (final var method : myMethods.values()) {
            method.renderInlays(editor);
        }

        renderChecks(editor);

        final var gutterProvider = new GutterProvider(editor.getDocument().getLineCount(), myMethods);
        editor.getGutter().registerTextAnnotation(gutterProvider);
    }

    @Override
    public void mouseClicked(@NotNull EditorMouseEvent event) {
        final var editor = event.getEditor();

        if (event.getArea() == EditorMouseEventArea.EDITING_AREA) {
            if (myRuntimeChecks.containsKey(event.getOffset())) {
                final var selectedCheck = myRuntimeChecks.get(event.getOffset());
                final var checks = myRuntimeChecks.values();
                // remove duplicates
                final Iterable<Check> i = () -> checks.stream().distinct().iterator();
                for (final var check : i) {
                    if (check.equals(selectedCheck)) {
                        selectedCheck.setSelected(true);
                    } else {
                        check.setSelected(false);
                    }
                    check.render(editor);
                }
                final var instance = TableModel.getInstance();
                instance.setCheckList(selectedCheck.getCheckList());
                instance.fireTableDataChanged();
            }
            return;
        }

        if (event.getArea() != EditorMouseEventArea.ANNOTATIONS_AREA) {
            return;
        }

        final var scrollingModel = editor.getScrollingModel();
        final var h = scrollingModel.getHorizontalScrollOffset();
        final var v = scrollingModel.getVerticalScrollOffset();

        U.cleanUpAfterClick(editor);

        // find method line belongs to, set path number to path clicked, and
        // redraw inlays
        final var lineClicked = event.getLogicalPosition().line;
        for (final var method : myMethods.values()) {
            // method should always have TranslatedPosition with end object,
            // except for methods in libraries
            final var pos = method.getPos();
            final var startLine = U.toIJ(pos.line());
            final var endLine = U.toIJ(pos.end().get().line());
            if (startLine <= lineClicked && lineClicked <= endLine) {
                // find out which path was clicked from the x coordinate
                final var f = editor.getColorsScheme().getFont(EditorFontType.BOLD_ITALIC);
                final var fontMetrics = editor.getComponent().getFontMetrics(f);
                final var singleCharWidth = fontMetrics.stringWidth(" ");
                final var lineCount = editor.getDocument().getLineCount();
                final var x = event.getMouseEvent().getX();
                // empirically determined that this is the way the UI is laid out
                final var pathNumber = (x - 8 - (singleCharWidth*U.numberOfChars(lineCount))) / singleCharWidth;
                method.setPathNumber(pathNumber);
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

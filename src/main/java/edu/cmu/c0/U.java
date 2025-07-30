package edu.cmu.c0;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Disposer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class U {
    public static final int LAYER_CONDITIONAL = 5432;
    public static final int LAYER_ERROR = 5678;

    // VIPER/SILICON HAS 1-INDEX LINE COLUMN NUMBERS, BUT INTELLIJ HAS 0-INDEX LINE NUMBERS!!!
    // Silver expressions should always have a TranslatedPosition, but inserted statements
    // will have a NoPosition$
    public static int toIJ(int viperLine) {
        return viperLine - 1;
    }

    // sets the value at arbitrary index of array, replacing the array with
    // a bigger array if necessary
    public static void grow(String[][] array, int row, int column, String e) {
        if (column >= array[row].length) {
            final var oldArray = array[row];
            array[row] = Arrays.copyOf(oldArray, column + 1);
            for (var i = oldArray.length; i < array[row].length; i += 1) {
                array[row][i] = " ";
            }
        }
        array[row][column] = e;
    }

    // all heap state and PC inlays are block inlays
    // the dummy inlay and error message inlays are after line end inlays
    public static void cleanUpAfterClick(@NotNull Editor editor) {
        // dispose of all block inlays (displaying heap state and PCs)
        final var document = editor.getDocument();
        final var inlayModel = editor.getInlayModel();
        for (final var inlay : inlayModel.getBlockElementsInRange(0,
                document.getTextLength()-1, InlayBoxRenderer.class)) {
            Disposer.dispose(inlay);
        }
        // then retrieve and dispose of all after line end inlays, EXCEPT
        // dummy inlay
        for (final var inlay : inlayModel.getAfterLineEndElementsInRange(1,
                document.getTextLength()-1, InlayRenderer.class)) {
            Disposer.dispose(inlay);
        }
        // remove all of our highlighters
        final var markupModel = editor.getMarkupModel();
        for (final var highlighter : markupModel.getAllHighlighters()) {
            // find our highlighters, which all have specific layer numbers
            final var layer = highlighter.getLayer();
            if (layer == LAYER_CONDITIONAL || layer == LAYER_ERROR) {
                markupModel.removeHighlighter(highlighter);
            }
        }
        // reset gutter
        editor.getGutter().closeAllAnnotations();
        // reset viewer table
        final var instance = VTableModel.getInstance();
        instance.reset();
        instance.fireTableDataChanged();
    }

    public static void reset(@NotNull Editor editor) {
        // retrieve and dispose of dummy inlay, which causes the disposer to
        // remove controller as well
        final var inlayModel = editor.getInlayModel();
        final var _0Or1Inlays = inlayModel.getAfterLineEndElementsInRange(0,
                0,
                InlayRenderer.class);
        if (_0Or1Inlays.size() == 1) { // there must be either 0 or 1 inlays
            Disposer.dispose(_0Or1Inlays.get(0));
        }
        U.cleanUpAfterClick(editor);
    }
}

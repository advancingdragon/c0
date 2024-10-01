package edu.cmu.c0;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Disposer;

import java.util.Arrays;

public class U {
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

    // all heap/optimistic heap state inlays are block inlays
    // the dummy inlay and error message inlays are after line end inlays
    public static void removeBlockInlays(Editor editor) {
        // dispose of all block inlays (displaying heap state)
        final var document = editor.getDocument();
        final var inlayModel = editor.getInlayModel();
        for (final var inlay : inlayModel.getBlockElementsInRange(0,
                document.getTextLength()-1, InlayRenderer.class)) {
            Disposer.dispose(inlay);
        }
    }

    public static void reset(Editor editor) {
        // retrieve and dispose of all inlays
        // first retrieve and dispose of all block inlays
        final var document = editor.getDocument();
        final var inlayModel = editor.getInlayModel();
        for (final var inlay : inlayModel.getBlockElementsInRange(0,
                document.getTextLength()-1, InlayRenderer.class)) {
            Disposer.dispose(inlay);
        }
        // then retrieve and dispose of all after line end inlays, including
        // dummy inlay, causing disposer to remove controller as well
        for (final var inlay : inlayModel.getAfterLineEndElementsInRange(0,
                document.getTextLength()-1, InlayRenderer.class)) {
            Disposer.dispose(inlay);
        }
        // reset gutter
        editor.getGutter().closeAllAnnotations();
        // remove highlighters highlighting errors
        final var markupModel = editor.getMarkupModel();
        for (final var highlighter : markupModel.getAllHighlighters()) {
            // this is a hack to find our highlighters, which all have
            // layer number 31337
            if (highlighter.getLayer() == 31337) {
                markupModel.removeHighlighter(highlighter);
            }
        }
    }
}

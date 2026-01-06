package edu.cmu.c0;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;
import scala.collection.Seq;
import viper.silicon.state.CheckInfo;
import viper.silicon.state.CheckPosition;
import viper.silver.ast.Positioned;
import viper.silver.ast.TranslatedPosition;

import java.awt.Font;

public class Check {
    public static final JBColor SELECTED = new JBColor(0xC88000, 0xC88000);
    private final CheckPosition myCheckPosition;
    private final Seq<CheckInfo> myCheckList;
    private int myOffset0;
    private int myOffset1;
    private RangeHighlighter myRangeHighlighter;
    private boolean mySelected;

    public Check(CheckPosition checkPosition, Seq<CheckInfo> checkList) {
        myCheckPosition = checkPosition;
        myCheckList = checkList;
        myOffset0 = -1;
        myOffset1 = -1;
        myRangeHighlighter = null;
        mySelected = false;
    }

    public Seq<CheckInfo> getCheckList() { return myCheckList; }
    public int getOffset0() { return myOffset0; }
    public int getOffset1() { return myOffset1; }
    public void setSelected(boolean selected) { mySelected = selected; }

    public void render(@NotNull Editor editor) {
        final var document = editor.getDocument();
        final var markupModel = editor.getMarkupModel();
        if (myRangeHighlighter != null) {
            markupModel.removeHighlighter(myRangeHighlighter);
        }
        if (myCheckPosition instanceof CheckPosition.GenericNode genericNode) {
            if (genericNode.node() instanceof Positioned positioned &&
                    positioned.pos() instanceof TranslatedPosition pos) {
                myOffset0 = document.getLineStartOffset(U.toIJ(pos.line())) + U.toIJ(pos.column());
                final var end = pos.end().get();
                myOffset1 = document.getLineStartOffset(U.toIJ(end.line())) + U.toIJ(end.column());
                final var color = mySelected ? SELECTED : JBColor.ORANGE;
                final var attr = new TextAttributes(JBColor.BLACK, color, color, EffectType.BOXED, Font.BOLD);
                myRangeHighlighter = markupModel.addRangeHighlighter(myOffset0, myOffset1,
                        U.LAYER_CHECK, attr, HighlighterTargetArea.EXACT_RANGE);
            }
        }
        // TODO CheckPosition.Loop loop
    }
}

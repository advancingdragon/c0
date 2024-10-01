package edu.cmu.c0;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;

import gvc.Main;
import viper.silicon.logger.SymbExLogger;
import viper.silicon.logger.SymbLog;
import viper.silicon.logger.records.data.MethodRecord;
import viper.silver.ast.TranslatedPosition;

import scala.collection.JavaConverters;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MyAction extends AnAction {
    public MyAction() { super(); }

    // VIPER/SILICON HAS 1-INDEX LINE COLUMN NUMBERS, BUT INTELLIJ HAS 0-INDEX LINE NUMBERS!!!
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        final var editor = event.getData(CommonDataKeys.EDITOR);
        if (editor == null) { return; }
        final var document = editor.getDocument();
        final var inlayModel = editor.getInlayModel();

        // event.getPresentation().setEnabledAndVisible(false);
        ApplicationManager.getApplication().runWriteAction(() ->
            Main.verifyFromPlugin(document.getText()));

        U.reset(editor);

        final Map<SymbLog, Method> methods = new HashMap<>();

        for (final var symbLog : JavaConverters.seqAsJavaList(SymbExLogger.memberList())) {
            if (SymbExLogger.m(symbLog) instanceof MethodRecord methodRecord &&
                    methodRecord.value().pos() instanceof TranslatedPosition) {
                final var method = new Method(symbLog.log());
                methods.put(symbLog, method);
            }
        }

        // create new controller and new dummy inlay
        final var controller = new Controller(methods);
        final var r = new InlayRenderer(JBColor.GREEN, "Verification successful");
        final var dummy = inlayModel.addAfterLineEndElement(0, false, r);
        assert dummy != null;
        // addEditorMouseListener takes a 2nd argument with a parentDisposable
        // object, the dummy inlay. When the parentDisposable is disposed of
        // the controller is removed as well
        editor.addEditorMouseListener(controller, dummy);
        controller.renderMethods(editor);

        // render errors
        final var markupModel = editor.getMarkupModel();
        for (final var error : JavaConverters.seqAsJavaList(SymbExLogger.errors())) {
            if (error.pos() instanceof TranslatedPosition pos) {
                final var offset0 = document.getLineStartOffset(U.toIJ(pos.line()));
                final var end = pos.end().get();
                final var offset1 = document.getLineStartOffset(U.toIJ(end.line()));
                final var attr = new TextAttributes(JBColor.BLACK, JBColor.RED, JBColor.RED, EffectType.BOXED, Font.BOLD);
                markupModel.addRangeHighlighter(offset0 + U.toIJ(pos.column()),
                        offset1 + U.toIJ(end.column()),
                        31337, attr, HighlighterTargetArea.EXACT_RANGE);
                inlayModel.addAfterLineEndElement(offset0, false,
                        new InlayRenderer(JBColor.RED, error.readableMessage()));
            }
        }
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        final var project = event.getProject();
        event.getPresentation().setEnabledAndVisible(project != null);
    }
}

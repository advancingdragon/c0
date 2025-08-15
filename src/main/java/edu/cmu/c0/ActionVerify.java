package edu.cmu.c0;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;

import gvc.Main;
import viper.silicon.logger.SymbExLogger;
import viper.silicon.logger.SymbLog;
import viper.silicon.logger.records.data.MethodRecord;
import viper.silver.ast.TranslatedPosition;

import scala.collection.JavaConverters;

import java.util.HashMap;
import java.util.Map;

public class ActionVerify extends DumbAwareAction {
    public ActionVerify() { super(); }

    // VIPER/SILICON HAS 1-INDEX LINE COLUMN NUMBERS, BUT INTELLIJ HAS 0-INDEX LINE NUMBERS!!!
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        final var editor = event.getData(CommonDataKeys.EDITOR);
        if (editor == null) { return; }
        // reset before doing anything else
        U.reset(editor);
        final var document = editor.getDocument();
        final var text = document.getText();

        // double check if this is actually thread-safe
        // all captured variables are read-only
        ApplicationManager.getApplication().executeOnPooledThread(() -> {
            // now in a background thread (BGT)
            final Main.GVC0Result result = Main.verifyFromPlugin(text);
            ApplicationManager.getApplication().invokeLater(() -> {
                // back in the Event Dispatch Thread (EDT) again
                final var inlayModel = editor.getInlayModel();
                final var markupModel = editor.getMarkupModel();

                if (result instanceof Main.GVC0ParserError parserError) {
                    final var i = parserError.failure().index();
                    markupModel.addRangeHighlighter(i, i, U.LAYER_ERROR, U.BAD, HighlighterTargetArea.LINES_IN_RANGE);
                    inlayModel.addAfterLineEndElement(0, false,
                            new InlayRenderer(JBColor.RED, "Syntax error"));
                    return;
                } else if (result instanceof Main.GVC0ValidatorError validatorError) {
                    for (final var error : JavaConverters.asJavaIterable(validatorError.errors())) {
                        final var span = error.node().span();
                        final var s = span.start().index();
                        final var e = span.end().index();
                        markupModel.addRangeHighlighter(s, e, U.LAYER_ERROR, U.BAD, HighlighterTargetArea.EXACT_RANGE);
                        inlayModel.addAfterLineEndElement(e, false,
                                new InlayRenderer(JBColor.RED, error.message()));
                    }
                    return;
                }

                final Map<SymbLog, Method> methods = new HashMap<>();

                for (final var symbLog : JavaConverters.seqAsJavaList(SymbExLogger.memberList())) {
                    if (SymbExLogger.m(symbLog) instanceof MethodRecord methodRecord &&
                            methodRecord.value().pos() instanceof TranslatedPosition pos) {
                        SymbExLogger.populateWhileLoops(methodRecord.value().bodyOrAssumeFalse().ss());
                        final var method = new Method(symbLog.log(), pos);
                        methods.put(symbLog, method);
                    }
                }

                // create new controller and new dummy inlay
                final var controller = new Controller(methods);
                final var r = SymbExLogger.errors().isEmpty() ?
                        new InlayRenderer(JBColor.GREEN, "No verification errors") :
                        new InlayRenderer(JBColor.RED, "Verification has errors");
                final var dummy = inlayModel.addAfterLineEndElement(0, false, r);
                assert dummy != null;
                // addEditorMouseListener takes a 2nd argument with a parentDisposable
                // object, the dummy inlay. When the parentDisposable is disposed of
                // the controller is removed as well
                editor.addEditorMouseListener(controller, dummy);
                controller.renderMethods(editor);
            });
        });
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

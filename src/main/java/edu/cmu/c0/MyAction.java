package edu.cmu.c0;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.ApplicationManager;
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

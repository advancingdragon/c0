package edu.cmu.c0;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.JSplitPane;

public class ActionVerify extends DumbAwareAction {
    public ActionVerify() { super(); }

    // VIPER/SILICON HAS 1-INDEX LINE COLUMN NUMBERS, BUT INTELLIJ HAS 0-INDEX LINE NUMBERS!!!
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        final var editor = event.getData(CommonDataKeys.EDITOR);
        if (editor == null) { return; }
        final var project = event.getData(CommonDataKeys.PROJECT);
        if (project == null) { return; }
        final var window = event.getData(EditorWindow.DATA_KEY);
        if (window == null) { return; }
        final var managerEx = U.resetAndUnsplit(editor, project, window);
        final var document = editor.getDocument();
        final var text = document.getText();
        final var temp = new LightVirtualFile("_.c0", document.getImmutableCharSequence());
        temp.setWritable(false);
        // most reliable way of opening a new file in a split window
        window.split(JSplitPane.HORIZONTAL_SPLIT, true, temp, false);
        final var newEditor = managerEx.openTextEditor(new OpenFileDescriptor(project, temp), false);
        if (newEditor == null) { return; }

        U.verify(text, newEditor);
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

package edu.cmu.c0;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

public class ActionReset extends DumbAwareAction {
    public ActionReset() { super(); }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        final var editor = event.getData(CommonDataKeys.EDITOR);
        if (editor != null) {
            U.reset(editor);
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

package edu.cmu.c0;

import com.intellij.openapi.command.CommandEvent;
import com.intellij.openapi.command.CommandListener;
import com.intellij.openapi.command.undo.UndoManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import org.jetbrains.annotations.NotNull;

public class ResetListener implements CommandListener {
    @Override
    public void commandStarted(@NotNull CommandEvent event) {
        final var project = event.getProject();
        if (project == null) { // getInstance takes @NotNull Project
            return;
        }
        final var name = event.getCommandName();
        final var className = event.getCommand().getClass().getSimpleName();
        final var undo = UndoManager.getInstance(project);
        // reset current editor when user performs an Undo or Redo action or
        // navigates away from the current editor to a different editor
        if (undo.isUndoOrRedoInProgress() ||
                name.equals("EditorChange") ||
                className.startsWith("NavigationUtil$$Lambda$")) {
            final var editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
            if (editor != null) {
                U.reset(editor);
            }
        }
    }
}

package edu.cmu.c0.tutorial;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefDisplayHandlerAdapter;

public class DisplayHandler extends CefDisplayHandlerAdapter {
    @Override
    public void onAddressChange(CefBrowser browser, CefFrame frame, String url) {
        final var projects = ProjectManager.getInstance().getOpenProjects();
        for (final var project : projects) {
            // comments say to check if project isDisposed()
            if (!project.isDisposed() && project.getName().equals("test")) {
                ApplicationManager.getApplication().invokeLater(() -> {
                    // must run in the Event Dispatch Thread (EDT)
                    final var managerEx = FileEditorManagerEx.getInstanceEx(project);
                    final var c0Name = url.startsWith("https://nguyen.bz/alethiometer-tutorial/book/chapter_2.html") ?
                            "multiply.c0" :
                            url.startsWith("https://nguyen.bz/alethiometer-tutorial/book/chapter_3.html") ?
                                    "multiply.c0" :
                                    url.startsWith("https://nguyen.bz/alethiometer-tutorial/book/chapter_4.html") ?
                                            "permissions.c0" :
                                            "wallet.c0";
                    final var files = FilenameIndex.getVirtualFilesByName(c0Name, GlobalSearchScope.projectScope(project));
                    if (files.size() == 1) {
                        managerEx.openFile(files.toArray(new VirtualFile[]{})[0], true);
                    }
                });
            }
        }
    }
}

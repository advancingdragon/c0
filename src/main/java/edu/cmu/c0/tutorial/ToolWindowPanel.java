package edu.cmu.c0.tutorial;

import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;

public class ToolWindowPanel extends SimpleToolWindowPanel {
    private final JBCefBrowser myBrowser;
    public ToolWindowPanel() {
        super(true);
        if (JBCefApp.isSupported()) {
            myBrowser = new JBCefBrowser("https://nguyen.bz/alethiometer-tutorial/book/index.html");
            myBrowser.getJBCefClient().getCefClient().addDisplayHandler(new DisplayHandler());
            add(myBrowser.getComponent());
        } else {
            myBrowser = null;
        }
    }
}

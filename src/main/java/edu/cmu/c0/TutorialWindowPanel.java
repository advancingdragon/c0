package edu.cmu.c0;

import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;

public class TutorialWindowPanel extends SimpleToolWindowPanel {
    private final JBCefBrowser myBrowser;
    public TutorialWindowPanel() {
        super(true);
        if (JBCefApp.isSupported()) {
            myBrowser = new JBCefBrowser("https://nguyen.bz/alethiometer-tutorial/book/index.html");
            myBrowser.getJBCefClient().getCefClient().addDisplayHandler(new TutorialDisplayHandler());
            add(myBrowser.getComponent());
        } else {
            myBrowser = null;
        }
    }
}

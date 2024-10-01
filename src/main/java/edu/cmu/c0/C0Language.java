package edu.cmu.c0;

import com.intellij.lang.Language;

public class C0Language extends Language {
    public static final C0Language INSTANCE = new C0Language();

    private C0Language() {
        super("C0");
    }
}

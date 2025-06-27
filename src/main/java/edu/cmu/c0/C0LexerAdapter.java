package edu.cmu.c0;

import com.intellij.lexer.FlexAdapter;

public class C0LexerAdapter extends FlexAdapter {
    public C0LexerAdapter() {
        super (new C0Lexer(null));
    }
}

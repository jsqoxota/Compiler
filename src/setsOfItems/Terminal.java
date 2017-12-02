package setsOfItems;

import lexer.Tag;

/**
 * 终结符
 */
public class Terminal {
    private String lexeme;

    public Terminal(String lexeme){
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return lexeme;
    }
}

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

    @Override
    public boolean equals(Object obj) {
        if(obj == null)return false;

        if(this == obj)return true;

        if(!(obj instanceof Terminal))return false;

        Terminal terminal = (Terminal)obj;
        if(this.lexeme == terminal.lexeme)return true;
        else return false;
    }
}

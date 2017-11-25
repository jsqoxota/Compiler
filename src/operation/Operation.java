package operation;

import lexer.Token;

abstract public class Operation extends Token {
    private String lexeme = "";

    public Operation(int tag, String lexeme) {
        super(tag);
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return lexeme;
    }

    abstract public String getTag();
}

package lexer;

public class Identifier extends Word{
    public Identifier(String lexeme) {
        super(lexeme, Tag.ID);
    }
}

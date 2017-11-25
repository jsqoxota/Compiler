package lexer;

public class Identifier extends Word{
    @Override
    public String getTag() {
        return "id";
    }

    public Identifier(String lexeme) {
        super(lexeme, Tag.ID);
    }
}

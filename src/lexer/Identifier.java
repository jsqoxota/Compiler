package lexer;

public class Identifier extends Word{
    @Override
    public String getTag() {
        return "id";
    }

    public Identifier(String lexeme) {
        super(lexeme, Tag.ID);
    }

    @Override
    public int hashCode() {
        return this.lexeme.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return lexeme.equals(obj.toString());
    }
}

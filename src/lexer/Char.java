package lexer;

public class Char extends Token {
    private String s;
    public Char(String s){
        super(Tag.CHAR);
        this.s = s;
    }

    @Override
    public String getTag() {
        return "char";
    }

    @Override
    public String toString() {
        return s;
    }
}

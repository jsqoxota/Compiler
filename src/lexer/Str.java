package lexer;

public class Str extends Token {
    private String string = "";
    public Str(String string) {
        super(Tag.TEMP);
        this.string = string;
    }

    @Override
    public String toString() {
        return '"'+ string + '"';
    }
}

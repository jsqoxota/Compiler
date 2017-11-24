package lexer;

public class Symbol extends Token {
    public Symbol(int tag){
        super(tag);
    }

    @Override
    public String toString() {
        return ""+(char)tag;
    }
}

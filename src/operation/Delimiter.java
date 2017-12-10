package operation;

public class Delimiter extends Operation{
    private Delimiter(int tag, String lexeme) {
        super(tag, lexeme);
    }

    @Override
    public String toString() {
        return ""+(char)tag;
    }

    @Override
    public String getTag() {
        return "delimiter";
    }

    public static final Delimiter
            curlyBraL  = new Delimiter('{', "{"),
            curlyBraR  = new Delimiter('}', "}");

    public static Delimiter isDelimiter(String lexeme){
        if( curlyBraL.toString().equals(lexeme))return curlyBraL;
        if( curlyBraR.toString().equals(lexeme))return curlyBraR;
        return null;
    }
}

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
            roundBraL  = new Delimiter('(', "("),
            squareBraL = new Delimiter('[', "["),
            curlyBraR  = new Delimiter('}', "}"),
            roundBraR  = new Delimiter(')', ")"),
            squareBraR = new Delimiter(']', "]");
}

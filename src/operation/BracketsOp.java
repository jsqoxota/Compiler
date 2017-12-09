package operation;

/**
 * 括号运算符
 */
public class BracketsOp extends Operation {
    private BracketsOp(int tag, String lexeme) {
        super(tag, lexeme);
    }

    @Override
    public String toString() {
        return ""+(char)tag;
    }

    @Override
    public String getTag() {
        return "bracket";
    }

    public static final BracketsOp
            roundBraL  = new BracketsOp('(', "("),
            squareBraL = new BracketsOp('[', "["),
            roundBraR  = new BracketsOp(')', ")"),
            squareBraR = new BracketsOp(']', "]");

    public static boolean isBracketsOp(String lexeme){
        if( roundBraL.toString().equals(lexeme))return true;
        if( squareBraL.toString().equals(lexeme))return true;
        if( roundBraR.toString().equals(lexeme))return true;
        if( squareBraR.toString().equals(lexeme))return true;
        return false;
    }
}

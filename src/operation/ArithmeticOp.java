package operation;

import lexer.Tag;

/**
 * 算术运算符
 */
public class ArithmeticOp extends Operation {
    private ArithmeticOp(int tag, String lexeme) {
        super(tag, lexeme);
    }

    @Override
    public String getTag() {
        return "ariOp";
    }

    public static final ArithmeticOp
            add = new ArithmeticOp('+', "+"),
            sub = new ArithmeticOp('-', "-"),
            div = new ArithmeticOp('/', "/"),
            mul = new ArithmeticOp('*', "*"),
            rem = new ArithmeticOp('%', "%"),
            inc = new ArithmeticOp(Tag.INC,     "++"),
            dec = new ArithmeticOp(Tag.DEC,     "--");

    public static boolean isArithmeticOp(String lexeme){
        if( add.toString().equals(lexeme))return true;
        if( sub.toString().equals(lexeme))return true;
        if( div.toString().equals(lexeme))return true;
        if( mul.toString().equals(lexeme))return true;
        if( rem.toString().equals(lexeme))return true;
        if( inc.toString().equals(lexeme))return true;
        if( dec.toString().equals(lexeme))return true;
        return false;
    }

}

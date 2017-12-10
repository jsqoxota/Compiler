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

    public static ArithmeticOp isArithmeticOp(String lexeme){
        if( add.toString().equals(lexeme))return add;
        if( sub.toString().equals(lexeme))return sub;
        if( div.toString().equals(lexeme))return div;
        if( mul.toString().equals(lexeme))return mul;
        if( rem.toString().equals(lexeme))return rem;
        if( inc.toString().equals(lexeme))return inc;
        if( dec.toString().equals(lexeme))return dec;
        return null;
    }

}

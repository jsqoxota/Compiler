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
}

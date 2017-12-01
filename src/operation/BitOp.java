package operation;

import lexer.Tag;

/**
 * 位运算符
 */
public class BitOp extends Operation {
    private BitOp(int tag, String lexeme) {
        super(tag, lexeme);
    }

    @Override
    public String getTag() {
        return "bitOp";
    }

    public static final BitOp
            or  = new BitOp('|',    "|"),
            and = new BitOp('&',    "&"),
            xor = new BitOp('^',    "^"),
            not = new BitOp('~',    "~"),
            sal = new BitOp(Tag.SAL,    "<<"),
            sar = new BitOp(Tag.SAR,    ">>"),
            shr = new BitOp(Tag.SHR,    "<<<");
}

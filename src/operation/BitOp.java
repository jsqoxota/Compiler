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
            shr = new BitOp(Tag.SHR,    ">>>");


    public static BitOp isBitOp(String lexeme){
        if( or.toString().equals(lexeme))return or;
        if( and.toString().equals(lexeme))return and;
        if( xor.toString().equals(lexeme))return xor;
        if( not.toString().equals(lexeme))return not;
        if( sal.toString().equals(lexeme))return sar;
        if( sar.toString().equals(lexeme))return sar;
        if( shr.toString().equals(lexeme))return shr;
        return null;
    }

}

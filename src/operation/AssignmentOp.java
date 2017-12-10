package operation;

import lexer.Tag;

/**
 * 赋值运算符
 */
public class AssignmentOp extends Operation {
    private AssignmentOp(int tag, String lexeme) {
        super(tag, lexeme);
    }

    @Override
    public String getTag() {
        return "assOp";
    }

    public static final AssignmentOp
            equal   = new AssignmentOp('=',     "="),
            az      = new AssignmentOp(Tag.AZ ,     "+="),
            sz      = new AssignmentOp(Tag.SZ,      "-="),
            mz      = new AssignmentOp(Tag.MZ,      "*="),
            dz      = new AssignmentOp(Tag.DZ,      "/="),
            pz      = new AssignmentOp(Tag.PZ,      "%="),
            andZ    = new AssignmentOp(Tag.ANDZ,    "&="),
            orz     = new AssignmentOp(Tag.ORZ,     "|="),
            xorZ    = new AssignmentOp(Tag.XORZ,    "^="),
            salZ    = new AssignmentOp(Tag.SALZ,    "<<="),
            sarZ    = new AssignmentOp(Tag.SARZ,    ">>="),
            shrZ    = new AssignmentOp(Tag.SHRZ,    ">>>=");

    public static AssignmentOp isAssignmentOp(String lexeme){
        if( equal.toString() .equals(lexeme))return equal;
        if( az.toString() .equals(lexeme))return az;
        if( sz.toString() .equals(lexeme))return sz;
        if( mz.toString() .equals(lexeme))return mz;
        if( dz.toString() .equals(lexeme))return dz;
        if( pz.toString() .equals(lexeme))return pz;
        if( andZ.toString().equals(lexeme))return andZ;
        if( orz.toString().equals(lexeme))return orz;
        if( xorZ.toString().equals(lexeme))return xorZ;
        if( salZ.toString().equals(lexeme))return salZ;
        if( sarZ.toString().equals(lexeme))return sarZ;
        if( shrZ.toString().equals(lexeme))return shrZ;
        return null;
    }

}

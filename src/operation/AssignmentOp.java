package operation;

import lexer.Tag;

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
            inc     = new AssignmentOp(Tag.INC,     "++"),
            dec     = new AssignmentOp(Tag.DEC,     "--");

}

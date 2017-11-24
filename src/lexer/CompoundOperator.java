package lexer;

public class CompoundOperator extends Word {
    private CompoundOperator(String lexeme, int tag) {
        super(lexeme, tag);
    }

    public static final CompoundOperator
            and     = new CompoundOperator("&&",    Tag.AND ),  or  = new CompoundOperator("||", Tag.OR  ),
            eq      = new CompoundOperator("==",    Tag.EQ  ),  ne  = new CompoundOperator("!=", Tag.NE  ),
            le      = new CompoundOperator("<=",    Tag.LE  ),  ge  = new CompoundOperator(">=", Tag.GE  ),
            inc     = new CompoundOperator("++",    Tag.INC ),  dec = new CompoundOperator("--", Tag.DEC ),
            az      = new CompoundOperator("+=",    Tag.AZ  ),  sz  = new CompoundOperator("-=", Tag.SZ  ),
            mz      = new CompoundOperator("*=",    Tag.MZ  ),  dz  = new CompoundOperator("/=", Tag.DZ  ),
            pz      = new CompoundOperator("%=",    Tag.PZ  ),  sal = new CompoundOperator("<<", Tag.SAL ),
            sar     = new CompoundOperator(">>",    Tag.SAR ),  shr = new CompoundOperator("<<<",Tag.SHR ),
            andz    = new CompoundOperator("&=",    Tag.ANDZ),  orz = new CompoundOperator("|=", Tag.ORZ ),
            xorz    = new CompoundOperator("^=",    Tag.XORZ);
}

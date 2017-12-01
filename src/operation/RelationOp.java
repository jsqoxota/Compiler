package operation;

import lexer.Tag;

/**
 * 关系运算符
 */
public class RelationOp extends Operation {
    private RelationOp(int tag, String lexeme) {
        super(tag, lexeme);
    }

    @Override
    public String getTag() {
        return "relOp";
    }

    public static final RelationOp
            ab  = new RelationOp('>',   ">"),
            be  = new RelationOp('<',   "<"),
            eq  = new RelationOp(Tag.EQ,"=="),
            ne  = new RelationOp(Tag.NE,"!="),
            le  = new RelationOp(Tag.LE,"<="),
            ge  = new RelationOp(Tag.GE,">=");
}

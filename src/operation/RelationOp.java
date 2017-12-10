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

    public static RelationOp isRelationOp(String lexeme){
        if(ab.toString().equals(lexeme)) return ab;
        if(be.toString().equals(lexeme)) return be;
        if(eq.toString().equals(lexeme)) return eq;
        if(ne.toString().equals(lexeme)) return ne;
        if(le.toString().equals(lexeme)) return le;
        if(ge.toString().equals(lexeme)) return ge;
        return null;
    }
}

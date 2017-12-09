package operation;

import lexer.Tag;

/**
 * 逻辑运算符
 */
public class LogicOp extends Operation {
    private LogicOp(int tag, String lexeme) {
        super(tag, lexeme);
    }

    @Override
    public String getTag() {
        return "logicOp";
    }

    public static final LogicOp
            not     = new LogicOp('!',  "!"),
            and     = new LogicOp(Tag.AND,  "&&"),
            or      = new LogicOp(Tag.OR,   "||");

    public static boolean isLogicOp(String lexeme){
        if( not.toString().equals(lexeme))return true;
        if( and.toString().equals(lexeme))return true;
        if (or.toString().equals(lexeme))return true;
        return false;
    }
}

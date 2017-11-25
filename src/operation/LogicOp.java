package operation;

import lexer.Tag;

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
}

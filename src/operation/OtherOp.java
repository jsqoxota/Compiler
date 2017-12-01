package operation;

import lexer.Tag;

public class OtherOp extends Operation {
    private OtherOp(int tag, String lexeme) {
        super(tag, lexeme);
    }

    @Override
    public String getTag() {
        return "otherOp";
    }

    public final static OtherOp
        point       = new OtherOp('.', "."),
        questionM   = new OtherOp('?', "?"),
        quotationM  = new OtherOp(':', ":"),
        semicolon   = new OtherOp(';', ";"),
        comma       = new OtherOp(',', ","),
        lambda      = new OtherOp(Tag.LAMBDA ,"->");
}

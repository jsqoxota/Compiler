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

    public static boolean isOtherOp(String lexeme){
        if(point.toString().equals(lexeme))return true;
        if(questionM.toString().equals(lexeme))return true;
        if(quotationM.toString().equals(lexeme))return true;
        if(semicolon.toString().equals(lexeme))return true;
        if(comma.toString().equals(lexeme))return true;
        if(lambda.toString().equals(lexeme))return true;
        return false;
    }
}

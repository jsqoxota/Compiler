package operation;

import lexer.OtherWord;
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

    public static OtherOp isOtherOp(String lexeme){
        if(point.toString().equals(lexeme))return point;
        if(questionM.toString().equals(lexeme))return questionM;
        if(quotationM.toString().equals(lexeme))return quotationM;
        if(semicolon.toString().equals(lexeme))return semicolon;
        if(comma.toString().equals(lexeme))return comma;
        if(lambda.toString().equals(lexeme))return lambda;
        return null;
    }
}

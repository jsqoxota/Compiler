package operation;

public class OtherOp extends Operation {
    private OtherOp(int tag, String lexeme) {
        super(tag, lexeme);
    }

    @Override
    public String getTag() {
        return "otherOp";
    }

    
}

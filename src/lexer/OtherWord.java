package lexer;

public class OtherWord extends Word{
    protected OtherWord(String lexeme, int tag){
        super(lexeme, tag);
    }

    @Override
    public String getTag() {
        return "other";
    }

    public static OtherWord
        num = new OtherWord("num", Tag.NUM),
        id = new OtherWord("id", Tag.ID),
        real = new OtherWord("real", Tag.REAL);

    public static boolean isOtherWord(String lexeme){
        if( num.toString().equals(lexeme))return true;
        if( id.toString().equals(lexeme))return true;
        if( real.toString().equals(lexeme))return true;
        else return false;
    }
}

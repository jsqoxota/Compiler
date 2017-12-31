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
        real = new OtherWord("real", Tag.REAL),
        epsilon = new OtherWord("ε", Tag.EPSILON),
        $ = new OtherWord("$", Tag.$),
        basic = new OtherWord("basic", Tag.BASIC);
//        a = new OtherWord("a", Tag.OTHER),
//        b = new OtherWord("b", Tag.OTHER),
//        c = new OtherWord("c", Tag.OTHER),
//        d = new OtherWord("d", Tag.OTHER);

    public static boolean isOtherWord(String lexeme){
        if( num.toString().equals(lexeme))return true;
        if( id.toString().equals(lexeme))return true;
        if( real.toString().equals(lexeme))return true;
        if( epsilon.toString().equals(lexeme))return true;
        if( basic.toString().equals(lexeme))return true;
        //if( a.toString().equals(lexeme))return true;
        //if (b.toString().equals(lexeme))return true;
        //if (c.toString().equals(lexeme))return true;
        //if (d.toString().equals(lexeme))return true;
        else return false;
    }
}

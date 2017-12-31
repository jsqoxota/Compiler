package lexer;

public class Boolean extends ReservedWord{
    private String value;
    private Boolean(String lexeme){
        super(lexeme,Tag.BOOLEAN);
        this.value = lexeme;
    }

    public static final Boolean
        True = new Boolean("true"),
        False = new Boolean("false");

    @Override
    public String getTag() {
        return "boolean";
    }

    @Override
    public String toString() {
        return "" + value;
    }

    public static Boolean isBoolean(String s){
        if(s.equals(True.toString()))return True;
        else if(s.equals(False.toString()))return False;
        else return null;
    }
}

package lexer;

public class Word extends Token {
    protected String lexeme = "";    //词素
    public Word(String lexeme, int tag){
       super(tag);
       this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return lexeme;
    }

    public static final Word
        and = new Word("&&",    Tag.AND),  or = new Word("||",  Tag.OR),
        eq  = new Word("==",    Tag.EQ ),  ne = new Word("!=",  Tag.NE),
        le  = new Word("<=",    Tag.LE ),  ge = new Word(">=",  Tag.GE),
        minus   = new Word("minus", Tag.MINUS   ),//负号
        temp    = new Word("t",     Tag.TEMP    );
}

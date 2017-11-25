package lexer;

abstract public class Word extends Token {
    protected String lexeme = "";           //词素
    public Word(String lexeme, int tag){
       super(tag);
       this.lexeme = lexeme;
    }

    abstract public String getTag();

    @Override
    public String toString() {
        return lexeme;
    }
}

package lexer;

public class Real extends Token {
    public final float value;
    public Real(float value){
        super(Tag.REAL);
        this.value = value;
    }

    @Override
    public String getTag() {
        return "real";
    }

    @Override
    public String toString() {
        return "" + value;
    }
}

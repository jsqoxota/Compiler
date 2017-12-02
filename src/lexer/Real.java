package lexer;

public class Real extends Token {
    public final double value;
    public Real(double value){
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

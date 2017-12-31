package inter;

import lexer.Token;
import symbol.Type;

public class Expr {
    protected Token op;
    protected Type type;
    Expr(Token token, Type type){
        op = token;
        this.type = type;
    }

    /**>>>>>>>>>>>>getter and setter and override<<<<<<<<<<*/
    @Override
    public String toString() {
        return op.toString();
    }

    public Token getOp() {
        return op;
    }

    public Type getType() {
        return type;
    }
}

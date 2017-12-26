package inter;

import lexer.Token;
import symbol.Type;

public class Expr extends Node {
    private Token op;
    private Type type;
    Expr(Token token, Type type){
        op = token;
        this.type = type;
    }
    public Expr gen(){
        return this;
    }
    public Expr reduce(){
        return this;
    }
    public void jumping(int i, int f){

    }
    public void emitjumps(String test, int t, int f){
        if( t != 0 && f != 0){
            emit( "if " + test + " goto L" +t);
            emit("goto L" + f);
        }
        else if( t != 0)emit("if " + test + " goto L" + t);
        else if( t != 0)emit("if false " + test + " goto L" + f);
        else ;
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

package inter;

import lexer.Identifier;
import lexer.Word;
import symbol.Type;

public class Id extends Expr {
    public int offset;
    public Id(Identifier id, Type p){
        super(id, p);
        offset = p.getWidth();
    }
}

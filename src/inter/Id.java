package inter;

import lexer.Word;
import symbol.Type;

public class Id extends Expr {
    public int offset;
    public Id(Word id, Type p, int b){
        super(id, p);
        offset = b;
    }
}

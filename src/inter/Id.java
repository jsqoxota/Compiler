package inter;

import lexer.Identifier;
import symbol.Array;
import symbol.Type;

public class Id extends Expr {
    private int offset;
    private byte[] value;

    public Id(Identifier id, Type p) {
        super(id, p);
        offset = p.getWidth();
    }

    public void applySpace() {
        value = new byte[type.getWidth()];
    }

    public int getOffset() {
        return offset;
    }

}

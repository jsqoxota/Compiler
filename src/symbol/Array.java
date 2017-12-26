package symbol;

import lexer.Tag;

public class Array extends Type{
    private Type of;
    private int size = 1;
    public Array(int size, Type p){                                     //一维数组
        super("[]", Tag.INDEX, size * p.getWidth());
        this.size = size;
        of = p;
    }

    public Array(int size, Array array){                                //多维数组
        super("[]", Tag.INDEX, size * array.getWidth());
        this.size = size;
        of = array;
    }

    /**>>>>>>>>>>>>>> proc: getter setter override <<<<<<<<<<<<<<<<<*/
    @Override
    public String toString() {
        return "[" + size + "] " + of.toString();
    }

    @Override
    public String getTag() {
        return "array";
    }

    public Type getOf() {
        return of;
    }

    public int getSize() {
        return size;
    }
}

package symbol;

import lexer.Tag;

public class Array extends Type{
    
    private Type of;                //数组的元素类型
    private int size = 1;           //数组元素个数
    public Array(int size, Type p){                                     //一维数组
        super("[]", Tag.INDEX, size * p.getWidth());
        this.size = size;
        of = p;
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

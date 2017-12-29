package inter;

import symbol.Type;

public class TypeS {
    private static Type type;                               //basic的类型

    public static Type getType() {
        return type;
    }

    public static void setType(symbol.Type basic){
        type = basic;
    }
}

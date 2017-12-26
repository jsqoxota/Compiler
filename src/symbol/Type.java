package symbol;

import lexer.ReservedWord;
import lexer.Tag;
import lexer.Word;

/**
 * 基本数据类型
 */
public class Type extends ReservedWord {
    private int width = 0;      //width存储分配

    public Type(String lexeme, int tag, int width){
        super(lexeme,tag);
        this.width = width;
    }

    private static final Type
            Byte        = new Type("byte",      Tag.BASIC, 1    ),
            Short       = new Type("short",     Tag.BASIC, 2    ),
            Int         = new Type("int",       Tag.BASIC, 4    ),
            Long        = new Type("long",      Tag.BASIC, 8    ),
            Float       = new Type("float",     Tag.BASIC, 4    ),
            Double      = new Type("double",    Tag.BASIC, 8    ),
            Char        = new Type("char",      Tag.BASIC, 1    ),
            Boolean     = new Type("boolean",   Tag.BASIC, 1    );

    public static boolean numeric(Type p){
        if(p == Type.Char || p == Type.Int || p == Type.Float) return true;
        else return false;
    }

    public static Type max(Type p1, Type p2){
        if( ! numeric(p1) || ! numeric(p2))return null;
        else if ( p1 == Type.Float || p2 == Type.Float)return Type.Float;
        else if ( p1 == Type.Int   || p2 == Type.Int)return Type.Int;
        else return Type.Char;
    }

    public int getWidth() {
        return width;
    }

    public static Type getInt() {
        return Int;
    }

    public static Type getFloat() {
        return Float;
    }

    public static Type getChar() {
        return Char;
    }

    public static Type getBoolean() {
        return Boolean;
    }

    public static Type getDouble() {
        return Double;
    }

    public static Type getByte() {
        return Byte;
    }

    public static Type getShort() {
        return Short;
    }

    public static Type getLong() {
        return Long;
    }

    @Override
    public String getTag() {
        return "basic";
    }
}

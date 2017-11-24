package symbol;

import lexer.ReservedWord;
import lexer.Tag;
import lexer.Word;

/**
 * 基本数据类型
 */
public class Type extends ReservedWord {
    private int width = 0;      //width存储分配

    private Type(String lexeme, int tag, int width){
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
}

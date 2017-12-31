package lexer;

import symbol.Type;

import java.util.HashMap;

public class ReservedWord extends Word {
    private static final HashMap<String, ReservedWord> reservedWords = new HashMap<>();

    protected ReservedWord(String lexeme, int tag){
        super(lexeme, tag);
    }

    //判断是否是关键字
    public static Object isReservedWord(String lexeme){
        if(reservedWords.size() == 0) {
            reserveReservedWord();
        }
        return reservedWords.get(lexeme);
    }

    //保存关键字s
    private static void reserveReservedWord(){
        reserve(new ReservedWord("abstract",    Tag.ABSTRACT    ));
        reserve(new ReservedWord("assert",      Tag.ASSERT      ));
        reserve(new ReservedWord("break",       Tag.BREAK       ));
        //reserve(new ReservedWord("boolean",     Tag.BOOLEAN     ));
        //reserve(new ReservedWord("byte",        Tag.BYTE        ));
        reserve(new ReservedWord("case",        Tag.CASE        ));
        reserve(new ReservedWord("catch",       Tag.CATCH       ));
        //reserve(new ReservedWord("char",        Tag.CHAR        ));
        reserve(new ReservedWord("class",       Tag.CLASS       ));
        reserve(new ReservedWord("continue",    Tag.CONTINUE    ));
        reserve(new ReservedWord("default",     Tag.DEFAULT     ));
        reserve(new ReservedWord("do",          Tag.DO          ));
        //reserve(new ReservedWord("double",      Tag.DOUBLE      ));
        reserve(new ReservedWord("else",        Tag.ELSE        ));
        reserve(new ReservedWord("extends",     Tag.EXTENDS     ));
        //reserve(new ReservedWord("false",       Tag.FALSE       ));
        reserve(new ReservedWord("final",       Tag.FINAL       ));
        reserve(new ReservedWord("finally",     Tag.FINALLY     ));
        //reserve(new ReservedWord("float",       Tag.FLOAT       ));
        reserve(new ReservedWord("for",         Tag.FOR         ));
        reserve(new ReservedWord("if",          Tag.IF          ));
        reserve(new ReservedWord("implements",  Tag.IMPLEMENTS  ));
        reserve(new ReservedWord("import",      Tag.IMPORT      ));
        reserve(new ReservedWord("index",       Tag.INDEX       ));
        reserve(new ReservedWord("instanceof",  Tag.INSTANCEOF  ));
        //reserve(new ReservedWord("int",         Tag.INT         ));
        reserve(new ReservedWord("interface",   Tag.INTERFACE   ));
        //reserve(new ReservedWord("long",        Tag.LONG        ));
        reserve(new ReservedWord("native",      Tag.NATIVE      ));
        reserve(new ReservedWord("new",         Tag.NEW         ));
        reserve(new ReservedWord("null",        Tag.NULL        ));
        reserve(new ReservedWord("package",     Tag.PACKAGE     ));
        reserve(new ReservedWord("private",     Tag.PRIVATE     ));
        reserve(new ReservedWord("protected",   Tag.PROTECTED   ));
        reserve(new ReservedWord("public",      Tag.PUBLIC      ));
        reserve(new ReservedWord("return",      Tag.RETURN      ));
        //reserve(new ReservedWord("short",       Tag.SHORT       ));
        reserve(new ReservedWord("static",      Tag.STATIC      ));
        reserve(new ReservedWord("super",       Tag.SUPER       ));
        reserve(new ReservedWord("switch",      Tag.SWITCH      ));
        reserve(new ReservedWord("synchronized",Tag.SYNCHRONIZED));
        reserve(new ReservedWord("this",        Tag.THIS        ));
        reserve(new ReservedWord("throw",       Tag.THROW       ));
        reserve(new ReservedWord("throws",      Tag.THROWS      ));
        reserve(new ReservedWord("transient",   Tag.TRANSIENT   ));
        reserve(new ReservedWord("try",         Tag.TRY         ));
        //reserve(new ReservedWord("true",        Tag.TRUE        ));
        reserve(new ReservedWord("void",        Tag.VOID        ));
        reserve(new ReservedWord("volatile",    Tag.VOLATILE    ));
        reserve(new ReservedWord("while",       Tag.WHILE       ));
        reserve(Type.getByte());
        reserve(Type.getShort());
        reserve(Type.getInt());
        reserve(Type.getLong());
        reserve(Type.getFloat());
        reserve(Type.getDouble());
        reserve(Type.getChar());
        reserve(Type.getBoolean());
        reserve(Boolean.True);
        reserve(Boolean.False);
    }

    //将关键字保存到HashMap
    private static void reserve(ReservedWord reservedWord){
        reservedWords.put(reservedWord.toString(), reservedWord);
    }

    @Override
    public String getTag() {
        return "key";
    }
}

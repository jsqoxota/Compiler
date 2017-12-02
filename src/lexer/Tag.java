package lexer;

public class Tag {
    public final static int
        ABSTRACT    = 256,  AND         = 257,  ASSERT      = 258,  BASIC       = 259,  BREAK       = 260,
        BOOLEAN     = 261,  BYTE        = 262,  CASE        = 263,  CATCH       = 264,  CHAR        = 265,
        CLASS       = 266,  CONTINUE    = 267,  DEFAULT     = 268,  DO          = 269,  DOUBLE      = 270,
        ELSE        = 271,  EQ          = 272,  EXTENDS     = 273,  FALSE       = 274,  FINAL       = 275,
        FINALLY     = 276,  FLOAT       = 277,  FOR         = 278,  GE          = 279,  ID          = 280,
        IF          = 281,  IMPLEMENTS  = 282,  IMPORT      = 283,  INDEX       = 284,  INSTANCEOF  = 285,
        INT         = 286,  INTERFACE   = 287,  LE          = 288,  LONG        = 289,  MINUS       = 290,
        NATIVE      = 291,  NE          = 292,  NEW         = 293,  NULL        = 294,  NUM         = 295,
        OR          = 296,  PACKAGE     = 297,  PRIVATE     = 298,  PROTECTED   = 299,  PUBLIC      = 300,
        REAL        = 301,  RETURN      = 302,  SHORT       = 303,  STATIC      = 304,  SUPER       = 305,
        SWITCH      = 306,  SYNCHRONIZED= 307,  TEMP        = 308,  THIS        = 309,  THROW       = 310,
        THROWS      = 311,  TRANSIENT   = 312,  TRY         = 313,  TRUE        = 314,  VOID        = 315,
        VOLATILE    = 316,  WHILE       = 317,  INC         = 318,  DEC         = 319,  AZ          = 320,
        SZ          = 321,  MZ          = 322,  DZ          = 323,  PZ          = 324,  SAL         = 325,
        SAR         = 326,  SHR         = 327,  ANDZ        = 328,  ORZ         = 329,  XORZ        = 330,
        SALZ        = 331,  SARZ        = 332,  LAMBDA      = 333,  SHRZ        = 334,  STRING      = 335;

    public static final String tagToString(int tag){
        if(tag < 0)return null;
        else if(tag < 256) return "" + (char)tag;
        else {
            String s = "";
            switch (tag){
                case 256:s = "abstract";    break;
                case 257:s = "&&";          break;
                case 258:s = "assert";      break;
                case 259:s = "";    break;
                case 260:s = "break";       break;
                case 261:s = "boolean";     break;
                case 262:s = "byte";        break;
                case 263:s = "case";        break;
                case 264:s = "catch";       break;
                case 265:s = "char";        break;
                case 266:s = "class";       break;
                case 267:s = "continue";    break;
                case 268:s = "default";     break;
                case 269:s = "do";          break;
                case 270:s = "double";      break;
                case 271:s = "else";        break;
                case 272:s = "==";          break;
                case 273:s = "extends";     break;
                case 274:s = "false";       break;
                case 275:s = "final";       break;
                case 276:s = "finally";     break;
                case 277:s = "float";       break;
                case 278:s = "for";         break;
                case 279:s = ">=";          break;
                case 280:s = "";    break;
                case 281:s = "if";          break;
                case 282:s = "implements";  break;
                case 283:s = "import";      break;
                case 284:s = "";    break;
                case 285:s = "instanceof";  break;
                case 286:s = "int";         break;
                case 287:s = "interface";   break;
                case 288:s = "<=";          break;
                case 289:s = "long";        break;
                case 290:s = "";    break;
                case 291:s = "native";      break;
                case 292:s = "!=";          break;
                case 293:s = "new";         break;
                case 294:s = "null";        break;
                case 295:s = "";    break;
                case 296:s = "||";          break;
                case 297:s = "package";     break;
                case 298:s = "private";     break;
                case 299:s = "protect";     break;
                case 300:s = "public";      break;
                case 301:s = "";    break;
                case 302:s = "return";      break;
                case 303:s = "short";       break;
                case 304:s = "static";      break;
                case 305:s = "super";       break;
                case 306:s = "switch";      break;
                case 307:s = "synchronized";break;
                case 308:s = "";    break;
                case 309:s = "this";        break;
                case 310:s = "throw";       break;
                case 311:s = "throws";      break;
                case 312:s = "transient";   break;
                case 313:s = "try";         break;
                case 314:s = "true";        break;
                case 315:s = "void";        break;
                case 316:s = "volatile";    break;
                case 317:s = "while";       break;
                case 318:s = "++";          break;
                case 319:s = "--";          break;
                case 320:s = "+=";          break;
                case 321:s = "-=";          break;
                case 322:s = "*=";          break;
                case 323:s = "/=";          break;
                case 324:s = "%=";          break;
                case 325:s = "<<";          break;
                case 326:s = ">>";          break;
                case 327:s = ">>>";         break;
                case 328:s = "&=";          break;
                case 329:s = "|=";          break;
                case 330:s = "^=";          break;
                case 331:s = "<<=";         break;
                case 332:s = ">>=";         break;
                case 333:s = "->";          break;
                case 334:s = ">>>=";        break;
                case 335:s = "";    break;
                default:s = null;   break;
            }
            return s;
        }
    }
}

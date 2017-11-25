package lexer;

import operation.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Lexer {
    public static int line = 1;            //行数
    private static boolean flag;            //文件是否读取结束
    char peek = ' ';
    HashMap id;
    FileReader fileReader = null;

    public Lexer(){
        flag = false;
        id = new HashMap();
    }

    public Lexer(File file)throws IOException{
        this();
        setFile(file);
    }

    //读入下一个字符
    private void readCh() throws IOException{
        int i;
        if(fileReader == null)peek = (char)System.in.read();
        else {
            if((i = fileReader.read())!=-1){        //判断文件是否读取结束
                peek = (char) i;
            }else flag = true;
        }
    }

    //比较下一个字符与输入的字符c是否相同
    private boolean readCh(char c)throws IOException{
        readCh();
        if(peek != c)return false;
        peek = ' ';
        return true;
    }

    //扫描
    public Token scan()throws IOException{
        while (true){
            skipSeparator();                //跳过分隔符
            if(!skipAnnotations())break;    //略过注释
        }
        Token temp;

        if(flag)return null;                //文件读取结束

        //是否是operator
        temp = isOperator();
        if(temp != null){
            return temp;
        }

        //是否数值
        temp = isNumber();
        if(temp != null){
            return temp;
        }

        //是否是字符串
        temp = isString();
        if(temp != null){
            return temp;
        }

        //是否是id或关键字
        temp = isId();
        if (temp != null){
            return temp;
        }

        peek = ' ';
        return null;
    }

    //跳过分隔符 空格 回车 换行 横向制表
    private void skipSeparator()throws IOException{
        for(;;readCh()){
            if(flag)break;
            if (peek == ' ' || peek == '\t' || peek == '\r') continue;
            else if (peek == '\n') line = line + 1;
            else return;
        }
    }

    //略过注释
    private boolean skipAnnotations()throws IOException{
        if(peek == '/') {
            readCh();
            if(peek == '/'){
                while (peek != '\n')readCh();
                peek = ' ';
                line ++;
                return true;
            }
            else if(peek == '*'){
                while (true){
                    readCh();
                    if(peek == '*'){
                        readCh();
                        if(peek == '/'){
                            peek = ' ';
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //是否是整数
    private Token isNumber()throws IOException{
        if( Character.isDigit(peek)){
            int v = 0;
            do{
                v = 10 * v + Character.digit(peek, 10);
                readCh();
            }while (Character.isDigit(peek));
            if( peek != '.')return new Num(v);
            float x = v;
            float d = 10;
            while (true){
                readCh();
                if (! Character.isDigit(peek))break;
                x = x + Character.digit(peek, 10) / d;
                d *= 10;
            }
            return new Real(x);
        }
        return null;
    }

    //是否为变量名或关键字
    private Token isId()throws IOException{
        if(Character.isLetter(peek) || peek == '_' || peek == '$'){
            StringBuffer b = new StringBuffer();
            do{
                b.append(peek);
                readCh();
            } while ( Character.isLetterOrDigit(peek) || peek == '_' || peek == '$');
            String s = b.toString();
            Word w = (Word) ReservedWord.isReservedWord(s);
            if( w != null) return w;
            w = new Identifier(s);
            id.put(s,w);
            return w;
        }
        return null;
    }

    //是否是运算符
    private Token isOperator()throws IOException{
        switch ( peek ){
            case '+':
                if( readCh('+'))return AssignmentOp.inc;
                else if(peek == '=')return AssignmentOp.az;
                else return ArithmeticOp.add;
            case '-':
                if ( readCh('-'))return AssignmentOp.dec;
                else if(peek == '=')return AssignmentOp.sz;
                else return ArithmeticOp.sub;
            case '*':
                if(readCh('='))return AssignmentOp.mz;
                else return ArithmeticOp.mul;
            case '/':
                if(readCh('='))return AssignmentOp.dz;
                else return ArithmeticOp.div;
            case '%':
                if( readCh('='))return AssignmentOp.pz;
                else return ArithmeticOp.rem;
            case '&':
                if ( readCh('&') )return LogicOp.and;
                else if( peek == '=')return AssignmentOp.andZ;
                else return BitOp.and;
            case '|':
                if ( readCh('|'))return LogicOp.or;
                else if(peek == '=')return AssignmentOp.orz;
                else return BitOp.or;
            case '^':
                if(readCh('='))return AssignmentOp.xorZ;
                else return BitOp.xor;
            case '=':
                if ( readCh('='))return RelationOp.eq;
                else return AssignmentOp.equal;
            case '!':
                if ( readCh('='))return RelationOp.ne;
                else return LogicOp.not;
            case '<':
                if ( readCh('='))return RelationOp.le;
                else if(peek == '<')return BitOp.sal;
                else return RelationOp.be;
            case '>':
                if ( readCh('='))return RelationOp.ge;
                else if(peek == '>'){
                    if(readCh('>'))return BitOp.shr;
                    else return BitOp.sar;
                }
                else return RelationOp.ab;
            case '{':
                peek = ' ';
                return Delimiter.curlyBraL;
            case '}':
                peek = ' ';
                return Delimiter.curlyBraR;
            case '[':
                peek = ' ';
                return Delimiter.squareBraL;
            case ']':
                peek = ' ';
                return Delimiter.squareBraR;
            case '(':
                peek = ' ';
                return Delimiter.roundBraL;
            case ')':
                peek = ' ';
                return Delimiter.roundBraR;
            default:
                break;
        }
        return null;
    }

    //是否是String
    private Str isString()throws IOException{
        if(peek == '"'){
            String s = "";
            char bef = '"';
            readCh();
            while (peek != '"'|| bef == '\\'){
                s += peek;
                bef = peek;
                readCh();
            }
            peek = ' ';
            return new Str(s);
        }
        else return null;
    }

    //读取文件
    private void setFile(File file)throws IOException{
        fileReader = new FileReader(file);
    }

    //关闭文件
    public void closeFile()throws IOException{
        fileReader.close();
    }
}

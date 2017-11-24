package lexer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Lexer {
    private static int line = 1;            //行数
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

        if(flag)return null;

        //是否是operator
        temp = isCompoundOperator();
        if(temp != null){
            return temp;
        }

        //是否数值
        temp = isNumber();
        if(temp != null){
            return temp;
        }

        //是否是id或关键字
        temp = isId();
        if (temp != null){
            return temp;
        }

        Token token = new Symbol(peek);
        peek = ' ';
        return token;
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

    //是否是复合词法单元的词素
    private Token isCompoundOperator()throws IOException{
        switch ( peek ){
            case '+':
                if( readCh('+'))return CompoundOperator.inc;
                else if(peek == '=')return CompoundOperator.az;
                else return new Symbol('+');
            case '-':
                if ( readCh('-'))return CompoundOperator.dec;
                else if(peek == '=')return CompoundOperator.sz;
                else return new Symbol('-');
            case '*':
                if(readCh('='))return CompoundOperator.mz;
                else return new Symbol('*');
            case '/':
                if(readCh('='))return CompoundOperator.dz;
                else return new Symbol('/');
            case '%':
                if( readCh('='))return CompoundOperator.pz;
                else return new Symbol('%');
            case '&':
                if ( readCh('&') )return CompoundOperator.and;
                else if( peek == '=')return CompoundOperator.andz;
                else return new Symbol('&');
            case '|':
                if ( readCh('|'))return CompoundOperator.or;
                else if(peek == '=')return CompoundOperator.orz;
                else return new Symbol('|');
            case '^':
                if(readCh('='))return CompoundOperator.xorz;
                else return new Symbol('^');
            case '=':
                if ( readCh('='))return CompoundOperator.eq;
                else return new Symbol('=');
            case '!':
                if ( readCh('='))return CompoundOperator.ne;
                else return new Symbol('!');
            case '<':
                if ( readCh('='))return CompoundOperator.le;
                else if(peek == '<')return CompoundOperator.sal;
                else return new Symbol('<');
            case '>':
                if ( readCh('='))return CompoundOperator.ge;
                else if(peek == '>'){
                    if(readCh('>'))return CompoundOperator.shr;
                    else return CompoundOperator.sar;
                }
                else return new Symbol('>');
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
            while (readCh('"')&&bef != '\\'){
                s += peek;
                bef = peek;
            }
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

package lexer;

import operation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Lexer {

    private static final int FIRST_BUFFER = 0;                          //第一个缓冲区
    private static final int SECOND_BUFFER = 1;                         //第二个缓冲区
    private static final int BUFFER_LENGTH = 1024 * 4;                  //buffer size
    private static final int FIRST_BUFFER_BEGIN = 0;                    //第一个缓冲区起始位置
    private static final int FIRST_BUFFER_END = ( BUFFER_LENGTH >>1 )  - 1; //第一个缓冲区结束位置
    private static final int SECOND_BUFFER_BEGIN = BUFFER_LENGTH >> 1;  //第二个缓冲区起始位置
    private static final int SECOND_BUFFER_END = BUFFER_LENGTH - 1;     //第二个缓冲区结束位置
    private static final int EOF = -1;                                  //end of file

    private static int lexemeBegin = 0;                                 //指针 词素开始位置
    private static int forward = -1;                                     //指针 扫描位置
    public static int line = 1;                                         //行数
    private static boolean flag;                                        //文件是否读取结束
    private static boolean commentsErrorFlag;                           //错误标志 未结束的注释

    private char[] buffer;                                              //缓冲区
    private static BufferedReader bufferedReader = null;
    private HashMap id;

    public Lexer(File file)throws IOException{
        flag = false;
        commentsErrorFlag = false;
        id = new HashMap();
        if(file != null)
            bufferedReader = new BufferedReader(new FileReader(file));
        buffer = new char[BUFFER_LENGTH];
        readBuffered(FIRST_BUFFER);
    }

    //扫描
    public Token scan()throws IOException{
        readCh();
        while (true){
            if(flag){
                bufferedReader.close();
                return null;
            }
            skipSeparator();                //跳过分隔符
            if(!skipAnnotations())break;    //略过注释
        }

        if(commentsErrorFlag){              //未结束的注释
            printError();
            return null;
        }
        if(flag){
            bufferedReader.close();
            return null;                //文件读取结束
        }
        Token temp;
        lexemeBegin = forward;

        //是否是operator
        temp = isOperator();
        if(temp != null){
            generateLexeme();
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

        return null;
    }

    //跳过分隔符 空格 回车 换行 横向制表
    private void skipSeparator()throws IOException{
        do{
            if (cmpCh(' ')|| cmpCh('\t') || cmpCh('\r') || cmpCh('\n')){
                if(!readCh())return;
            }
            else break;
        }while (true);
    }

    //略过注释
    private boolean skipAnnotations()throws IOException{
        if(cmpCh('/')) {
            //Line comment
            if(!readCh())return false;
            if(cmpCh('/')){
                while (!cmpCh('\n'))readCh();
                readCh();
                return true;
            }
            //Block comment
            else if(cmpCh('*')){
                while (true){
                    if(!readCh()){
                        commentsErrorFlag = true;
                        return false;
                    }
                    if(cmpCh('*')){
                        if(!readCh())
                        {
                            commentsErrorFlag = true;
                            return false;
                        }
                        if(cmpCh('/')){
                            readCh();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //是否是实数
    private Token isNumber()throws IOException{
        if( Character.isDigit(buffer[forward])){
            do{
                readCh();
            }while (Character.isDigit(buffer[forward]));
            if(!cmpCh('.')){
                backCh();
                return new Num(Integer.valueOf(generateLexeme()));
            }
            while (true){
                readCh();
                if (! Character.isDigit(buffer[forward]))break;
            }
            backCh();
            return new Real(Float.valueOf(generateLexeme()));
        }
        return null;
    }

    //是否为变量名或关键字
    private Token isId()throws IOException{
        if(Character.isLetter(buffer[forward]) || cmpCh('_') || cmpCh('$')){
            do{
                readCh();
            } while ( Character.isLetterOrDigit(buffer[forward]) || cmpCh('_') || cmpCh('$'));
            backCh();
            String s = generateLexeme();
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
        switch ( buffer[forward] ){
            case '*':
                return isComplexOperation('=', AssignmentOp.mz, ArithmeticOp.mul);
            case '/':
                return isComplexOperation('=', AssignmentOp.dz, ArithmeticOp.div);
            case '%':
                return isComplexOperation('=', AssignmentOp.pz, ArithmeticOp.rem);
            case '^':
                return isComplexOperation('=', AssignmentOp.xorZ, BitOp.xor);
            case '=':
                return isComplexOperation('=', RelationOp.eq, AssignmentOp.equal);
            case '!':
                return isComplexOperation('=', RelationOp.ne, LogicOp.not);
            case '+':
                return isComplexOperation('+','=',ArithmeticOp.inc, AssignmentOp.az, ArithmeticOp.add);
            case '-':
                return isComplexOperation('-','=',ArithmeticOp.dec, AssignmentOp.sz, ArithmeticOp.sub);
            case '&':
                return isComplexOperation('&','=',LogicOp.and, AssignmentOp.andZ, BitOp.and);
            case '|':
                return isComplexOperation('|','=',LogicOp.or, AssignmentOp.orz, BitOp.or);
            case '<':
                return isComplexOperation('=','<',RelationOp.le, BitOp.sal, RelationOp.be);
            case '{':
                return Delimiter.curlyBraL;
            case '}':
                return Delimiter.curlyBraR;
            case '[':
                return Delimiter.squareBraL;
            case ']':
                return Delimiter.squareBraR;
            case '(':
                return Delimiter.roundBraL;
            case ')':
                return Delimiter.roundBraR;
            case '>':
                if ( readCh('='))return RelationOp.ge;
                else if(cmpCh('>')){
                    return isComplexOperation('>', BitOp.shr, BitOp.sar);
                }
                else {
                    backCh();
                    return RelationOp.ab;
                }
            default:
                break;
        }
        return null;
    }

    //是否是String
    private Str isString()throws IOException{
        if(cmpCh('"')){
            readCh();
            while (!cmpCh('"')|| buffer[forward - 1] == '\\'){
                readCh();
            }
            backCh();
            return new Str(generateLexeme());
        }
        else return null;
    }

    //读缓冲区
    private void readBuffered(int bufferNum)throws IOException{
        if(bufferNum == FIRST_BUFFER){
            bufferedReader.read(buffer, FIRST_BUFFER_BEGIN, ( BUFFER_LENGTH >> 1 ) - 1);
            buffer[FIRST_BUFFER_END] = (char)EOF;
        }
        else if(bufferNum == SECOND_BUFFER){
            bufferedReader.read(buffer, SECOND_BUFFER_BEGIN, ( BUFFER_LENGTH >> 1 ) - 1);
            buffer[SECOND_BUFFER_END] = (char)EOF;
        }
    }

    //read next char
    private boolean readCh()throws IOException{
        forward ++;
        if(buffer[forward] == '\n')line ++;
        if(buffer[forward] == EOF ){
            if(forward == FIRST_BUFFER_END){
                readBuffered(SECOND_BUFFER);
                forward = SECOND_BUFFER_BEGIN;
            }
            else if(forward == SECOND_BUFFER_END){
                readBuffered(FIRST_BUFFER);
                forward = FIRST_BUFFER_BEGIN;
            }
            else
            {
                flag = true;
                return false;
            }
        }
        return true;
    }

    //read next char and cmp
    private boolean readCh(char c)throws IOException{
        readCh();
        return cmpCh(c);
    }

    //比较当前字符与输入的参数是否相同
    private boolean cmpCh(char c)throws IOException{
        if(buffer[forward] != c)return false;
        else return true;
    }

    //回退一个字符
    private void backCh(){
        forward = (forward - 1) % BUFFER_LENGTH;
        if(buffer[forward] == EOF)forward --;
    }

    //生成词素
    private String generateLexeme(){
        String lexeme = String.valueOf(buffer, lexemeBegin, forward - lexemeBegin + 1);
        lexemeBegin = forward;
        return lexeme;
    }

    //打印错误
    private void printError(){
        if(commentsErrorFlag){
            System.out.println("Error("+ line +"):未结束的注释");
        }
    }

    //complex operation
    private Token isComplexOperation(char op1, char op2, Token token1, Token token2, Token token3)throws IOException{
            if( readCh(op1))return token1;
            else if(cmpCh(op2))return token2;
            else {
                backCh();
                return token3;
            }
    }
    private Token isComplexOperation(char op, Token token1, Token token2)throws IOException {
        if (readCh(op)) return token1;
        else {
            backCh();
            return token2;
        }
    }
}

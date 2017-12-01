package lexer;

import delegation.EndLexer;
import delegation.PrintError;
import operation.*;

import java.io.*;

public class Lexer {
    private static Lexer instance = null;
    private static PrintError printError;
    private static EndLexer endLexer;

    private static final int FIRST_BUFFER = 0;                          //第一个缓冲区
    private static final int SECOND_BUFFER = 1;                         //第二个缓冲区
    private static final int BUFFER_LENGTH = 1024 * 4;                  //buffer size
    private static final int FIRST_BUFFER_BEGIN = 0;                    //第一个缓冲区起始位置
    private static final int FIRST_BUFFER_END = (BUFFER_LENGTH >>1)- 1; //第一个缓冲区结束位置
    private static final int SECOND_BUFFER_BEGIN = BUFFER_LENGTH >> 1;  //第二个缓冲区起始位置
    private static final int SECOND_BUFFER_END = BUFFER_LENGTH - 1;     //第二个缓冲区结束位置
    private static final int EOF = 0;                                  //end of file

    public  static int line = 1;                                        //行数
    private static int lexemeBegin = 0;                                 //指针 词素开始位置
    private static int forward = -1;                                    //指针 扫描位置
    private static boolean fileEndFlag;                                 //文件是否读取结束
    private static boolean commentsErrorFlag;                           //错误标志 未结束的注释
    private static boolean charErrorFlag;                               //错误标志 未结束的字符文字
    private static boolean stringErrorFlag;                             //错误标志 未结束的字符串文字
    private static boolean hexErrorFlag;                                //错误标志 16进制数字必须包含至少1为16进制数
    private static boolean floatErrorFlag;                              //错误标志 浮点文字的格式错误
    private static boolean octErrorFlag;                                //错误标志 过大的整数

    private static char[] buffer;                                       //缓冲区
    private static BufferedReader bufferedReader = null;

    private Lexer(File file)throws IOException{
        fileEndFlag = false;
        commentsErrorFlag = false;
        charErrorFlag = false;
        stringErrorFlag = false;
        hexErrorFlag = false;

        if(file != null)
            bufferedReader = new BufferedReader(new FileReader(file));
        buffer = new char[BUFFER_LENGTH];
        readBuffered(FIRST_BUFFER);
    }

    public static Lexer getInstance(File file)throws IOException {
        if(instance == null){
            instance = new Lexer(file);
            return instance;
        }
        else return instance;
    }

    //扫描
    public Token scan()throws IOException{
        readCh();
        while (true){
            if(fileEndFlag){
                bufferedReader.close();
                endLexer.EndLexer();
                return null;
            }
            skipSeparator();                                //跳过分隔符
            if(!skipAnnotations())break;                    //略过注释
        }

        if(commentsErrorFlag){                              //未结束的注释
            printError.PrintError(errorMsg("未结束的注释"));
            commentsErrorFlag = false;
            return null;
        }
        if(fileEndFlag){
            bufferedReader.close();
            endLexer.EndLexer();
            return null;                                    //文件读取结束
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
        else{
            if(hexErrorFlag){
                printError.PrintError(errorMsg("16进制数字必须包含至少1为16进制数"));
                ignoreThisSentence();
                hexErrorFlag = false;
                return null;
            }
            else if(floatErrorFlag){
                printError.PrintError(errorMsg("浮点文字的格式错误"));
                ignoreThisSentence();
                floatErrorFlag = false;
                return null;
            }
            else if (octErrorFlag){
                printError.PrintError(errorMsg("过大的整数"));
                ignoreThisSentence();
                octErrorFlag = false;
                return null;
            }
        }

        //是否是字符
        temp = isChar();
        if(temp != null){
            return temp;
        }
        else {
            if (charErrorFlag){
                printError.PrintError(errorMsg("未结束的字符文字"));
                ignoreThisLine();
                charErrorFlag = false;
                return null;
            }
        }

        //是否是字符串
        temp = isString();
        if(temp != null){
            return temp;
        }
        else {
            if(stringErrorFlag){
                printError.PrintError(errorMsg("未结束的字符串文字"));
                stringErrorFlag = false;
                return null;
            }
        }

        //是否是id或关键字
        temp = isId();
        if (temp != null){
            return temp;
        }

        printError.PrintError(errorMsg("非法字符: '" + buffer[forward] + "'"));
        return null;
    }

    //委托 输出错误 文件读取完成
    public void delegate(PrintError printError1, EndLexer endLexer1){
        printError = printError1;
        endLexer = endLexer1;
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
            else backCh();
        }
        return false;
    }

    //是否是实数
    private Token isNumber()throws IOException{
        if( Character.isDigit(buffer[forward])){
            readCh();
            //8进制或10进制
            if(Character.isDigit(buffer[forward])){
                do{
                    readCh();
                }while (Character.isDigit(buffer[forward]));
            }
            //16进制
            else if(buffer[forward] == 'x' || buffer[forward] == 'X'){
                do{
                    readCh();
                }while (isHex(buffer[forward]));
            }
            //是否是小数
            if(!cmpCh('.')){
                //是否包含E
                if(buffer[lexemeBegin] != '0') {
                    Token token = isE();
                    if (token != null) return token;
                }

                backCh();
                if(buffer[forward] == 'x' || buffer[forward] == 'X'){
                    hexErrorFlag = true;
                    return null;
                }
                //是否8进制
                else if(buffer[lexemeBegin] == '0'&& buffer[lexemeBegin + 1] != 'x'&& buffer[lexemeBegin + 1] !='X'){
                    if(!isOct()){
                        octErrorFlag = true;
                        return null;
                    }
                }
                if(buffer[lexemeBegin + 1] == 'x'||buffer[lexemeBegin + 1] == 'X')return new Num(Integer.valueOf(generateLexeme().substring(2),16));
                else return new Num(Integer.valueOf(generateLexeme()));
            }
            if(buffer[lexemeBegin+2]=='.'&&(buffer[lexemeBegin + 1] == 'x'|| buffer[lexemeBegin + 1] == 'X')){
                hexErrorFlag = true;
                return null;
            }
            else if(buffer[lexemeBegin + 1] == 'x'|| buffer[lexemeBegin + 1] == 'X'){
                floatErrorFlag = true;
                return null;
            }
            while (true){
                readCh();
                if (! Character.isDigit(buffer[forward]))break;
            }

            Token token = isE();
            if(token != null)return token;

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
            case '&':
                return isComplexOperation('&','=',LogicOp.and, AssignmentOp.andZ, BitOp.and);
            case '|':
                return isComplexOperation('|','=',LogicOp.or, AssignmentOp.orz, BitOp.or);
            case '.':
                return OtherOp.point;
            case '?':
                return OtherOp.questionM;
            case ':':
                return OtherOp.quotationM;
            case '~':
                return BitOp.not;
            case '{':
                return Delimiter.curlyBraL;
            case '}':
                return Delimiter.curlyBraR;
            case '[':
                return BracketsOp.squareBraL;
            case ']':
                return BracketsOp.squareBraR;
            case '(':
                return BracketsOp.roundBraL;
            case ')':
                return BracketsOp.roundBraR;
            case ';':
                return OtherOp.semicolon;
            case ',':
                return OtherOp.comma;
            case '-':
                if( readCh('-'))return ArithmeticOp.dec;
                else if(cmpCh('='))return AssignmentOp.sz;
                else if(cmpCh('>'))return OtherOp.lambda;
                else {
                    backCh();
                    return ArithmeticOp.sub;
                }
            case '<':
                if (readCh('=')) return RelationOp.le;
                else if (cmpCh('<')) {
                    return isComplexOperation('=', AssignmentOp.salZ, BitOp.sal);
                }
                else {
                    backCh();
                    return RelationOp.be;
                }
            case '>':
                if ( readCh('='))return RelationOp.ge;
                else if(cmpCh('>')){
                    if (readCh('>')) {
                        return isComplexOperation('=', AssignmentOp.shrZ, BitOp.shr);
                    }
                    else if (cmpCh('='))return AssignmentOp.sarZ;
                    else {
                        backCh();
                        return BitOp.sar;
                    }
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
                if(cmpCh('\n')){
                    stringErrorFlag = true;
                    generateLexeme();
                    return null;
                }
            }
            return new Str(generateLexeme());
        }
        else return null;
    }

    //是否是字符
    private Char isChar()throws IOException{
        if(cmpCh('\'')){
            if(readCh('\\')){
                readCh();
                if (readCh('\'')){
                    return new Char(generateLexeme());
                }
                else{
                    charErrorFlag = true;
                    return null;
                }
            }
            else if(readCh('\'')){
                return new Char(generateLexeme());
            }
            else{
                charErrorFlag = true;
                return null;
            }
        }
        else return null;
    }

    //读缓冲区
    private void readBuffered(int bufferNum)throws IOException{
        if(bufferNum == FIRST_BUFFER){
            bufferedReader.read(buffer, FIRST_BUFFER_BEGIN, ( BUFFER_LENGTH >> 1 ) - 1);
            buffer[FIRST_BUFFER_END] = EOF;
        }
        else if(bufferNum == SECOND_BUFFER){
            bufferedReader.read(buffer, SECOND_BUFFER_BEGIN, ( BUFFER_LENGTH >> 1 ) - 1);
            buffer[SECOND_BUFFER_END] = EOF;
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
                fileEndFlag = true;
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

    //complex operation
    private Token isComplexOperation(char op1, char op2, Token token1, Token token2, Token token3)throws IOException {
        if (readCh(op1)) return token1;
        else if (cmpCh(op2)) return token2;
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

    //错误信息
    private String errorMsg(String errorMsg){
        return "Error:("+ line +") java: " + errorMsg;
    }

    //忽略本行
    private void ignoreThisLine() throws IOException{
        while (!readCh('\n'));
        lexemeBegin = forward;
    }

    private void ignoreThisSentence()throws IOException{
        while (!readCh(';'));
        lexemeBegin = forward;
    }

    private boolean isHex(char c){
        if(Character.isDigit(c))return true;
        else if((c >= 'a'&& c<='f')||(c >= 'A'&&c<='F'))return true;
        else return false;
    }

    private boolean isOct(){
        for(int i = lexemeBegin + 1; i<= forward; i++){
            if(!(buffer[i]>= '0' && buffer[i] <= '7'))return false;
        }
        return true;
    }

    private Token isE()throws IOException{
        if(buffer[lexemeBegin + 1]!= 'x'&& buffer[lexemeBegin + 1] != 'X' && (buffer[forward] == 'E' || buffer[forward] == 'e')){
            if(readCh('-')){
                readCh();
                if(Character.isDigit(buffer[forward])){
                    while (true){
                        readCh();
                        if (! Character.isDigit(buffer[forward]))break;
                    }
                    backCh();
                    return new Real(Float.valueOf(generateLexeme()));
                }
                else {
                    backCh();
                    backCh();
                    return null;
                }
            }
            else if(Character.isDigit((buffer[forward]))){
                while (true){
                    readCh();
                    if (! Character.isDigit(buffer[forward]))break;
                }
                backCh();
                return new Real(Float.valueOf(generateLexeme()));
            }
        }
        return null;
    }
}

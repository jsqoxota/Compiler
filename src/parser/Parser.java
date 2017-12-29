package parser;

import delegation.OutputToFile;
import inter.*;
import lexer.*;
import operation.*;
import setsOfItems.*;
import symbol.Env;
import symbol.TempVarS;
import symbol.Type;

import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

public class Parser {
    private static File productionFile;
    private static File tokenFile;
    private static Parser instance;
    private static AnalysisTable analysisTable;
    private static OutputToFile setsOfItemsFile;
    private static OutputToFile analysisTableFile;
    private static OutputToFile analysisProcessFile;
    private static ArrayList<Token> tokens;                 //源程序词法单元
    private static boolean flag;                            //打印表头标志
    private static Env env;                                 //符号表
    private static Grammar grammar;                         //文法
    private static Quadruples quadruples;                   //四元式
    private static TempVarS tempVarS;                       //临时变量表

    private Parser(File productionFile, File tokenFile){
        this.productionFile = productionFile;
        this.tokenFile = tokenFile;
        tokens = new ArrayList<>();
        env = new Env(null);
        Statement.setEnv(env);
        Var.setEnv(env);
        quadruples = Quadruples.getInstance();
        tempVarS = TempVarS.getInstance();
        flag = true;
    }

    public static Parser getInstance(File productionFile, File tokenFile) {
        if(instance == null){
            instance = new Parser(productionFile, tokenFile);
            return instance;
        }
        else return instance;
    }

    //分析
    public void analysis()throws IOException{
        ArrayReference arrayReference = null;
        Token num = null;
        Token id = null;
        Token basic = null;
        boolean flag = false;
        int step = 1;                                   //步骤编号
        int location = 0;                               //第一个符号的位置
        grammar = analysisTable.getGrammar();           //文法
        Production production;                          //产生式
        Stack<Integer> stateStack = new Stack<>();      //状态栈
        Stack<Var> symbolStack = new Stack<>();         //符号栈
        stateStack.push(0);
        while (true){
            int s = stateStack.peek();
            Token token = tokens.get(location);                 //当前输入的词法单元
            Var var = new Var(token, env);
            String string = analysisTable.getAnalysisTable()[s][analysisTable.getNumber(token)];
            /**>>>>>>>>>>>>>>>>>>>>>>>>>符号表新建和删除<<<<<<<<<<<<<<<<<<*/
            if(flag) {
                if ("{".equals(token.toString())) {
                    env = new Env(env);
                    Statement.setEnv(env);
                    Var.setEnv(env);
                    flag = false;
                } else if ("}".equals(token.toString())) {
                    if (env != null) {
                        System.out.println(env.toString());
                        env = env.getPrev();
                    }
                    flag = false;
                    System.out.println(quadruples.toString());
                }
                else if(";".equals(token.toString())){
                    ArrayReference.locIsIdFlag = false;             // is loc -> id [bool]?
                }
            }
            /**>>>>>>>>>>>>>>>>>>>>>>移入，规约，接受<<<<<<<<<<<<<<<<<<<*/
            if(string == null) {
                analysisProcessFile.OutputToFile("Error");
                return;
            }
            else if( string.charAt(0) == 's'){//移入
                printAnalysisProcess(step, stateStack, symbolStack, location, "移入　　　　");
                stateStack.push(Integer.parseInt(string.substring(1, string.length())));
                symbolStack.push(var);
                location++;
                flag = true;
            }
            else if( string.charAt(0) == 'r'){//规约
                production = grammar.getProduction(Integer.parseInt(string.substring(1, string.length())));
                int productionNum = grammar.getProductionNumber(production);
                //arrayReference = assignment(productionNum, arrayReference, id, num);//变量赋值
                String s1;
                if(production.getElements().get(0).equals(SetsOfItems.epsilon))s1 = production.toString();
                else s1 = production.toString()+"　";
                printAnalysisProcess(step, stateStack, symbolStack, location, "根据"+ s1 +"规约");
                ArrayList<Var> vars = new ArrayList<>();
                if (!production.getElements().get(0).equals(SetsOfItems.epsilon)) {
                    for (int i = 0; i < production.getElements().size(); i++)
                        vars.add(symbolStack.pop());
                }
                Var var1 = new Var(production.getNonTerminals(), vars, productionNum);
                symbolStack.push(var1);
                GOTO(production, stateStack);
            }
            else if("acc".equals(string)){//接受
                printAnalysisProcess(step, stateStack, symbolStack, location, "接受　　　　");
                break;
            }
            else{
                analysisProcessFile.OutputToFile("Error");
                break;
            }
            step++;
            /**>>>>>>>>>>>>>>>>记录数据<<<<<<<<<<<<<<<<<<*/
            if("num".equals(token.getTag())){
                num = token;
            }
            else if("id".equals(token.getTag())){
                id = token;
            }
            else if("basic".equals(token.getTag())){
                TypeS.setType((Type) token);
            }
        }
    }

    //s = GOTO[Sm-r, A]
    private void GOTO(Production production, Stack<Integer> stateStack){
        NonTerminals A = production.getNonTerminals();
        int r = production.getElements().size();
        if (!production.getElements().get(0).equals(SetsOfItems.epsilon)) {
            for (int i = 0; i < r; i++)
                stateStack.pop();
        }
        int s = stateStack.peek();
        String string = analysisTable.getAnalysisTable()[s][analysisTable.getNumber(A)];
        if(string != null){
            stateStack.add(Integer.parseInt(string));
        }
    }

    //输出分析过程
    public void printAnalysisProcess(int step, Stack<Integer> stateStack, Stack<Var> symbolStack, int location, String action)throws IOException{
        String s1 = "%-90s";
        if(flag){
            analysisProcessFile.OutputToFile(String.format("%-5s","步骤"));
            analysisProcessFile.OutputToFile(String.format(s1,"栈"));
            analysisProcessFile.OutputToFile(String.format("%-55s","动作"));
            analysisProcessFile.OutputToFile(String.format(s1,"符号"));
            analysisProcessFile.OutputToFile("输入");
            analysisProcessFile.OutputToFile("\r\n");
            flag = false;
        }
        //step
        analysisProcessFile.OutputToFile(String.format("%-7s","("+step+")"));
        //栈
        String string = stateStack.toString().replace("[","")
                .replace(",","")
                .replace("]","");
        analysisProcessFile.OutputToFile(String.format(s1,string));
        //动作
        analysisProcessFile.OutputToFile(String.format("%-50s",action));
        //符号
        string = symbolStack.toString().replace("[","")
                .replace(",","")
                .replace("]","");
        analysisProcessFile.OutputToFile(String.format(s1, string));
        //输入
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = location; i < tokens.size(); i++)
            stringBuilder.append(tokens.get(i).toString());
        analysisProcessFile.OutputToFile(stringBuilder.toString());
        analysisProcessFile.OutputToFile("\r\n");
    }

    //获得分析表
    public void getAnalysisTable()throws IOException{
        analysisTable = new AnalysisTable(productionFile);
        analysisTable.constructorAnalysisTable();
        setsOfItemsFile.OutputToFile(analysisTable.getSetsOfItems().toString());
        analysisTableFile.OutputToFile(analysisTable.toString());
    }

    //获得tokens
    public void getTokens()throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(tokenFile));
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            String[] strings = s.split(" ");
            Token token = getToken(strings[1]);
            if(token != null){//关键字或op
                tokens.add(token);
                continue;
            }
            if("num".equals(strings[3]))//整数
                tokens.add(new Num(Integer.parseInt(strings[1])));
            else if("id".equals(strings[3]))//id
            {
                tokens.add(new Identifier(strings[1]));
            }
            else if("real".equals(strings[3]))//小数
                tokens.add(new Real(Double.parseDouble(strings[1])));
        }
        tokens.add(OtherWord.$);
    }

    //输出
    public void delegate(OutputToFile setsOfItemsFile, OutputToFile analysisTableFile, OutputToFile analysisProcessFile){
        this.setsOfItemsFile = setsOfItemsFile;
        this.analysisTableFile = analysisTableFile;
        this.analysisProcessFile = analysisProcessFile;
    }

    //获得Token
    private Token getToken(String s) {
        Token token;
        token = (Token) ReservedWord.isReservedWord(s);
        if( token != null) return token;
        token = ArithmeticOp.isArithmeticOp(s);
        if( token != null) return token;
        token = AssignmentOp.isAssignmentOp(s);
        if( token != null) return token;
        token = BitOp.isBitOp(s);
        if( token != null) return token;
        token = BracketsOp.isBracketsOp(s);
        if( token != null) return token;
        token = Delimiter.isDelimiter(s);
        if( token != null) return token;
        token = LogicOp.isLogicOp(s);
        if( token != null) return token;
        token = OtherOp.isOtherOp(s);
        if( token != null) return token;
        token = RelationOp.isRelationOp(s);
        if( token != null) return token;
        return null;
    }

//    //变量赋值
//    private ArrayReference assignment(int productionNum, ArrayReference arrayReference, Token id, Token num){
//        switch (productionNum){
//            case 18:{                                       //loc → loc [ bool ]
//                if(arrayReferenceFlag){
//                    arrayReference = new ArrayReference(id,env);
//                    arrayReference.setType();
//                    arrayReference.addAddr(tempVarS);
//                    arrayReference.addQuadruple2(quadruples, tempVarS, Integer.parseInt(num.toString()));
//                    arrayReferenceFlag = false;
//                }
//                else {
//                    arrayReference.setType();
//                    arrayReference.addTempVar(tempVarS);
//                    arrayReference.addAddr(tempVarS);
//                    arrayReference.addQuadruple(quadruples, tempVarS, Integer.parseInt(num.toString()));
//                    arrayReference.addQuadruple(quadruples, tempVarS);
//                }
//            }break;
//            case 19:{                                           //loc → id
//                arrayReferenceFlag = true;
//            }break;
//        }
//        return arrayReference;
//    }
}

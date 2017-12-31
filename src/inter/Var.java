package inter;

import lexer.Identifier;
import lexer.Token;
import setsOfItems.NonTerminals;
import symbol.*;

import java.util.ArrayList;
import java.util.Stack;

public class Var {
    private Quadruples quadruples = Quadruples.getInstance();
    private TempVarS tempVarS = TempVarS.getInstance();
    private static Env env = null;                  //符号表
    private Type type = null;                       //变量类型
    private String addr = null;                     //临时变量名
    private String name = null;                     //变量名
    private Token array = null;                     //数组基地址
    private BackPatchingList trueList = null;       //跳转表
    private BackPatchingList falseList = null;
    private BackPatchingList nextList = null;
    private BackPatchingList lastList = null;       //循环语句末尾
    private int instr;

    //终结符
    public Var(Token token, Env env){
        name = token.toString();
        array = token;
        tokenType(token, env);
    }

    //非终结符
    public Var(NonTerminals nonTerminals, ArrayList<Var> vars, int productionNum){
        name = nonTerminals.toString();
        switch (productionNum){
            case 5:statement5(vars);break;                  //decl → type id ;
            case 7:statement7(vars);break;                  //C → [ num ] C1
            case 8:type = TypeS.getType();break;            //C → ε  C.type = t;
            case 9: stmtBackPatch(vars);break;              //stmts → stmts M stmt
            case 11: assignment(vars);break;                //stmt → loc = bool ;
            case 12: If(vars);break;                        //stmt → if ( bool ) M stmt
            case 13: IfElse(vars);break;                    //stmt → if ( bool ) M stmt N else M stmt
            case 14: While(vars);break;                     //stmt → while M ( bool ) M stmt
            case 15: doWhile(vars);break;                   //stmt → do M stmt while ( bool ) ; M
            case 19: Array(vars);break;                     //loc → loc[bool]
            case 20: getId(vars);break;                     //loc → id
            case 21: or(vars);break;                        //bool → bool || M join
            case 23: and(vars);break;                       //join → join && M equality
            case 25: MIsEmpty(vars);break;                  //M → ε
            case 26: NIsEmpty(vars);break;                  //N → ε
            case 27: relation2(vars, "==");break;       //equality → equality == rel
            case 28: relation2(vars, "!=");break;       //equality → equality != rel
            case 30: relation(vars,"<");break;          //rel → expr < expr
            case 31: relation(vars,"<=");break;         //rel → expr <= expr
            case 32: relation(vars,">=");break;         //rel → expr >= expr
            case 33: relation(vars,">");break;          //rel → expr > expr
            case 35: addOrSub(vars, "+");break;         //expr → expr + term
            case 36: addOrSub(vars, "-");break;         //expr → expr - term
            case 38: mulOrDiv(vars, "*");break;         //term → term * unary
            case 39: mulOrDiv(vars, "/");break;         //term → term / unary
            case 41: monocular(vars, "!");break;        //unary → ! unary
            case 42: monocular(vars, "-");break;        //unary → - unary
            case 44: defaultMethod(vars, 1);break;    //factor → ( bool )
            case 45: arrayAssignment(vars);break;           //factor → loc
            case 48: boolExpressionTrue(vars);break;        //factor → true
            case 49: boolExpressionFalse(vars);break;       //factor → false
            default:defaultMethod(vars, 0); break;
        }
    }

    //break 语句
    //stmt → break ;
    public Var(NonTerminals nonTerminals, Stack<Var> vars, int productionNum){
        name = nonTerminals.toString();
        boolean flag = false;
        Var stmts = null;
        for (int i = vars.size() - 1; i >= 0; i --){
            if("while".equals(vars.get(i).name))flag = true;
            else if(flag && "stmts".equals(vars.get(i).name)){
                stmts = vars.get(i);
                break;
            }
        }
        if(productionNum == 16) {
            lastList = BackPatchingList.makeList(Quadruples.getLocation());
            stmts.lastList = BackPatchingList.merge(stmts.lastList, lastList);
        }
        else if(productionNum == 17){
            nextList = BackPatchingList.makeList(Quadruples.getLocation());
            stmts.nextList = BackPatchingList.merge(stmts.nextList, nextList);
        }
        quadruples.addQuadruple("goto", null, null, null);
    }

    public static void setEnv(final Env e){
        env = e;
    }

    //basic类型
    private void basicType(Token token){
        String temp = token.toString();
        if ("int".equals(temp)){
            type = Type.getInt();
        }
        else if ("float".equals(temp)){
            type = Type.getFloat();
        }
        else if ("double".equals(temp)){
            type = Type.getDouble();
        }
        else if ("long".equals(temp)){
            type = Type.getLong();
        }
        else if ("short".equals(temp)){
            type = Type.getShort();
        }
        else if ("char".equals(temp)){
            type = Type.getChar();
        }
        else if ("byte".equals(temp)){
            type = Type.getByte();
        }
        else if("boolean".equals(temp)){
            type = Type.getBoolean();
        }
    }

    //词法单元类型
    private void tokenType(Token token, Env env){
        String temp = token.getTag();
        if("num".equals(temp)){
            type = Type.getInt();
            addr = token.toString();
            name = "num";
        }
        else if("id".equals(temp)){
            Id id = null;
            if(env != null)id = env.getId(token);
            if(id != null){//已定义
                type = id.getType();
                addr = token.toString();
            }
            name = "id";
        }
        else if("basic".equals(temp)){
            basicType(token);
        }
        else if("real".equals(temp)){
            type = Type.getDouble();
            addr = token.toString();
            name = "real";
        }
        else if("char".equals(temp)){
            type = Type.getChar();
            addr = token.toString();
            name = "char";
        }
        else if ("boolean".equals(temp)){
            type = Type.getBoolean();
            name = "boolean";
            booleanValue(token);
        }
    }

    //boolean
    private void booleanValue(Token token){
        String value = token.toString();
        if("true".equals(value)){
            addr = "true";
        }else if("false".equals(value)){
            addr = "false";
        }
    }

    //loc → id
    private void getId(ArrayList<Var> vars){
        defaultMethod(vars, 0);
        this.array = vars.get(0).array;
        Id id = env.getId(array);
        if(id != null){
            this.type = id.getType();
            this.addr = id.toString();
        }
    }

    //loc → loc[bool]
    private void Array(ArrayList<Var> vars){
        for (Var v: vars){
            if("loc".equals(v.name)){//判断是否是 loc → id[bool]
                if(v.type.toString().equals(env.getId(v.array).type.toString())){
                    arrayReferenceTrue(vars);
                }
                else arrayReferenceFalse(vars);
                break;
            }
        }
    }

    //expr → expr + term or expr → expr - term
    private void addOrSub(ArrayList<Var> vars, String op){
        Var arg1 = null;
        Var arg2 = null;
        for (Var var : vars){
            if("expr".equals(var.name)) {                     //操作数1
                arg1 = var;
            }
            else if("term".equals(var.name)){                 //操作数2
                arg2 = var;
            }
        }
        this.addr = tempVarS.addTempVar();
        this.type = Type.max(arg1.type, arg2.type);
        if(type == null)System.out.println("类型错误！");
        quadruples.addQuadruple(op, arg1.addr, arg2.addr, this.addr);
    }

    //term → term * unary  or  term → term / unary
    private void mulOrDiv(ArrayList<Var> vars, String op){
        Var arg1 = null;
        Var arg2 = null;
        for (Var var : vars){
            if("term".equals(var.name)) {                     //操作数1
                arg1 = var;
            }
            else if("unary".equals(var.name)){                //操作数2
                arg2 = var;
            }
        }
        this.addr = tempVarS.addTempVar();
        this.type = Type.max(arg1.type, arg2.type);
        if(type == null)System.out.println("类型错误！");
        quadruples.addQuadruple(op, arg1.addr, arg2.addr, this.addr);
    }

    //unary → ! unary or  unary → - unary
    private void monocular(ArrayList<Var> vars, String op){
        Var arg = null;
        for (Var var : vars){
            if("unary".equals(var.name)) {                     //操作数1
                arg = var;
            }
        }
        this.addr = tempVarS.addTempVar();
        if("!".equals(op)){
            if (arg.type == Type.getBoolean()){
                quadruples.addQuadruple(op, arg.addr, null, this.addr);
                this.type = arg.type;
                trueList = arg.falseList;
                falseList = arg.trueList;
            }
            else System.out.println("Operator '!' cannot be applied to '" + arg.type + "'");
        }
        else if ("-".equals(op)){
            if (arg.type != Type.getBoolean()) {
                quadruples.addQuadruple(op, arg.addr, null, this.addr);
                this.type = arg.type;
            }
            else System.out.println("Operator '-' cannot be applied to '" + arg.type + "'");
        }
    }

    //C → [ num ] C1
    private void statement7(ArrayList<Var> vars){
        int num = 0;
        Type type = null;
        for (Var var : vars){
            if("num".equals(var.name))                      //数组大小
                num = Integer.parseInt(var.addr);
            else if("C".equals(var.name)){                  //数组类型
                type = var.type;
            }
        }
        this.type = new Array(num, type);                   //C.type = array(num.value, C1.type);
    }

    //decl → type id ;
    private void statement5(ArrayList<Var> vars){
        Token token = vars.get(1).array;
        Type type = vars.get(2).type;
        if(env.getId(token) == null){                           //添加符号表
            Statement.statement(type, (Identifier) token);
            env.getId(token).applySpace();
        }
        else System.out.println("变量已声明");
    }

    //loc -> id [num]
    private void arrayReferenceTrue(ArrayList<Var> vars){
        String addr = null;
        Type type = null;
        for (Var var : vars){
            if("bool".equals(var.name)) {
                addr = var.addr;
            }
            else if ("loc".equals(var.name)){
                type = var.type;
                array = var.array;                      //loc.array = top.get(id.lexeme);   id名
            }
        }
        this.addr = tempVarS.addTempVar();              //loc.addr = new Temp();    申请临时变量
        this.type = ((Array)type).getOf();              //loc.type = loc.array.type.elem
        quadruples.addQuadruple("*", addr, "" + this.type.getWidth(), this.addr);   // gen(loc.addr = bool.addr * loc.type.width;)
    }

    //loc -> loc [num]
    private void arrayReferenceFalse(ArrayList<Var> vars){
        String addrB = null;
        String addrL = null;
        Type type = null;
        for (Var var : vars){
            if("bool".equals(var.name)) {
                addrB = var.addr;
            }
            else if ("loc".equals(var.name)){
                type = var.type;
                addrL = var.addr;
                array = var.array;                      //loc.array = loc1.array;
            }
        }
        this.type = ((Array)type).getOf();              //loc.type = loc.type.elem
        String t = tempVarS.addTempVar();               //t = new Temp();
        this.addr = tempVarS.addTempVar();              //loc.addr = new Temp();    申请临时变量
        quadruples.addQuadruple("*", addrB, "" + this.type.getWidth(), t);                        // gen(t = bool.addr * loc.type.width;)
        quadruples.addQuadruple("+", addrL, t, this.addr);               // gen(loc.addr = loc1.addr + t;)
    }

    //变量和数组赋值
    // stmt → loc = bool ;
    private void assignment(ArrayList<Var> vars){
        nextList = null;
        Var bool = vars.get(1);
        Var loc = vars.get(3);
        array = loc.array;
        Type var = env.getId(array).getType();
        if(!(var instanceof Array)){
            if (Type.conversion(var, bool.type)) {
                quadruples.addQuadruple("=", bool.addr, null, array.toString());
            } else System.out.println("类型不正确 assignment");
        }
        else {
            if (Type.conversion(((Array) var).getBasicType(), bool.type)){
                Id b = env.getId(new Identifier(bool.addr));
                if(b != null) {
                    if( !(b.type instanceof Array))
                        quadruples.addQuadruple("=[]", loc.addr, bool.addr, array.toString());
                    else quadruples.addQuadruple("=", array.toString(), bool.addr, loc.addr);
                }
                else {
                    quadruples.addQuadruple("=[]", loc.addr, bool.addr, array.toString());
                }
            }
        }
    }

    //factor → loc
    private void arrayAssignment(ArrayList<Var> vars){
        Var var = vars.get(0);
        TempVar tempVar = tempVarS.getTempVar(var.getAddr());
        if(tempVar != null) {
            type = var.type;
            addr = tempVarS.addTempVar();
            quadruples.addQuadruple("=", var.array.toString(), var.addr, addr);
        }
        else defaultMethod(vars, 0);
    }

    //factor → true
    private void boolExpressionTrue(ArrayList<Var> vars){
        defaultMethod(vars, 0);
        trueList = BackPatchingList.makeList(Quadruples.getLocation());
        quadruples.addQuadruple("goto", null,null, null);
    }

    //factor → false
    private void boolExpressionFalse(ArrayList<Var> vars){
        defaultMethod(vars, 0);
        falseList = BackPatchingList.makeList(Quadruples.getLocation());
        quadruples.addQuadruple("goto", null, null, null);
    }

    //关系运算符
    // rel → expr < expr
    // rel → expr <= expr
    // rel → expr > expr
    // rel → expr >= expr
    private void relation(ArrayList<Var> vars, String op){
        type = Type.getBoolean();
        Var arg1 = vars.get(2);
        Var arg2 = vars.get(0);

        trueList = BackPatchingList.makeList(Quadruples.getLocation());
        quadruples.addQuadruple("if "+ op, arg1.addr, arg2.addr, null);
        falseList = BackPatchingList.makeList(Quadruples.getLocation());
        quadruples.addQuadruple("goto", null, null, null);
    }

    private void relation2(ArrayList<Var> vars, String op){
        type = Type.getBoolean();
        Var arg1 = null;
        Var arg2 = null;
        for (Var var : vars){
            if ("equality".equals(var.name)) {
                arg1 = var;
            }
            else if("rel".equals(var.name)){
                arg2 = var;
            }
        }
        trueList = BackPatchingList.makeList(Quadruples.getLocation());
        quadruples.addQuadruple("if "+ op, arg1.addr, arg2.addr, null);
        falseList = BackPatchingList.makeList(Quadruples.getLocation());
        quadruples.addQuadruple("goto", null, null, null);
    }

    //and  join → join && M equality
    private void and(ArrayList<Var> vars){
        Var join = vars.get(3);
        Var M = vars.get(1);
        Var equality = vars.get(0);
        BackPatchingList.backPatch(join.trueList, M.instr, quadruples);
        trueList = equality.trueList;
        falseList = BackPatchingList.merge(join.falseList, equality.falseList);
    }

    //bool → bool || M join
    private void or(ArrayList<Var> vars){
        Var bool = vars.get(3);
        Var M = vars.get(1);
        Var join = vars.get(0);
        BackPatchingList.backPatch(bool.falseList, M.instr, quadruples);
        trueList = BackPatchingList.merge(bool.trueList, join.trueList);
        falseList = join.falseList;
    }

    //stmt → if ( bool ) M stmt
    private void If(ArrayList<Var> vars){
        Var stmt = vars.get(0);
        Var M = vars.get(1);
        Var bool = vars.get(3);
        BackPatchingList.backPatch(bool.trueList, M.instr, quadruples);
        nextList = BackPatchingList.merge(bool.falseList, stmt.nextList);
    }

    //stmt → if ( bool ) M1 stmt1 N else M2 stmt2
    private void IfElse(ArrayList<Var> vars){
        Var stmt2 = vars.get(0);
        Var M2 = vars.get(1);
        Var N = vars.get(3);
        Var stmt1 = vars.get(4);
        Var M1 = vars.get(5);
        Var bool = vars.get(7);

        BackPatchingList.backPatch(bool.trueList, M1.instr, quadruples);
        BackPatchingList.backPatch(bool.falseList, M2.instr, quadruples);
        BackPatchingList temp = BackPatchingList.merge(stmt1.nextList, N.nextList);
        nextList = BackPatchingList.merge(temp, stmt2.nextList);
    }

    //stmt → while M ( bool ) M stmt
    private void While(ArrayList<Var> vars){
        Var stmt = vars.get(0);
        Var M2 = vars.get(1);
        Var bool = vars.get(3);
        Var M1 = vars.get(5);

        BackPatchingList.backPatch(stmt.nextList, M1.instr, quadruples);
        BackPatchingList.backPatch(bool.trueList, M2.instr, quadruples);
        nextList = bool.falseList;
        quadruples.addQuadruple("goto", null, null, String.valueOf(M1.instr));
    }

    //stmt → do M1 stmt while ( bool ) ; M2
    private void doWhile(ArrayList<Var> vars){
        Var M1 = vars.get(7);
        Var bool = vars.get(3);
        Var M2 = vars.get(0);
        BackPatchingList.backPatch(bool.trueList, M1.instr, quadruples);
        BackPatchingList.backPatch(bool.falseList, M2.instr, quadruples);
        nextList = bool.falseList;
    }

    //M → ε
    private void MIsEmpty(ArrayList<Var> vars){
        defaultMethod(vars, 0);
        instr = Quadruples.getLocation();
    }

    //N → ε
    private void NIsEmpty(ArrayList<Var> vars){
        defaultMethod(vars, 0);
        nextList = BackPatchingList.makeList(Quadruples.getLocation());
        quadruples.addQuadruple("goto", null, null, null);
    }

    //stmts → stmts M1 stmt M2
    private void stmtBackPatch(ArrayList<Var> vars){
        Var stmts = vars.get(3);
        Var M1 = vars.get(2);
        Var stmt = vars.get(1);
        Var M2 = vars.get(0);
        BackPatchingList.backPatch(stmt.nextList, M1.instr, quadruples);
        BackPatchingList.backPatch(stmts.lastList, M2.instr, quadruples);
        BackPatchingList.backPatch(stmts.nextList, M1.instr, quadruples);
        nextList = stmt.nextList;
    }

    //default
    private void defaultMethod(ArrayList<Var> vars, int num) {
        if(vars != null && vars.size() != 0) {
            Var var = vars.get(num);
            type = var.type;
            addr = var.addr;
            array = var.array;
            falseList = var.falseList;
            trueList = var.trueList;
            nextList = var.nextList;
            instr = var.instr;
            lastList = var.lastList;
        }
    }

    /**>>>>>>>>>>>>>> proc: getter setter override <<<<<<<<<<<<<<<<<*/
    @Override
    public String toString() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getAddr() {
        return addr;
    }

    public String getName() {
        return name;
    }
}

package inter;

import lexer.Identifier;
import lexer.Token;
import setsOfItems.NonTerminals;
import symbol.*;

import java.util.ArrayList;

public class Var {
    private Quadruples quadruples = Quadruples.getInstance();
    private TempVarS tempVarS = TempVarS.getInstance();
    private static Env env = null;                  //符号表
    private Type type = null;                       //变量类型
    private String addr = null;                     //临时变量名
    private String name = null;                     //变量名
    private Token array = null;                     //数组基地址

    //终结符
    public Var(Token token, Env env){
        name = token.toString();
        array = token;
        tokenType(token, env);
    }

    public static void setEnv(final Env e){
        env = e;
    }

    //非终结符
    public Var(NonTerminals nonTerminals, ArrayList<Var> vars, int productionNum){
        name = nonTerminals.toString();
        switch (productionNum){
            case 8:type = TypeS.getType();break;                //C → ε  C.type = t;
            case 7:statement7(vars);break;                      //C → [ num ] C1
            case 5:statement5(vars);break;                      //decl → type id ;
            case 18:{                                           //loc → loc[bool]
                for (Var v: vars){
                    if("loc".equals(v.name)){//判断是否是 loc → id[bool]
                        if(v.type.toString().equals(env.getId(v.array).type.toString())){
                            arrayReferenceTrue(vars);
                        }
                        else arrayReferenceFalse(vars);
                        break;
                    }
                }
            }break;
            case 19:{                                           //loc → id
                this.array = vars.get(0).array;
                Id id = env.getId(array);
                if(id != null){
                    this.type = id.getType();
                    this.addr = id.toString();
                    break;
                }
                defaultMethod(vars);
            }break;
            case 11: assignment(vars);break;                    //stmt → loc = bool ;
            case 44: arrayAssignment(vars);break;               //factor → loc
            case 34: addOrSub(vars, "+");break;                 //expr → expr + term
            case 35: addOrSub(vars, "-");break;                 //expr → expr - term
            case 37: mulOrDiv(vars, "*");break;                 //term → term * unary
            case 38: mulOrDiv(vars, "/");break;                 //term → term / unary
            default:defaultMethod(vars); break;
        }
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
            Id id = env.getId(token);
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
        else if("boolean".equals(temp)){
            type = Type.getBoolean();
            addr = token.toString();
            name = "boolean";
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
            else if("term".equals(var.name)){                  //数组类型
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
            else if("unary".equals(var.name)){                  //数组类型
                arg2 = var;
            }
        }
        this.addr = tempVarS.addTempVar();
        this.type = Type.max(arg1.type, arg2.type);
        if(type == null)System.out.println("类型错误！");
        quadruples.addQuadruple(op, arg1.addr, arg2.addr, this.addr);
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
        Token token = null;
        Type type = null;
        for (Var var : vars){
            if("id".equals(var.name))token = var.array;
            else if ("type".equals(var.name))type = var.type;
        }
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

    //变量和数组赋值 stmt → loc = bool ;
    private void assignment(ArrayList<Var> vars){
        Var bool = null;
        Var var1 = null;
        for (Var var : vars) {
            if ("bool".equals(var.name)) {
                bool = var;
            } else if ("loc".equals(var.name)) {
                array = var.array;
                var1 = var;
            }
        }
        Type var = env.getId(array).type;
        if(!(var instanceof Array)){
            if (Type.conversion(var, bool.type)) {
                quadruples.addQuadruple("=", bool.addr, null, array.toString());
            } else System.out.println("类型不正确");
        }
        else {
            if (Type.conversion(((Array) var).getBasicType(), bool.type)){
                this.addr = tempVarS.addTempVar();
                quadruples.addQuadruple("=", array.toString(), var1.addr, this.addr);
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
        else defaultMethod(vars);
    }

    //default
    private void defaultMethod(ArrayList<Var> vars){
        for(Var var : vars){
            type = var.type;
            if (type != null) {
                addr = var.addr;
                array = var.array;
                break;
            }
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

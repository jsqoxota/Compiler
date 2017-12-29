package inter;

import lexer.Identifier;
import lexer.Token;
import setsOfItems.Grammar;
import setsOfItems.NonTerminals;
import setsOfItems.Production;
import symbol.Array;
import symbol.Env;
import symbol.Type;

import java.util.ArrayList;

public class Var {
    //private Token token;
    private static Env env = null;
    private Token token = null;
    private Type type = null;                      //变量类型
    private String addr = null;                    //临时变量名
    private String value = null;                   //变量值
    private String name = null;                    //变量名

    //终结符
    public Var(Token token, Env env){
        name = token.toString();
        this.token = token;
        tokenType(token, env);
    }

    //非终结符
    public Var(NonTerminals nonTerminals, ArrayList<Var> vars, int productionNum){
        switch (productionNum){
            case 8:{                                            //C → ε  C.type = t;
                type = TypeS.getType();
                name = nonTerminals.toString();
            }break;
            case 7:{                                            //C → [ num ] C1
                int num = 0;
                Type type = null;
                for (Var var : vars){
                    if("num".equals(var.name))                      //数组大小
                        num = Integer.parseInt(var.value);
                    else if("C".equals(var.name))type = var.type;   //数组类型
                }
                this.type = new Array(num, type);           //C.type = array(num.value, C1.type);
                name = nonTerminals.toString();
            }break;
            case 5:{                                        //decl → type id ;
                Token token = null;
                Type type = null;
                for (Var var : vars){
                    if("id".equals(var.name))token = var.token;
                    if ("type".equals(var.name))type = var.type;
                }
                if(env.getId(token) == null){                           //添加符号表
                    Statement.statement(type, (Identifier) token);
                    env.getId(token).applySpace();                      //分配空间
                }
                else System.out.println("变量已声明");
            }break;
            default:{
                name = nonTerminals.toString();
                for(Var var : vars){
                    type = var.type;
                    if (type != null) {
                        value = var.value;
                        addr = var.addr;
                        break;
                    }
                }
            } break;
        }

    }
    public static void setEnv(Env env1){
        env = env1;
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
            value = token.toString();
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
            value = token.toString();
            name = "real";
        }
        else if("char".equals(temp)){
            type = Type.getChar();
            value = token.toString();
            name = "char";
        }
        else if("boolean".equals(temp)){
            type = Type.getBoolean();
            value = token.toString();
            name = "boolean";
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

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Token getToken() {
        return token;
    }
}

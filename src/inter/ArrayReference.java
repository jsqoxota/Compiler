package inter;

import lexer.Token;
import symbol.*;

public class ArrayReference {
    private Token arrayName;
    private int totalWidth;
    private String tempVarName;
    private Env env;
    private Type type;
    private String addr;
    private String lastAddr;
    public ArrayReference(Token arrayName, Env env){
        this.arrayName = arrayName;
        this.env = env;
        type = env.getId(arrayName).getType();
        totalWidth = type.getWidth();
    }

    public void addTempVar(TempVarS tempVarS){
        tempVarName = tempVarS.addTempVar(Type.getInt());
    }

    public void setType() {
        this.type = ((Array) type).getOf();
    }

    public void addAddr(TempVarS tempVarS){
        addr = tempVarS.addTempVar(Type.getInt());
    }

    public void addQuadruple(Quadruples quadruples, TempVarS tempVarS, int num){
        int arg1 = type.getWidth();
        int arg2 = num;
        quadruples.addQuadruple("*", "" + arg1, ""+ arg2, tempVarName);
        tempVarS.getTempVar(tempVarName).setMessage(""+ arg1 * arg2);
    }

    public void addQuadruple(Quadruples quadruples, TempVarS tempVarS){
        int arg1 = Integer.parseInt(tempVarS.getTempVar(tempVarName).getMessage());
        int arg2 = Integer.parseInt(tempVarS.getTempVar(lastAddr).getMessage());
        quadruples.addQuadruple("+", "" + arg1, ""+arg2, addr);
        tempVarS.getTempVar(addr).setMessage(""+ arg1 * arg2);
        lastAddr = addr;
    }

    public void addQuadruple2(Quadruples quadruples, TempVarS tempVarS, int num){
        int arg1 = type.getWidth();
        int arg2 = num;
        quadruples.addQuadruple("*", "" + arg1, ""+ arg2, addr);
        tempVarS.getTempVar(addr).setMessage(""+ arg1 * arg2);
        lastAddr = addr;
    }
}

package inter;

import lexer.Str;
import symbol.TempVarS;

import javax.xml.stream.Location;
import java.util.ArrayList;

public class Quadruples {
    private static Quadruples quadruple;
    private static final int OP = 0;                                        //运算符内部编码
    private static final int ARG1 = 1;                                      //操作数1
    private static final int ARG2 = 2;                                      //操作数2
    private static final int RESULT = 3;                                    //结果
    private ArrayList<String[]> quadruples;                                 //四元式
    private static int location = 0;

    private Quadruples(){
        quadruples = new ArrayList<>();
    }

    public static Quadruples getInstance(){
        if(quadruple == null){
            quadruple = new Quadruples();
            return quadruple;
        }
        else return quadruple;
    }

    public void addQuadruple(String op, String arg1, String arg2, String result){
        String[] strings = new String[4];
        strings[OP] = op;
        strings[ARG1] = arg1;
        strings[ARG2] = arg2;
        strings[RESULT] = result;
        quadruples.add(strings);
        location++;
    }

    public static int getLocation() {
        return location;
    }

    public void setResult(int location, int num){
        quadruples.get(location)[RESULT] = "" + num;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (String[] strings : quadruples){
            stringBuilder.append(String.format("%-3s:",i));
            if("=".equals(strings[OP]))assignment(strings,stringBuilder);
            else if ("=[]".equals(strings[OP]))valueIsArray(strings,stringBuilder);
            else if("goto".equals(strings[OP]))Goto(strings, stringBuilder);
            else if(strings[OP].charAt(0) == 'i')IfGo(strings, stringBuilder);
            else if(strings[ARG2] == null && !("=".equals(strings[OP]))) monocular(strings, stringBuilder);
            else binocular(strings, stringBuilder);
            stringBuilder.append("\n");
            i++;
        }
        return stringBuilder.toString();
    }

    //赋值
    private void assignment(String[] strings, StringBuilder stringBuilder){
        if(strings[ARG2] == null){
            stringBuilder.append(strings[RESULT]);
            stringBuilder.append(" " + strings[OP] + " ");
            stringBuilder.append(strings[ARG1]);
            return;
        }
        else {
            stringBuilder.append(strings[RESULT]);
            stringBuilder.append(" " + strings[OP] + " ");
            stringBuilder.append(strings[ARG1]);
            stringBuilder.append("[" + strings[ARG2] + "]");
        }
    }

    //双目运算符
    private void binocular(String[] strings, StringBuilder stringBuilder){
        stringBuilder.append(strings[RESULT]);
        stringBuilder.append(" = ");
        stringBuilder.append(strings[ARG1] + " ");
        stringBuilder.append(strings[OP] + " ");
        stringBuilder.append(strings[ARG2]);
    }

    //单目运算符
    private void monocular(String[] strings, StringBuilder stringBuilder){
        stringBuilder.append(strings[RESULT]);
        stringBuilder.append(" = ");
        stringBuilder.append(" " + strings[OP] + " ");
        stringBuilder.append(strings[ARG1]);
    }

    //goto
    private void Goto(String[] strings,StringBuilder stringBuilder){
        stringBuilder.append("goto ");
        stringBuilder.append(strings[RESULT]);
    }

    //if goto
    private void IfGo(String[] strings,StringBuilder stringBuilder){
        String[] strings1 = strings[OP].split(" ");
        stringBuilder.append(strings1[0] + " ");
        stringBuilder.append(strings[ARG1]);
        stringBuilder.append(" " + strings1[1] + " ");
        stringBuilder.append(strings[ARG2]);
        stringBuilder.append(" goto ");
        stringBuilder.append(strings[RESULT]);
    }

    //value is array
    private void valueIsArray(String[] strings, StringBuilder stringBuilder){
        stringBuilder.append(strings[RESULT]);
        stringBuilder.append("[" + strings[ARG1] + "]");
        stringBuilder.append(" = ");
        stringBuilder.append(strings[ARG2]);
    }
}

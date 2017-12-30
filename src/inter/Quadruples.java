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
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String[] strings : quadruples){
            if("=".equals(strings[OP]))assignment(strings,stringBuilder);
            else binocular(strings, stringBuilder);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private void assignment(String[] strings, StringBuilder stringBuilder){
        stringBuilder.append(String.format("%-4s",strings[RESULT]));
        stringBuilder.append(" " + strings[OP] + " ");
        if(strings[ARG2] == null){
            stringBuilder.append(strings[ARG1]);
            return;
        }
        else {
            stringBuilder.append(strings[ARG1]);
            stringBuilder.append("[" + strings[ARG2] + "]");
        }
    }

    private void binocular(String[] strings, StringBuilder stringBuilder){
        stringBuilder.append(String.format("%-4s",strings[RESULT]));
        stringBuilder.append(" = ");
        stringBuilder.append(strings[ARG1] + " ");
        stringBuilder.append(strings[OP] + " ");
        stringBuilder.append(strings[ARG2]);
    }
}

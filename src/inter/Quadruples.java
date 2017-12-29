package inter;

import lexer.Str;

import javax.xml.stream.Location;
import java.util.ArrayList;

public class Quadruples {
    private static final int OP = 0;                                        //运算符内部编码
    private static final int ARG1 = 1;                                      //操作数1
    private static final int ARG2 = 2;                                      //操作数2
    private static final int RESULT = 3;                                    //结果
    private static ArrayList<String[]> quadruples;                          //四元式
    private static int location = 0;

    public Quadruples(){
        quadruples = new ArrayList<>();
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
            stringBuilder.append(strings[OP] + " ");
            stringBuilder.append(strings[ARG1] + " ");
            stringBuilder.append(strings[ARG2] + " ");
            stringBuilder.append(strings[RESULT] + " ");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}

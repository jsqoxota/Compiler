package inter;

import java.util.ArrayList;

public class Quadruple {
    private static final int OP = 0;                                        //运算符内部编码
    private static final int ARG1 = 1;                                      //操作数1
    private static final int ARG2 = 2;                                      //操作数2
    private static final int RESULT = 3;                                    //结果
    private static ArrayList<String[]> quadruples;                          //四元式

    public Quadruple(){
        quadruples = new ArrayList<>();
    }
}

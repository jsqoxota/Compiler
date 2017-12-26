package inter;

import symbol.Array;
import symbol.Type;

public class TypeS {
    private static Type type;                               //basic的类型
    private static Type CType;                              //数组或epsilon的类型
    public TypeS(symbol.Type basic){                        //初始化
        type = basic;
    }

    public void getArrayType(int num){                      //数组   C → [ num ] C
        CType = new Array(num, CType);
    }



    /**>>>>>>>>>>>>>> proc: getter setter override <<<<<<<<<<<<<<<<<*/

    public void setCType(){
        CType = type;
    }

    public Type getCType() {
        return CType;
    }
}

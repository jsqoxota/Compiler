package inter;

import symbol.Array;
import symbol.Type;

public class TypeS {
    private static Type type;                               //类型
    public TypeS(symbol.Type basic){                        //非数组  type → basic
        type = basic;
    }

    public void getArrayType(int num){                      //数组   type → type [ num ]
        if(type instanceof Array){
            this.type = new Array(num, (Array)type);        //多维数组
        }
        else {
            this.type = new Array(num, type);               //一维数组
        }
    }

    /**>>>>>>>>>>>>>> proc: getter setter override <<<<<<<<<<<<<<<<<*/
    public Type getType() {
        return type;
    }
}

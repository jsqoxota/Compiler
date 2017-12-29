package symbol;

import inter.Id;

import java.util.HashMap;

public class TempVarS {
    private static TempVarS tempVarS;
    private static int count = 0;
    private HashMap<String, TempVar> hashMap;

    private TempVarS(){
        hashMap = new HashMap<>();
    }

    public static TempVarS getInstance(){
        if(tempVarS == null){
            tempVarS = new TempVarS();
            return tempVarS;
        }
        else return tempVarS;
    }

    public String addTempVar(){
        String name = "t" + count;
        hashMap.put(name, new TempVar(name));
        count++;
        return name;
    }

    public TempVar getTempVar(String s){
        return hashMap.get(s);
    }
}

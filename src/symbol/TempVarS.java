package symbol;

import inter.Id;

import java.util.HashMap;

public class TempVarS {
    public static int count = 0;
    private HashMap<String, TempVar> hashMap;
    public TempVarS(){
        hashMap = new HashMap<>();
    }

    public String addTempVar(Type type){
        String name = "t" + count;
        hashMap.put(name, new TempVar(type, name));
        count++;
        return name;
    }

    public TempVar getTempVar(String s){
        return hashMap.get(s);
    }
}

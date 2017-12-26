package symbol;

import inter.Id;

public class TempVar {
    private Type type;
    private String message;
    private String name;
    public TempVar(Type type, String name){
        this.type = type;
        this.name = name;
    }

    public void setMessage(String s){
        message = s;
    }

    public String getMessage(){
        return message;
    }
}

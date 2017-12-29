package symbol;

import inter.Id;

public class TempVar {
    private String name;
    private String value;
    public TempVar(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

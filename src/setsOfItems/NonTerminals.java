package setsOfItems;

/**
 * 非终结符
 */
public class NonTerminals{
    private String name;

    public NonTerminals(String name){
        this.name = name;
    }


    /**>>>>>>>>>>>>>> proc: getter setter override <<<<<<<<<<<<<<<<<*/
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)return false;

        if(this == obj)return true;

        if(!(obj instanceof NonTerminals))return false;

        if(this.name.equals(((NonTerminals) obj).name))return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

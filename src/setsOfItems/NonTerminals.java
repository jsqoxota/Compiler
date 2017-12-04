package setsOfItems;

/**
 * 非终结符
 */
public class NonTerminals {
    private String name;

    public NonTerminals(String name){
        this.name = name;
    }

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

        if(!(obj instanceof Terminal))return false;

        NonTerminals nonTerminals = (NonTerminals) obj;
        if(this.name == nonTerminals.name)return true;
        else return false;
    }
}

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
}

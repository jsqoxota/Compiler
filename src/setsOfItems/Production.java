package setsOfItems;

import java.util.ArrayList;

/**
 * 产生式
 */
public class Production {
    private NonTerminals nonTerminals;
    private ArrayList<Object> elements;

    public Production(String name){
        nonTerminals = new NonTerminals(name);
        elements = new ArrayList<>();
    }

    //添加产生式体的元素
    public void addElement(Object o){
        elements.add(o);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(nonTerminals.toString());
        s.append(" -> ");
        for (Object object : elements){
            s.append(object.toString());
            s.append(' ');
        }
        return s.toString();
    }

    public NonTerminals getNonTerminals() {
        return nonTerminals;
    }

    public ArrayList<Object> getElements() {
        return elements;
    }
}

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
        String s = "";
        s += nonTerminals.toString();
        s += " -> ";
        for (Object object : elements){
            s += object.toString();
            s += ' ';
        }
        return s;
    }
}

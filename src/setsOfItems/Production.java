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


    /**>>>>>>>>>>>>>> proc: getter setter override <<<<<<<<<<<<<<<<<*/
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(nonTerminals.toString());
        s.append(" -> ");
        for (Object Object : elements){
            s.append(Object.toString());
            s.append(' ');
        }
        return s.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Production))return false;
        if( !nonTerminals.equals(((Production) obj).nonTerminals))return false;

        if (this.elements.size() != ((Production) obj).elements.size()){
            return false;
        }

        for (Object o : this.elements){
            if(!(((Production) obj).elements.contains(o)))return false;
        }

        return true;
    }

    public NonTerminals getNonTerminals() {
        return nonTerminals;
    }

    public ArrayList<Object> getElements() {
        return elements;
    }
}

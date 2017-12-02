package setsOfItems;

import java.util.ArrayList;

/**
 * 项集
 */
public class SetsOfItems {
    private int number;     //编号
    private ArrayList<Production> productions;

    public SetsOfItems(int number){
        this.number = number;
        productions = new ArrayList<>();
    }

    //添加产生式
    public void addElement(Production production){
        productions.add(production);
    }

    @Override
    public String toString() {
        String s = "";
        for (Production production : productions){
            s += production.toString();
            s += "\r\n";
        }
        return s;
    }
}

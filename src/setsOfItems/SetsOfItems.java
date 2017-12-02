package setsOfItems;

import javafx.beans.binding.StringBinding;

import java.util.ArrayList;

/**
 * 项集
 */
public class SetsOfItems {
    private int number;                             //编号
    private ArrayList<Production> productions;      //产生式集合

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
        StringBuilder s = new StringBuilder();
        for (Production production : productions){
            s.append(production.toString());
            s.append("\r\n");
        }
        return s.toString();
    }
}

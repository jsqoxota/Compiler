package setsOfItems;

import javafx.beans.binding.StringBinding;

import java.util.ArrayList;

/**
 * 文法
 */
public class Grammar {
    private ArrayList<Production> productions;      //产生式集合

    public Grammar(){
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

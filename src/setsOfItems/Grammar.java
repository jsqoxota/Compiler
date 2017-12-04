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

    //获得产生式
    public Production getProduction(int index){
        return productions.get(index);
    }

    //获得产生式集合
    public ArrayList<Production> getProduction(NonTerminals nonTerminals){
        ArrayList<Production> productions = new ArrayList<>();
        for (Production production : productions){
            if(production.getNonTerminals() == nonTerminals){
                productions.add(production);
            }
        }
        return productions;
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

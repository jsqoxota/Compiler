package setsOfItems;

import delegation.AddTable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class AnalysisTable {
    private static Grammar grammar;
    private static SetsOfItems setsOfItems;
    private static HashMap<Terminal, Integer> action;
    private static HashMap<NonTerminals, Integer> goTo;
    private static String [][]analysisTable;

    public AnalysisTable(File file)throws IOException{
        setsOfItems = SetsOfItems.getInstance(file);
        grammar = setsOfItems.getGrammar();
        action = new HashMap<>();
        goTo = new HashMap<>();
    }

    //规定编号
    private int getNumber(){
        int count = 0;
        HashSet<Terminal> terminals = setsOfItems.getTerminals();
        HashSet<NonTerminals> nonTerminals = setsOfItems.getNonTerminals();
        for (Terminal terminal : terminals){
            action.put(terminal, count);
            count++;
        }
        for(NonTerminals nonTerminals1 : nonTerminals){
            goTo.put(nonTerminals1, count);
            count++;
        }
        return count;
    }

    //初始化表
    public void initTable(int row, int col){
        analysisTable = new String[row][col];
    }

    //构造分析表
    public void constructorAnalysisTable()throws IOException{
        int row = grammar.getProductionSize();
        setsOfItems.getAugmentedGrammar();                      //获得增广文法
        int col = getNumber();
        initTable(row, col);                                    //初始化构造分析表

        setsOfItems.getAddTableMethod(new AddTableMethod());    //委托构造分析表的方法

        setsOfItems.constructorSetsOfItems();                   //构造项集族
    }

    //实现构造表的方法
    class AddTableMethod implements AddTable{
        //构造分析表 终结符 规约 [A -> alpha *, a]      Action[i, a]设置为 规约A->alpha
        @Override
        public void ACTION(int setOfItemsNumber, HashSet<Terminal> terminals, int productionNumber){
            for (Terminal terminal : terminals)
                analysisTable[setOfItemsNumber][action.get(terminal)] = "r" + productionNumber;
        }

        //构造分析表 终结符 接受 [S' -> S *, $] Action[i, $] 设置为"acc"
        @Override
        public void ACTION(int setOfItemsNumber, Terminal terminal){
            analysisTable[setOfItemsNumber][action.get(terminal)] = "acc";
        }

        //构造分析表 终结符 移入 [A -> alpha * a beta, b]  goTo[Ii, a] = Ij   Action[i, a]设置为移入j
        @Override
        public void ACTION(int setOfItemsNumber, Terminal a, int nextSetOfItemsNumber) {
            analysisTable[setOfItemsNumber][action.get(a)] = "s" + nextSetOfItemsNumber;
        }

        //构造分析表 非终结符 goto
        @Override
        public void GOTO(int setOfItemsNumber, NonTerminals X, int nextSetOfItemsNumber) {
            analysisTable[setOfItemsNumber][goTo.get(X)] = "" + nextSetOfItemsNumber;
        }
    }
}

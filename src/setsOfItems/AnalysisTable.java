package setsOfItems;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class AnalysisTable {
    private static Grammar grammar;                                 //增广文法
    private static SetsOfItems setsOfItems;                         //项集族
    private static HashMap<Terminal, Integer> action;
    private static HashMap<NonTerminals, Integer> goTo;
    private static String [][]analysisTable;                        //分析表
    private static ArrayList<RelevanceInf> relevanceInfs;

    public AnalysisTable(File file)throws IOException{
        setsOfItems = SetsOfItems.getInstance(file);
        action = new HashMap<>();
        goTo = new HashMap<>();
    }

    //规定编号
    private int getNumber(){
        int count = 0;
        HashSet<Terminal> terminals = setsOfItems.getTerminals();
        HashSet<NonTerminals> nonTerminals = setsOfItems.getNonTerminals();
        //terminals.remove(new Terminal("ε"));
        nonTerminals.remove(grammar.getProduction(0).getNonTerminals());
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
        setsOfItems.getAugmentedGrammar();                      //获得增广文法
        grammar = setsOfItems.getGrammar();

        setsOfItems.constructorSetsOfItems();                   //构造项集族
        //System.out.println(setsOfItems);


        int row = setsOfItems.getCount();
        int col = getNumber();
        initTable(row, col);                                    //初始化构造分析表

        System.out.println(action.toString());
        System.out.println(goTo.toString());

        relevanceInfs = setsOfItems.getRelevanceInfs();
        for (int i = 1; i < relevanceInfs.size(); i++){
            RelevanceInf relevanceInf = relevanceInfs.get(i);
            addTable(relevanceInf.getX(), relevanceInf.getLr1Item(), relevanceInf.getFirstSetOfItemNum(), relevanceInf.getSecondSetOfItemNum());
        }
    }

    //添加构造分析表
    public void addTable(Object X, LR1Item lr1Item, int setOfItemsNumber, int nextSetOfItemsNumber){
        if(isTerminal(X) || X == null){
                int productionNumber = grammar.getProductionNumber(lr1Item.getProduction());
                if (X == null) {
                    if (lr1Item.getProduction().equals(grammar.getProduction(0)))
                        ACTION(nextSetOfItemsNumber, new Terminal("$"));   //接受
                        //规约
                    else ACTION(nextSetOfItemsNumber, lr1Item.getExtraInformationS(), productionNumber);
                } else {//移入
                    ACTION(setOfItemsNumber, (Terminal) X, nextSetOfItemsNumber);
                }
        }
        else {//goto
            GOTO(setOfItemsNumber, (NonTerminals)X, nextSetOfItemsNumber);
        }
    }

    //构造分析表 终结符 规约 [A -> alpha *, a]      Action[i, a]设置为 规约A->alpha
    public void ACTION(int setOfItemsNumber, ArrayList<Terminal> terminals, int productionNumber){
        for (Terminal terminal : terminals)
            analysisTable[setOfItemsNumber][action.get(terminal)] = "r" + productionNumber;
    }

    //构造分析表 终结符 接受 [S' -> S *, $] Action[i, $] 设置为"acc"
    public void ACTION(int setOfItemsNumber, Terminal terminal){
        analysisTable[setOfItemsNumber][action.get(terminal)] = "acc";
    }

    //构造分析表 终结符 移入 [A -> alpha * a beta, b]  goTo[Ii, a] = Ij   Action[i, a]设置为移入j
    public void ACTION(int setOfItemsNumber, Terminal a, int nextSetOfItemsNumber) {
        analysisTable[setOfItemsNumber][action.get(a)] = "s" + nextSetOfItemsNumber;
    }

    //构造分析表 非终结符 goto
    public void GOTO(int setOfItemsNumber, NonTerminals X, int nextSetOfItemsNumber) {
        analysisTable[setOfItemsNumber][goTo.get(X)] = "" + nextSetOfItemsNumber;
    }

    //判断是否是终结符
    private boolean isTerminal(Object o){
        if(action.get(o) == null)return false;
        else return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%-13s","状态"));
        for (Terminal terminal: setsOfItems.getTerminals()){
            stringBuilder.append(String.format("%-15s",terminal.toString()));
        }
        for (NonTerminals nonTerminals : setsOfItems.getNonTerminals()){
            stringBuilder.append(String.format("%-15s", nonTerminals.toString()));
        }
        stringBuilder.append("\r\n");
        for (int i = 0; i < analysisTable.length; i++){
            stringBuilder.append(String.format("%-15d",i));
            for (int j = 0; j < analysisTable[i].length; j++){
                stringBuilder.append(String.format("%-15s", analysisTable[i][j]));
            }
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }
}

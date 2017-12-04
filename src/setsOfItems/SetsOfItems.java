package setsOfItems;


import lexer.Token;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 构造项集族
 */
public class SetsOfItems {
    private static SetsOfItems setsOfItems;                             //实例
    private static Grammar grammar;                                     //文法
    private static ArrayList<SetOfItems> setOfItems;                    //项集族
    private static HashSet<Terminal> terminals;                         //终结符集合
    private static int count = 0;                                       //项集数量
    private static BufferedReader bufferedReader;
    private static Terminal epsilon;
    private static Terminal $;

    //构造函数 初始化
    private SetsOfItems(File file) throws IOException {
        setOfItems = new ArrayList<>();
        epsilon  = new Terminal("ε");
        $ = new Terminal("$");
        if (file != null)
            bufferedReader = new BufferedReader(new FileReader(file));
    }

    //单例模式 获取实例
    public static SetsOfItems getInstance(File file) throws IOException {
        if (setsOfItems == null) {
            setsOfItems = new SetsOfItems(file);
            return setsOfItems;
        } else return setsOfItems;
    }

    //构造项集族
    private void constructorSetsOfItems() throws IOException {
        getAugmentedGrammar();                                                                  //获得增广文法
        ArrayList<Terminal> extraInformationS = new ArrayList<>();                              //额外信息
        extraInformationS.add($);
        LR1Item lr1Item = changeToLR1Item(grammar.getProduction(0), extraInformationS);  //获得初始项

        //第0个项集
        closure(lr1Item, count);
        count++;


    }

    //获得增广文法
    private void getAugmentedGrammar() throws IOException {
        grammar = new Grammar();
        terminals = new HashSet<>();
        terminals.add(new Terminal("$"));
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            String[] strings = s.split(" ");                        //分割字符串
            Production production = new Production(strings[0]);         //设置产生式左边
            for (int i = 2; i < strings.length; i++) {                  //设置产生式右边
                Object object;
                if (isTerminal(strings[i]))
                    object = new Terminal(strings[i]);                  //终结符
                else {                                                  //非终结符
                    object = new NonTerminals(strings[i]);
                    terminals.add((Terminal)object);
                }

                production.addElement(object);                          //添加产生式右边元素
            }
            grammar.addElement(production);
        }
    }

    //求闭包
    //B -> gamma    b = FIRST(beta a)   返回项集
    private SetOfItems closure(final LR1Item lr1Item, int number) {
        SetOfItems setOfItems = new SetOfItems(number);
        Object B = lr1Item.getB();                                              //获得B
        ArrayList<Object> beta = lr1Item.getBeta();                             //获得beta
        ArrayList<Object> a = lr1Item.geta();                                   //获得a
        ArrayList<Terminal> extraInformationS = new ArrayList<>(first(beta,a)); //获得b
        setOfItems.addItem(lr1Item);                        //将LR(1)项加入项集
        if(!isTerminal(B)){
            ArrayList<Production> productions = grammar.getProduction((NonTerminals)B);
            for (Production production : productions) {
                setOfItems.addItem(changeToLR1Item(production,extraInformationS));
            }
        }
        return setOfItems;
    }

//    //goto
//    private LR1Item goTo()

    //First(o)
    private HashSet<Terminal> first(final ArrayList<Object> objects){
        HashSet<Terminal> firstSet = new HashSet<>();                       //first集合

        for(Object o : objects){                //终结符或epsilon
            if(isTerminal(o)) {
                if(o.toString() != "ε"){
                    firstSet.add((Terminal)o);
                    return firstSet;
                }
            }
            else{                               //非终结符
                ArrayList<Production> productions = grammar.getProduction((NonTerminals)o);
                for (Production production : productions){
                    HashSet<Terminal> temp = first(production.getElements());
                    firstSet.addAll(temp);
                    if(temp.contains(epsilon)){
                        firstSet.remove(epsilon);
                    }
                    else return firstSet;
                }
            }
        }
        firstSet.add(epsilon);
        return firstSet;
    }

    //First(a,b)
    private HashSet<Terminal> first(final ArrayList<Object> a, final ArrayList<Object> b){
        HashSet<Terminal> firstSet;
        firstSet = first(a);
        if(firstSet.contains(epsilon)){
            firstSet.remove(epsilon);
            firstSet.addAll(first(b));
        }
        return firstSet;
    }

    //转化为LR(1)项
    private LR1Item changeToLR1Item(Production production, ArrayList<Terminal> extraInformationS) {
        LR1Item lr1Item = new LR1Item(production);
        for (Terminal terminal : extraInformationS) {
            lr1Item.addExtraInformationS(terminal);
        }
        return lr1Item;
    }

    //判断是否为终结符
    private boolean isTerminal(String s) {
        if (s.charAt(0) == '<') return true;
        else return false;
    }

    //判断是否是终结符
    private boolean isTerminal(Object o){
        return terminals.contains(o);
    }
}
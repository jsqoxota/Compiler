package setsOfItems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * 构造项集族
 */
public class SetsOfItems {
    private static SetsOfItems setsOfItems;                             //实例
    private static Grammar grammar;                                     //文法
    private static ArrayList<SetOfItems> setOfItemsS;                    //项集族
    private static HashSet<Terminal> terminals;                         //终结符集合
    private static int count = 0;                                       //项集数量
    private static BufferedReader bufferedReader;
    private static Terminal epsilon;
    private static Terminal $;

    //构造函数 初始化
    private SetsOfItems(File file) throws IOException {
        setOfItemsS = new ArrayList<>();
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
    public void constructorSetsOfItems() throws IOException {
        getAugmentedGrammar();                                                                  //获得增广文法
        HashSet<Terminal> extraInformationS = new HashSet<>();                                  //额外信息
        extraInformationS.add($);
        LR1Item lr1Item = changeToLR1Item(grammar.getProduction(0), extraInformationS);  //获得初始项
        //第0个项集
        setOfItemsS.add(closure(lr1Item, count));
        count++;

        for (int i = 0; i < setOfItemsS.size(); i++){
            ArrayList<Object> X = setOfItemsS.get(i).getElementAfterPoint();
            for (Object x : X){     //goTo(I,X);
                setOfItemsS.addAll(goTo(setOfItemsS.get(i),x));
            }
        }
    }

    //获得增广文法
    private void getAugmentedGrammar() throws IOException {
        grammar = new Grammar();
        terminals = new HashSet<>();
        terminals.add($);
        terminals.add(epsilon);
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            String[] strings = s.split(" ");                        //分割字符串
            Production production = new Production(strings[0]);         //设置产生式左边
            for (int i = 2; i < strings.length; i++) {                  //设置产生式右边
                Object Object;
                if (isTerminal(strings[i])){
                    Object = new Terminal(strings[i]);                  //终结符
                    terminals.add((Terminal) Object);
                }
                else                                                    //非终结符
                    Object = new NonTerminals(strings[i]);

                production.addElement(Object);                          //添加产生式右边元素
            }
            grammar.addElement(production);
        }
    }

    //求闭包   [A -> alpha * B beta, a]    LR(1)项:LR1Item  项集编号:number
    //B -> gamma    b = FIRST(beta a)   返回项集
    private SetOfItems closure(final LR1Item lr1Item, int number) {
        SetOfItems setOfItems = new SetOfItems(number);
        setOfItems.addItem(lr1Item);                                                    //第一项
        int count = 0;                                                                  //LR(1)项数量 - 1
        while (true) {
            LR1Item item = setOfItems.getItems(count);
            Object B = item.getB();                                                     //获得B
            if (B == null || isTerminal(B)) break;                                      //终结符或null
            ArrayList<Object> beta = item.getBeta();                                    //获得beta
            ArrayList<Terminal> a = item.geta();                                        //获得a
            HashSet<Terminal> extraInformationS = new HashSet<>();
            for (int i = 0; i < a.size(); i++) {
                extraInformationS.addAll(first(beta, a.get(i)));    //获得b
            }
            ArrayList<Production> productions = grammar.getProduction((NonTerminals) B);
            for (Production production : productions) {
                setOfItems.addItem(changeToLR1Item(production, extraInformationS));
            }

            count++;
        }
        if(setOfItemsS.contains(setOfItems))return null;
        return setOfItems;
    }

    //多个LR(1)项求项集
    private ArrayList<SetOfItems> closure(final ArrayList<LR1Item> lr1Items){
        ArrayList<SetOfItems> setOfItems = new ArrayList<>();
        for (LR1Item lr1Item : lr1Items){
            SetOfItems items = closure(lr1Item,count);
            if(items != null){
                setOfItems.add(items);
                count++;
            }
        }
        return setOfItems;
    }

    //goto  项集:setOfItems   文法符号:X
    private ArrayList<SetOfItems> goTo(final SetOfItems setOfItems, final Object X){
        ArrayList<LR1Item> J = new ArrayList<>();                    //文法符号集合
        for(LR1Item lr1Item : setOfItems.getItems()){
            if(lr1Item.getB().toString().equals(X.toString())){
                LR1Item temp = new LR1Item(lr1Item);
                temp.pointLocationInc();
                J.add(temp);
            }
        }
        return closure(J);
    }

    //First(o)
    private HashSet<Terminal> first(final ArrayList<Object> Objects){
        HashSet<Terminal> firstSet = new HashSet<>();                       //first集合

        for(Object o : Objects){                //终结符或epsilon
            if(isTerminal(o)) {
                firstSet.add((Terminal)o);
            }
            else{                               //非终结符
                ArrayList<Production> productions = grammar.getProduction((NonTerminals)o);
                for (Production production : productions){
                    HashSet<Terminal> temp = first(production.getElements());
                    firstSet.addAll(temp);
                }
            }
            if(!firstSet.contains(epsilon)) return firstSet;            //不包含epsilon则返回
            else firstSet.remove(epsilon);                              //包含则移除epsilon继续
        }
        firstSet.add(epsilon);                                          //当所有项都包含epsilon
        return firstSet;
    }

    //First(a,b)
    private HashSet<Terminal> first(final ArrayList<Object> a, final Terminal b){
        HashSet<Terminal> firstSet;
        ArrayList<Object> objects = new ArrayList<>(a);
        objects.add(b);
        firstSet = first(objects);
        return firstSet;
    }

    //转化为LR(1)项
    private LR1Item changeToLR1Item(Production production, HashSet<Terminal> extraInformationS) {
        LR1Item lr1Item = new LR1Item(production);
        for (Terminal terminal : extraInformationS) {
            lr1Item.addExtraInformationS(terminal);
        }
        return lr1Item;
    }

    //判断是否为终结符
    private boolean isTerminal(String s) {
        if (s.charAt(0) == '<') return false;
        else return true;
    }

    //判断是否是终结符
    private boolean isTerminal(Object o){
        return terminals.contains(o);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for ( SetOfItems setOfItems : setOfItemsS){
            stringBuilder.append(setOfItems.toString());
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }
}
package setsOfItems;

import lexer.ReservedWord;
import operation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 构造项集族
 */
public class SetsOfItems {
    private static SetsOfItems setsOfItems;                             //实例
    private static Grammar grammar;                                     //文法
    private static ArrayList<SetOfItems> setOfItemsS;                   //项集族
    private static HashSet<Terminal> terminals;                         //终结符集合
    private static HashSet<NonTerminals> nonTerminals;                  //非终结符
    private static int count = 0;                                       //项集数量
    private static BufferedReader bufferedReader;
    private static Terminal epsilon;
    private static Terminal $;
    private static int location;                                        //正在处理的项集编号
    private static int nextSetOfItemsNum;                               //下一项项集编号
    private static ArrayList<RelevanceInf> relevanceInfs;               //关联信息
    private static Object object;                                       //X GOTO(I,X)
    private static LR1Item I;                                           //I GOTO(I,X)

    //构造函数 初始化
    private SetsOfItems(File file) throws IOException {
        setOfItemsS = new ArrayList<>();
        epsilon  = new Terminal("ε");
        relevanceInfs = new ArrayList<>();
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
    public void constructorSetsOfItems(){
        HashSet<Terminal> extraInformationS = new HashSet<>();                                  //额外信息
        extraInformationS.add($);
        LR1Item lr1Item = changeToLR1Item(grammar.getProduction(0), extraInformationS);  //获得初始项
        //第0个项集

        setOfItemsS.add(mergeLR1Item(closure(lr1Item, count)));
        count++;
        I = lr1Item;

        for (int i = 0; i < setOfItemsS.size(); i++){
            location = i;
            ArrayList<Object> X = setOfItemsS.get(i).getElementAfterPoint();
            for (Object x : X){     //goTo(I,X)
                SetOfItems setOfItems = goTo(setOfItemsS.get(i),x);
                if(setOfItems != null)setOfItemsS.add(setOfItems);
            }
        }
    }

    //获得增广文法
    public void getAugmentedGrammar() throws IOException {
        grammar = new Grammar();
        terminals = new HashSet<>();
        nonTerminals = new HashSet<>();
        terminals.add($);
        terminals.add(epsilon);
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            String[] strings = s.split(" ");                        //分割字符串
            Production production = new Production(strings[0]);         //设置产生式左边
            for (int i = 2; i < strings.length; i++) {                  //设置产生式右边
                Object object;
                if (isTerminal(strings[i])){
                    object = new Terminal(strings[i]);                  //终结符
                    terminals.add((Terminal) object);
                }
                else {                                                   //非终结符
                    object = new NonTerminals(strings[i]);
                    nonTerminals.add((NonTerminals)object);
                }
                production.addElement(object);                          //添加产生式右边元素
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
            if(count == setOfItems.getItems().size())break;
            LR1Item item = setOfItems.getItems(count);
            Object B = item.getB();                                                     //获得B
            if (B == null || isTerminal(B)) break;                                      //终结符或null
            ArrayList<Object> beta = item.getBeta();                                    //获得beta
            HashSet<Terminal> a = item.geta();                                          //获得a
            HashSet<Terminal> extraInformationS = new HashSet<>();
            for (Terminal terminal: a) {
                extraInformationS.addAll(first(beta, terminal));                        //获得b
            }
            ArrayList<Production> productions = grammar.getProduction((NonTerminals) B);//添加LR(1)项
            for (Production production : productions) {
                LR1Item lr1Item1 = changeToLR1Item(production, extraInformationS);
                if(!setOfItems.getItems().contains(lr1Item1))setOfItems.addItem(lr1Item1);
            }
            count++;
        }
        return setOfItems;
    }

    //多个LR(1)项求项集
    private SetOfItems closure(final ArrayList<LR1Item> lr1Items){
        SetOfItems items = null;
        boolean flag = true;
        for (LR1Item lr1Item : lr1Items){
            I = new LR1Item(lr1Item);
            I.pointLocationDec();
            if(flag){
                items = closure(lr1Item,count);
                if(items != null)flag = false;
            }
            else items.getItems().addAll(closure(lr1Item,count).getItems());
        }
        if(items != null)mergeLR1Item(items);
        for (SetOfItems setOfItems : setOfItemsS){                                               //判断是否已存在
            if(items.equals(setOfItems)){
                nextSetOfItemsNum = setOfItems.getNumber();
                addRelevance(nextSetOfItemsNum, lr1Items);
                return null;
            }
        }
        nextSetOfItemsNum = count;
        count++;
        addRelevance(nextSetOfItemsNum, lr1Items);
        return items;
    }

    //添加关联信息
    private void addRelevance(int setOfItemsNum, ArrayList<LR1Item> lr1Items){
        for (LR1Item lr1Item : lr1Items)
            if(lr1Item.getProduction().getElements().size() == lr1Item.getPointLocation()){
                relevanceInfs.add(new RelevanceInf(0, null, setOfItemsNum, lr1Item));
            }
    }

    //goto  项集:setOfItems   文法符号:X
    private SetOfItems goTo(final SetOfItems setOfItems, final Object X){
        object = X;
        ArrayList<LR1Item> J = new ArrayList<>();                    //文法符号集合
        for(LR1Item lr1Item : setOfItems.getItems()){
            if(lr1Item.getB() != null && lr1Item.getB().toString().equals(X.toString())){
                LR1Item temp = new LR1Item(lr1Item);
                temp.pointLocationInc();
                J.add(temp);
            }
        }
        SetOfItems setOfItems1 = closure(J);
        for (LR1Item lr1Item : J) {
            LR1Item temp = new LR1Item(lr1Item);
            temp.pointLocationDec();
            relevanceInfs.add(new RelevanceInf(setOfItems.getNumber(), X, nextSetOfItemsNum, temp));
        }
        return setOfItems1;
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
                    ArrayList<Object> objectArrayList = production.getElements();
                    if(objectArrayList.size() > 0 && !objectArrayList.get(0).equals(o)){
                        HashSet<Terminal> temp = first(production.getElements());
                        firstSet.addAll(temp);
                    }
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
        if(s.equals("ε"))return true;
        if(ReservedWord.isReservedWord(s)!=null)return true;
        if(ArithmeticOp.isArithmeticOp(s))return true;
        if(AssignmentOp.isAssignmentOp(s))return true;
        if(BitOp.isBitOp(s))return true;
        if(BracketsOp.isBracketsOp(s))return true;
        if(Delimiter.isDelimiter(s))return true;
        if(LogicOp.isLogicOp(s))return true;
        if(OtherOp.isOtherOp(s))return true;
        if(RelationOp.isRelationOp(s))return true;
        else return false;
    }

    //判断是否是终结符
    private boolean isTerminal(Object o){
        return terminals.contains(o);
    }

    //合并同类项
    private SetOfItems mergeLR1Item(SetOfItems setOfItems){
        int num = setOfItems.getItems().size();         //LR(1)项数量
        for (int i = 0; i < num; i++){
            LR1Item lr1Item = setOfItems.getItems(i);
            for (int j = i + 1; j < num; j++){
                LR1Item lr1Item2 = setOfItems.getItems(j);
                if(lr1Item.getProduction().equals(lr1Item2.getProduction()) && lr1Item.getPointLocation() == lr1Item2.getPointLocation()){
                    lr1Item.getExtraInformationS().addAll(lr1Item2.getExtraInformationS());
                    setOfItems.getItems().remove(j);
                    num--;
                    j--;
                }
            }
        }
        return setOfItems;
    }

    /**>>>>>>>>>>>>>> proc: getter setter override <<<<<<<<<<<<<<<<<*/
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for ( SetOfItems setOfItems : setOfItemsS){
            stringBuilder.append(setOfItems.toString());
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public HashSet<Terminal> getTerminals() {
        return terminals;
    }

    public HashSet<NonTerminals> getNonTerminals() {
        return nonTerminals;
    }

    public int getCount() {
        return count;
    }

    public ArrayList<RelevanceInf> getRelevanceInfs() {
        return relevanceInfs;
    }
}
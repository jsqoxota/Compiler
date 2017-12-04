package setsOfItems;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 构造项集
 */
public class ConstructorItemSet {
    private static ConstructorItemSet constructorItemSet;
    private ArrayList<Grammar> collection;            //项集族
    private static BufferedReader bufferedReader;

    //构造函数 初始化
    private ConstructorItemSet(File file)throws IOException{
        collection = new ArrayList<>();
        if(file != null)
            bufferedReader = new BufferedReader(new FileReader(file));
    }

    //单例模式 获取实例
    public static ConstructorItemSet getInstance(File file)throws IOException{
        if(constructorItemSet == null){
            constructorItemSet = new ConstructorItemSet(file);
            return constructorItemSet;
        }
        else return constructorItemSet;
    }

    //获得增广文法
    private Grammar getAugmentedGrammar()throws IOException{
        Grammar grammar = new Grammar();
        String s;
        while ((s = bufferedReader.readLine()) != null){
            String []strings = s.split(" ");                        //分割字符串
            Production production = new Production(strings[0]);         //设置产生式左边
            for (int i = 2; i < strings.length; i++) {                  //设置产生式右边
                Object object;
                if(isTerminals(strings[i]))
                    object = new Terminal(strings[i]);                  //终结符
                else object = new NonTerminals(strings[i]);             //非终结符

                production.addElement(object);                          //添加产生式右边元素
            }
            grammar.addElement(production);
        }
        return grammar;
    }

    //判断是否为终结符
    private boolean isTerminals(String s){
        if(s.charAt(0) == '<')return true;
        else return false;
    }

    //转化化为LR(1)项
    private LR1Item changeToLR1Item(Production production, ArrayList<Object> objects){
        LR1Item lr1Item = new LR1Item(production);
        for (Object o: objects){
            lr1Item.addExtraInformationS(o);
        }
        return lr1Item;
    }

    //求闭包
    private SetOfItems closure(SetOfItems setOfItems){

    }
}
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
    private ArrayList<SetsOfItems> collection;            //项集族
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
    public SetsOfItems getAugmentedGrammar()throws IOException{
        SetsOfItems setsOfItems = new SetsOfItems(0);
        String s;
        while ((s = bufferedReader.readLine()) != null){
            String []strings = s.split(" ");                        //分割字符串
            Production production = new Production(strings[0]);         //设置产生式左边
            for (int i = 2; i < strings.length; i++) {                  //设置产生式右边
                Object object;
                if(strings[i].charAt(0) == '<'){
                    object = new NonTerminals(strings[i]);
                }
                else object = new Terminal(strings[i]);
                production.addElement(object);
            }
            setsOfItems.addElement(production);
        }
        return setsOfItems;
    }
}

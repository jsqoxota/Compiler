package setsOfItems;

import java.util.ArrayList;

/**
 * LR(1)项集
 */

public class SetOfItems {
    private int number;                             //编号
    private ArrayList<LR1Item> items;               //LR(1)项

    public SetOfItems(int number){
        this.number = number;
    }

    //添加产生式
    public void addItem(LR1Item lr1Item){
        items.add(lr1Item);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (LR1Item lr1Item : items){
            s.append(lr1Item.toString());
            s.append("\r\n");
        }
        return s.toString();
    }
}

package setsOfItems;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * LR(1)项集
 */

public class SetOfItems {
    private int number;                             //编号
    private ArrayList<LR1Item> items;               //LR(1)项

    //构造函数 项集编号:number
    public SetOfItems(int number){
        this.number = number;
        items = new ArrayList<>();
    }

    //添加产生式
    public void addItem(LR1Item lr1Item){
        items.add(lr1Item);
    }

    //获得点后面的元素
    public ArrayList<Object> getElementAfterPoint(){
        ArrayList<Object> Objects = new ArrayList<>();
        for (LR1Item lr1Item: items){
            Object o = lr1Item.getElementAfterPoint();
            if(o!= null && !Objects.contains(o)) Objects.add(o);
        }
        return Objects;
    }


    public ArrayList<LR1Item> getLR1Item(Object X){
        ArrayList<LR1Item> lr1Items = new ArrayList<>();
        for (LR1Item lr1Item : items){
            if (lr1Item.getB().equals(X))
            lr1Items.add(lr1Item);
        }
        return lr1Items;
    }

    /**>>>>>>>>>>>>>> proc: getter setter override <<<<<<<<<<<<<<<<<*/
    public int getNumber() {
        return number;
    }

    public ArrayList<LR1Item> getItems() {
        return items;
    }

    public LR1Item getItems(int index){
        return items.get(index);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("I" + number + "\r\n");
        for (LR1Item lr1Item : items){
            s.append(lr1Item.toString());
            s.append("\r\n");
        }
        return s.toString();
    }

    @Override
    public boolean equals(Object obj) { //项集是否相同
        if(!(obj instanceof SetOfItems))return false;

        if (this.items.size() != ((SetOfItems) obj).getItems().size()){
            return false;
        }

        for (LR1Item lr1Item : this.items){
            if(!((SetOfItems) obj).items.contains(lr1Item))return false;
        }

        return true;
    }
}

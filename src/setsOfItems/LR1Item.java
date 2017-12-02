package setsOfItems;

import java.util.ArrayList;

/**
 * LR(1)项
 */
public class LR1Item{
    private Production production;                  //产生式
    private int pointLocation;                      //点的位置
    private ArrayList<Object> extraInfomations;     //额外信息

    //构造函数
    public LR1Item(Production production) {
        this.production = production;
        pointLocation = 0;
        extraInfomations.add("$");
    }

    @Override
    public String toString() {      //输出LR(0)项
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(production.getNonTerminals().toString());
        stringBuilder.append(" ->");
        ArrayList<Object> objects = production.getElements();
        for (int i = 0; i < objects.size(); i++) {
            if (pointLocation == i) stringBuilder.append(" . ");
            stringBuilder.append(objects.toString());
        }
        stringBuilder.append(',');
        for (Object o : extraInfomations){
            stringBuilder.append(o.toString());
        }
        return stringBuilder.toString();
    }
}

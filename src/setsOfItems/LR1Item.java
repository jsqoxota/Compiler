package setsOfItems;

import java.util.ArrayList;

/**
 * LR(1)项
 */
public class LR1Item{
    private Production production;                  //产生式
    private int pointLocation;                      //点的位置
    private ArrayList<Object> extraInformationS;    //额外信息

    //构造函数
    public LR1Item(Production production) {
        this.production = production;
        pointLocation = 0;
        extraInformationS = new ArrayList<>();
    }

    //添加额外信息
    public void addExtraInformationS(Object o){
        extraInformationS.add(o);
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
        for (Object o : extraInformationS){
            stringBuilder.append(o.toString());
        }
        return stringBuilder.toString();
    }

    public int getPointLocation() {
        return pointLocation;
    }

    public ArrayList<Object> getExtraInformationS() {
        return extraInformationS;
    }
}

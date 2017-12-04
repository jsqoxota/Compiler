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

    //点的位置+1
    public void pointLocationInc(){
        pointLocation++;
    }

    //[A -> alpha * B beta, a]
    //获得A项
    public NonTerminals getA(){
        if(production != null){
            return production.getNonTerminals();
        }
        return null;
    }

    //[A -> alpha * B beta, a]
    //获得alpha项
    public ArrayList<Object> getAlpha(){
        if(production != null){
            ArrayList<Object> elements = production.getElements();
            ArrayList<Object> alpha = new ArrayList<>();
            for (int i = 0; i < elements.size() && i < pointLocation; i++ ){
                alpha.add(elements.get(i));
            }
            if(alpha.size() == 0)alpha.add(new Terminal("ε"));
            return alpha;
        }
        return null;
    }

    //[A -> alpha * B beta, a]
    //获得B项
    public Object getB(){
        if(production != null){
            return production.getElements().get(pointLocation);
        }
        return null;
    }

    //[A -> alpha * B beta, a]
    //获得beta项
    public ArrayList<Object> getBeta(){
        if(production != null){
            ArrayList<Object> elements = production.getElements();
            ArrayList<Object> beta = new ArrayList<>();
            for (int i = pointLocation + 1; i < elements.size(); i++ ){
                beta.add(elements.get(i));
            }
            if(beta.size() == 0)beta.add(new Terminal("ε"));
            return beta;
        }
        return null;
    }

    //[A -> alpha * B beta, a]
    //获得a项
    public ArrayList<Object> geta(){
        return extraInformationS;
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

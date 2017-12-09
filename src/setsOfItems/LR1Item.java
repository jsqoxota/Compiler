package setsOfItems;

import java.util.ArrayList;

/**
 * LR(1)项
 */
public class LR1Item{
    private Production production;                      //产生式
    private int pointLocation;                          //点的位置
    private ArrayList<Terminal> extraInformationS;      //额外信息

    //构造函数
    public LR1Item(Production production) {
        this.production = production;
        pointLocation = 0;
        extraInformationS = new ArrayList<>();
    }

    public LR1Item(LR1Item lr1Item){
        this.production = lr1Item.getProduction();
        this.pointLocation = lr1Item.getPointLocation();
        this.extraInformationS = lr1Item.extraInformationS;
    }

    //添加额外信息
    public void addExtraInformationS(Terminal o){
        extraInformationS.add(o);
    }

    //点的位置+1
    public void pointLocationInc(){
        pointLocation++;
    }

    //点的位置-1
    public void pointLocationDec(){pointLocation--;}

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
        if(production != null && pointLocation < production.getElements().size()){
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
    public ArrayList<Terminal> geta(){
        return extraInformationS;
    }

    //获得点后面的元素
    public Object getElementAfterPoint(){
        if(pointLocation == production.getElements().size())return null;
        return production.getElements().get(pointLocation);
    }

    /**>>>>>>>>>>>>>> proc: getter setter override <<<<<<<<<<<<<<<<<*/
    @Override
    public String toString() {      //输出LR(1)项
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(production.getNonTerminals().toString());
        stringBuilder.append(" ->");
        ArrayList<Object> Objects = production.getElements();
        int i;
        for (i = 0; i < Objects.size(); i++) {
            if (pointLocation == i) stringBuilder.append(" . ");
            stringBuilder.append(Objects.get(i).toString());
        }
        if (pointLocation == i) stringBuilder.append(" . ");
        stringBuilder.append(", ");
        boolean flag = true;
        for (Object o : extraInformationS){
            if(flag)flag = false;
            else stringBuilder.append("/");
            stringBuilder.append(o.toString());
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof LR1Item))return false;

        if(this.pointLocation != ((LR1Item) obj).getPointLocation())return false;

        if(!production.equals(((LR1Item) obj).getProduction()))return false;

        if (this.extraInformationS.size() != ((LR1Item) obj).extraInformationS.size()){
            return false;
        }

        for (Terminal terminal: this.extraInformationS){
            if(!(((LR1Item) obj).extraInformationS.contains(terminal)))return false;
        }

        return true;
    }

    public int getPointLocation() {
        return pointLocation;
    }

    public ArrayList<Terminal> getExtraInformationS() {
        return extraInformationS;
    }

    public Production getProduction() {
        return production;
    }
}

package setsOfItems;

public class RelevanceInf {
    private int firstSetOfItemNum;
    private int secondSetOfItemNum;
    private Object X;
    private LR1Item lr1Item;

    public RelevanceInf(int firstSetOfItemNum, Object X, int secondSetOfItemNum, LR1Item lr1Item){
        this.firstSetOfItemNum = firstSetOfItemNum;
        this.secondSetOfItemNum = secondSetOfItemNum;
        this.X = X;
        this.lr1Item = lr1Item;
    }


    /**>>>>>>>>>>>>>> proc: getter setter override <<<<<<<<<<<<<<<<<*/
    public int getFirstSetOfItemNum() {
        return firstSetOfItemNum;
    }

    public int getSecondSetOfItemNum() {
        return secondSetOfItemNum;
    }

    public Object getX() {
        return X;
    }

    public LR1Item getLr1Item() {
        return lr1Item;
    }
}

package inter;


public class Node {
    static int labels = 0;                          //节点标号
    public int newlabel(){
        return ++labels;
    }      //创建新的编号

    public void emitlabel(int i){
        System.out.println("L" + i + ":");
    }

    public void emit(String s){
        System.out.println("\t" + s);
    }
}

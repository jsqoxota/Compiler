package inter;

import java.util.ArrayList;

public class BackPatchingList {
    private ArrayList<Integer> list;
    private BackPatchingList(){
        list = new ArrayList<>();
    }

    public static BackPatchingList makeList(int num){
        BackPatchingList backPatchingList = new BackPatchingList();
        backPatchingList.addElem(num);
        return backPatchingList;
    }

    //添加元素
    private void addElem(int num){
        list.add(num);
    }

    //合并BackPatchingList
    public static BackPatchingList merge(BackPatchingList patchingList1, BackPatchingList patchingList2){
        BackPatchingList backPatchingList = new BackPatchingList();
        if(patchingList1 != null) {
            for (Integer integer : patchingList1.list) {
                backPatchingList.addElem(integer);
            }
        }
        if (patchingList2 != null) {
            for (Integer integer : patchingList2.list) {
                backPatchingList.addElem(integer);
            }
        }
        return backPatchingList;
    }

    //回填
    public static void backPatch(BackPatchingList backPatchingList, int instr, Quadruples quadruples){
        if(backPatchingList == null || backPatchingList.list.size() == 0)return;
        while (backPatchingList.list.size() != 0) {
            quadruples.setResult(backPatchingList.list.get(0), instr);
            backPatchingList.list.remove(0);
        }
    }

}

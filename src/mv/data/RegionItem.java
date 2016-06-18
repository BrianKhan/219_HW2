/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mv.data;

import java.util.ArrayList;

/**
 *
 * @author Brian
 */
public class RegionItem {
    ArrayList<int[]> myList;
    public RegionItem() {
        
    }
    public void add(int x, int y) {
        int[] tempList = new int[2];
        tempList[0] = x;
        tempList[1] = y;
        myList.add(tempList);
    }
}

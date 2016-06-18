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

    ArrayList<Double[]> myList;
    Double[] tempList;

    public RegionItem() {
        myList = new ArrayList<Double[]>();
    }

    public void add(double x, double y) {
        tempList = new Double[2];
        tempList[0] = x;
        tempList[1] = y;
        myList.add(tempList);
    }
    
    public ArrayList<Double[]> get() {
        return myList;
    }
}

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

    ArrayList<double[]> myList;
    double[] tempList;

    public RegionItem() {
        myList = new ArrayList<double[]>();
    }

    public void add(double x, double y) {
        tempList = new double[2];
        tempList[0] = x;
        tempList[1] = y;
        System.out.println(x);
        System.out.println(y);
        myList.add(tempList);
    }
}

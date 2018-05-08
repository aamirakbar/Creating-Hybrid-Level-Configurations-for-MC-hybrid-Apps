package com.company;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by akbara2 on 25/05/17.
 */
public class BSGenerator {

    private int[] mainConfigs;
    private ArrayList<String> configsBase;

    public BSGenerator(int n){
        mainConfigs  = new int[n];
        configsBase = new ArrayList<String>();
        nBits(n);
    }

    private void nBits(int n) {
        if (n <= 0) {
            //System.out.println(Arrays.toString(mainConfigs));
            configsBase.add(Arrays.toString(mainConfigs).replace("[", "").replace("]", "").replace(" ", ""));
        } else {
            mainConfigs[n - 1] = 0;
            nBits(n - 1);
            mainConfigs[n - 1] = 1;
            nBits(n - 1);
        }
    }

    public ArrayList<String> getConfigs(){
        return configsBase;
    }

    public void removeAllSame(){

        ArrayList<String> temp = new ArrayList<String>();

        for(String conf : configsBase){

            String[] arr = conf.split(",");
            String s = arr[0];
            boolean same = true;
            for(String d : arr){
                if(!s.equals(d)){
                    same = false;
                    break;
                }
            }
            if(same){
                temp.add(conf);
            }
        }

        for (String c : temp){
            configsBase.remove(c);
        }
    }
}

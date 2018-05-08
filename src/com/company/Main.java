package com.company;


import java.util.*;

public class Main {

    private static int numOfClasses=0;
    private static ArrayList<Integer> methodsInClasses = new ArrayList<Integer>();

    private static void getUserInput(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of classes: ");
        numOfClasses = input.nextInt();

        for (int i = 1; i <= numOfClasses; i++){
            System.out.println("Class "+ i +" >> Enter number of methods = ");
            int m = input.nextInt();
            methodsInClasses.add(m);
        }
    }

    private static HashMap<Integer, ArrayList<String>> configsMap = new HashMap<Integer, ArrayList<String>>();
    //private static boolean classRunOnCloud = false;
    private static Queue<String> zeroQueue = new LinkedList();

    private static void findSubConfigs(String config){

        final String FINE_GRAINED="1";
        String [] classBits = config.split(",");
        int classCount = 1;

        for(String b : classBits){

            ArrayList<String> configs = new ArrayList<String>();

            if(b.equals(FINE_GRAINED)){
                //Generate methods combination of the class
                int numOfMethods = methodsInClasses.get(classCount-1);
                BSGenerator methodsConfigs = new BSGenerator(numOfMethods);
                configs = methodsConfigs.getConfigs();
            }
            else {
                //if(classRunOnCloud)
                   // configs.add("1");
               // else
                    configs.add(zeroQueue.remove());
            }

            configsMap.put(classCount, configs);
            classCount++;
        }
    }

    public static void main(String[] args) {

        getUserInput();

        BSGenerator classCombinations = new BSGenerator(numOfClasses);
        classCombinations.removeAllSame();
        ArrayList<String> configs = classCombinations.getConfigs();

        for(String c : configs) {

            int zeroCount = c.length() - c.replace("0", "").length();
            ArrayList<String> zeroConfigs = new BSGenerator(zeroCount).getConfigs();

            //System.out.println(c.toString() + ": " + zeroConfigs.size() + ": ");

            for(int i = 0; i < zeroConfigs.size(); i++){

                String s = zeroConfigs.get(i);
                for (String k : s.split(",")){
                    zeroQueue.add(k);
                }

                generateSubConfigs(c);
            }
        }



        //classRunOnCloud = false;
       // generateSubConfigs(generator.getConfigs());
        //classRunOnCloud = true;
        //generateSubConfigs(generator.getConfigs());
        showConfigs();
    }

    private static ArrayList<String> kConfigs = new ArrayList<String>();

    //private static void generateSubConfigs(ArrayList<String> configs){
    private static void generateSubConfigs(String c){

        //for(String c : configs) {

            findSubConfigs(c);

            if(configsMap != null && configsMap.size() >= 2){

                for(String a : configsMap.get(1)){

                    ArrayList<String> temp = new ArrayList<String>();

                    for (int i = 2; i <= configsMap.size(); i++){

                        ArrayList<String> temp2 = new ArrayList<String>();
                        boolean updated = false;

                        for(String b : configsMap.get(i)){

                            if(i==2){
                                String s = c+":"+a + "," + b;
                                temp.add(s);
                            }
                            else{

                                if(temp.size() == 1 && !updated){
                                    String s = temp.get(0);
                                    temp2.add(s);
                                    s = s + "," + b;
                                    temp.set(0, s);
                                    updated = true;
                                }
                                else if(temp.size() > 1 && !updated){

                                    for (int k = 0; k < temp.size(); k++){
                                        String s = temp.get(k);
                                        temp2.add(s);
                                        s = s + "," + b;
                                        temp.set(k, s);
                                    }
                                    updated = true;
                                }
                                else if(updated){

                                    for (int k = 0; k < temp2.size(); k++){
                                        String s = temp2.get(k);
                                        s = s + "," + b;
                                        temp.add(s);
                                    }
                                }
                            }
                        }
                    }
                    kConfigs.addAll(temp);
                }
            }
        //}

    }

    private static void showConfigs(){
        System.out.println("\n\n\n-->Total number of sub-configs: " + kConfigs.size() + "  ********************* \n\n\n");

        System.out.print("configs = {");
        for(int i = 0; i < kConfigs.size(); i++){
            String o = kConfigs.get(i);
            System.out.print("\"" + o + "\"");
            if((i+1) != kConfigs.size()){
                System.out.print(", ");
            }
        }
        System.out.print("}");

        System.out.println("\n\n*************************************************************************");
    }
}

package net.ximias;

import java.util.ArrayList;

/**
 * Created by Alex on 07/02/2017.
 */
public class Register {
    public static final int MAX_CPU_DATA_POINTS =86400;
    private static ArrayList<String> webappNames=new ArrayList<String>();
    private static ArrayList<Byte> cpuData = new ArrayList<Byte>();
    public static void registerWebapp(String name){
        webappNames.add(name);
    }

    public static ArrayList<String> getWebappNames(){
        return webappNames;
    }

    public static void unregisterWebapp(){
        //TODO
    }

    public static void addCpuDataPoint(byte n){
        while (cpuData.size()>86400){
            cpuData.remove(0);
        }
        cpuData.add(n);
    }

    public static Byte[] getCpuData(int size){
        //return new Byte[]{40,40,40,40,40,38,36,34,32,30,30,30,30,30,30,30,30,38,50,80,100,100,100,100,100,100,50,40,30,30,25,20,15,10,8,6,4,3,1,0};
        if (size>cpuData.size()) size=cpuData.size();
        Byte[] ret = new Byte[size];
        for (int i = cpuData.size() - 1; i >= cpuData.size()-size; i--) {
            if (i<0) break;
            ret[cpuData.size()-1-i]=cpuData.get(i);
        }
        return ret;
    }
}

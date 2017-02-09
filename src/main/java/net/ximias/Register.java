package net.ximias;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Alex on 07/02/2017.
 */
public class Register {
    public static final int MAX_CPU_DATA_POINTS =86400;
    private static String adminCredentials;
    private static ArrayList<String> webappNames=new ArrayList<String>();
    private static ArrayList<Byte> cpuData = new ArrayList<Byte>();
    private static BufferedWriter writer;
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

    /**
     * TODO: encrypt written file
     * @param value
     */
    public static void updateAdminCredentials(String value){
        adminCredentials = value;
        try {
            writer = new BufferedWriter(new FileWriter(new File("admin.txt")));
            writer.write(value);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO encrypt written file
     * @return
     */
    public static String getAdminCredentials(){
        if (adminCredentials!=null) return adminCredentials;
        File passwordFile = new File("admin.txt");
        if (passwordFile.exists()){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(passwordFile));
                adminCredentials=reader.readLine();
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (adminCredentials!=null&&adminCredentials.length()>2) return adminCredentials;
        }
        try {
            writer = new BufferedWriter(new FileWriter(passwordFile));
            writer.write("YWRtaW46YWRtaW4=");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "YWRtaW46YWRtaW4=";
    }
}

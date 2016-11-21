package org.xuqi.geophysics.batch;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangxuqi on 5/10/2016.
 */
public class MyFileReader {
    public static List<double[]> readFileData(String path) throws IOException {
        List<double[]> re= new LinkedList<double[]>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String line = null;
            while ((line = br.readLine()) != null) {
                    double[] array = toDoubleArray(line);
                if(array!=null && array.length==3 && array[2] != 1.70141E+038) {
                    re.add(array);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        } finally {
            if(br!=null){
                try{br.close();}catch(IOException e1){
                    e1.printStackTrace();
                }
            }
        }
        return re;
    }
    public static void  main(String[] arg) throws IOException {
        double[] array = toDoubleArray("       14.750        -1.244      23.3700");
    }

    private static double[] toDoubleArray(String line){
        String[] s = line.trim().split("\\s+");
        double[] d = new double[s.length];
        for (int i = 0 ;i<d.length;i++) {
            d[i] = Double.valueOf(s[i]);
        }
        return d;
    }
}

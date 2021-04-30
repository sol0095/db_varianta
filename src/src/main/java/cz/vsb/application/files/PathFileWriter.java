package cz.vsb.application.files;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PathFileWriter {

    private static BufferedWriter bufferedWriter;

    public static synchronized void write(String str){
        try {
            bufferedWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startWriting(){
        try {
            String outputPath = PropertyLoader.loadProperty("outputPath");
            bufferedWriter = new BufferedWriter(new FileWriter(outputPath, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopWriting(){
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



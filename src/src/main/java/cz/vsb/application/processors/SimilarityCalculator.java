package cz.vsb.application.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vsb.application.selects.SelectWithPathNum;
import cz.vsb.application.selects.SelectWithPaths;
import cz.vsb.application.selects.SelectWithSimilarity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class SimilarityCalculator{

    private SelectWithPathNum selectWithPathNum;
    private String line;
    private ArrayList<String> inputPaths;
    private HashSet<String> inputHash = new HashSet<>();
    private HashSet<String> fileHash = new HashSet<>();
    private HashSet<String> totalHash = new HashSet<>();

    public SimilarityCalculator(String line, ArrayList<String> input){
        this.line = line;
        this.inputPaths = input;
    }

    public SelectWithSimilarity calculate() {

        ObjectMapper mapper = new ObjectMapper();   //mapper is for conversion from json to object
        try {
            selectWithPathNum = mapper.readValue(line, SelectWithPathNum.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        prepareHashSets();

        int intersection = 0;

        for(String s : inputHash){
            if(fileHash.contains(s))
                intersection++;
        }

        SelectWithSimilarity selectWithSimilarity = new SelectWithSimilarity(selectWithPathNum.getSelect(), (double)intersection / (double) totalHash.size());

        totalHash = null;
        inputHash = null;
        fileHash = null;
        return selectWithSimilarity;
    }

    private void prepareHashSets(){
        for(String s : inputPaths){
            int i = 0;
            while(inputHash.contains(s+i)){
                i++;
            }
            inputHash.add(s+i);
            totalHash.add(s+i);
        }

        for(String s : selectWithPathNum.getPathsWithNums()){
            int i = 0;
            while(fileHash.contains(s+i)){
                i++;
            }
            fileHash.add(s+i);
            totalHash.add(s+i);
        }
    }
}

package cz.vsb.application.selects;

import java.util.ArrayList;

public class SelectWithPathNum {

    private String select;
    private ArrayList<String> pathsWithNums;

    public SelectWithPathNum(){

    }

    public SelectWithPathNum(String select, ArrayList<String> pathsWithNums) {
        this.select = select;
        this.pathsWithNums = pathsWithNums;
    }

    public String getSelect() {
        return select;
    }

    public ArrayList<String> getPathsWithNums() {
        return pathsWithNums;
    }
}

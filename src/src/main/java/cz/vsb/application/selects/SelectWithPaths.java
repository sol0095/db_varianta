package cz.vsb.application.selects;

import java.util.ArrayList;

public class SelectWithPaths {

    private String select;
    private ArrayList<String> paths;

    public SelectWithPaths(){

    }

    public SelectWithPaths(String select, ArrayList<String> paths) {
        this.select = select;
        this.paths = paths;
    }

    public String getSelect() {
        return select;
    }

    public ArrayList<String> getPaths() {
        return paths;
    }
}

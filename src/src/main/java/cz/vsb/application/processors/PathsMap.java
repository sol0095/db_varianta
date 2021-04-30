package cz.vsb.application.processors;

import java.util.HashMap;

public class PathsMap {
    /**
     * Defines paths with numbers and then it will insert to database.
     * Variable <code>pathNum</code> is only for determining ID for the new path
     */
    public HashMap<String, Integer> pathsWithNums;
    public int pathNum;

    public PathsMap(){
        this.pathsWithNums = new HashMap<>();
        this.pathNum = 1;
    }


}

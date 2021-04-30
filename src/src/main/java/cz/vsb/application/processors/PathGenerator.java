package cz.vsb.application.processors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vsb.application.files.PathFileWriter;
import cz.vsb.application.selects.SelectWithPathNum;
import cz.vsb.application.selects.SelectWithTree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class PathGenerator {
    /**
     * Writes the query and path numbers for each query into a file. On each line.
     * @param selectWithTree
     * @param pathsMap
     */
    private static void writeToFile(SelectWithTree selectWithTree, PathsMap pathsMap) {
        ArrayList<String> pathsInTree = new ArrayList<>();
        XmlTreeView.getLeafPaths(selectWithTree.getElement(), new StringBuilder(), pathsInTree);
        Integer num = null;
        ArrayList<String> pathsWithNums = new ArrayList<>();

        synchronized (pathsMap){
            for(String path : pathsInTree){
                if(pathsMap.pathsWithNums.containsKey(path)){
                    num = pathsMap.pathsWithNums.get(path);

                }
                else{
                    pathsMap.pathsWithNums.put(path, pathsMap.pathNum);
                    num = pathsMap.pathNum;
                    pathsMap.pathNum++;
                }
                pathsWithNums.add(num.toString());
            }
        }


        ObjectMapper mapper = new ObjectMapper();

        SelectWithPathNum selectWithPathNum = new SelectWithPathNum(selectWithTree.getQuery(), pathsWithNums);

        try {
            PathFileWriter.write( mapper.writeValueAsString(selectWithPathNum) + "\n");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the paths from defined XML file.
     * @param line
     * @param pathsMap
     */
    public static void generate(String line, PathsMap pathsMap){
        if(line.contains("<sqlSelect>")){
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document document = dBuilder.parse(new InputSource(new StringReader(line)));
                NodeList nodeList = document.getElementsByTagName("sqlStatement");
                String selectCode = document.getElementsByTagName("selectCode").item(0).getFirstChild().getNodeValue();

                for(int i = 0; i < nodeList.getLength(); i++)
                    writeToFile(new SelectWithTree((Element)nodeList.item(i), selectCode), pathsMap);

            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

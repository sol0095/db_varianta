package cz.vsb.application;

import cz.vsb.application.files.InputFileReader;
import cz.vsb.application.files.PathFileWriter;
import cz.vsb.application.files.PropertyLoader;
import cz.vsb.application.processors.*;
import cz.vsb.application.selects.SelectComparator;
import cz.vsb.application.selects.SelectWithSimilarity;
import cz.vsb.database.Database;
import cz.vsb.database.Path;
import cz.vsb.database.PathDAO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Stream;

public class Application {

    private static ArrayList<String> inputPathsNums = new ArrayList<>();

    public static void runApplication(){
        Database.setConnectionString();

        if(Boolean.parseBoolean(PropertyLoader.loadProperty("generatePathsFile")))
            writePathsTofile();

        calculateSimilarity();
        //clearPathTable();
    }

    private static void writePathsTofile(){
        PathsMap pathsMap = new PathsMap();
        PathFileWriter.startWriting();

        String inputXml = PropertyLoader.loadProperty("inputXML");
        Stream<String> lines = InputFileReader.readFile(inputXml);
        lines.forEach(e ->{
            PathGenerator.generate(e, pathsMap);
        });

        PathFileWriter.stopWriting();
        PathDAO.insert(pathsMap.pathsWithNums);
    }

    private static void calculateSimilarity(){
        String query = PropertyLoader.loadProperty("inputQuery");
        InputPreparator.prepareInput(query, 0);

        Vector<SelectWithSimilarity> resultList = new Vector<>();

        String outputPath = PropertyLoader.loadProperty("outputPath");
        Stream<String> lines = InputFileReader.readFile(outputPath);
        lines.forEach(e ->{
            SelectWithSimilarity selectWithSimilarity = new SimilarityCalculator(e, InputPreparator.getInputPaths()).calculate();
            resultList.add(selectWithSimilarity);
        });

        //System.out.println("res " + resultList.size());

        Collections.sort(resultList, new SelectComparator());

        for(int i = 0; i < 10; i++){
            System.out.println(resultList.get(i).getQuery());
            System.out.println(resultList.get(i).getSimilarity());
        }
    }

    private static void clearPathTable(){
        PathDAO.clearTable();
    }
}

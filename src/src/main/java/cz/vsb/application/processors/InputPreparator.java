package cz.vsb.application.processors;

import cz.vsb.database.Path;
import cz.vsb.database.PathDAO;
import cz.vsb.grammars.*;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import static org.antlr.v4.runtime.CharStreams.fromString;

public class InputPreparator {

    private static ArrayList<String> inputPathsNum = new ArrayList<>();

    /**
     * Prepares input for defined query and grammar. Gets the derivative tree for query and gets the query paths.
     * @param query
     * @param grammar
     */
    public static void prepareInput(String query, int grammar){
        ParseTree parseTree = null;
        Parser parser = null;
        query = query.toUpperCase();

        if(grammar == 0){
            MySqlLexer lexer = new MySqlLexer(fromString(query));
            parser = new MySqlParser(new CommonTokenStream(lexer));
            parseTree = ((MySqlParser)parser).root();
        }
        else if(grammar == 1){
            SQLiteLexer lexer = new SQLiteLexer(fromString(query));
            parser = new SQLiteParser(new CommonTokenStream(lexer));
            parseTree = ((SQLiteParser)parser).parse();
        }
        else if(grammar == 2){
            TSqlLexer lexer = new TSqlLexer(fromString(query));
            parser = new TSqlParser(new CommonTokenStream(lexer));
            parseTree = ((TSqlParser)parser).tsql_file();
        }
        else if(grammar == 3){
            PlSqlLexer lexer = new PlSqlLexer(fromString(query));
            parser = new PlSqlParser(new CommonTokenStream(lexer));
            parseTree = ((PlSqlParser)parser).sql_script();
        }

        if(parseTree != null && parser.getNumberOfSyntaxErrors() == 0){
            ResultPreparator resultPreparator = new ResultPreparator();
            resultPreparator.prepareData(query, parseTree, parser);
            prepareInputPaths(resultPreparator.getXmlData());
        }
    }

    private static void removeErrorListeners(Lexer lexer, Parser parser){
        lexer.removeErrorListener(ConsoleErrorListener.INSTANCE);
        parser.removeErrorListener(ConsoleErrorListener.INSTANCE);
    }

    /**
     * Makes paths for input derivation tree. Adds them into a variable list <code>inputPaths</code>.
     * Gets a unique number for each path and adds them into variable list <code>inputPathsNum</code>.
     * @param xmlTree
     */
    private static void prepareInputPaths(String xmlTree){
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(new InputSource(new StringReader(xmlTree)));
            ArrayList<String> inputPaths = new ArrayList<>();
            XmlTreeView.getLeafPaths((Element)(document.getElementsByTagName("sqlStatement").item(0)), new StringBuilder(), inputPaths);

            Integer num;
            Integer maxNum = PathDAO.selectMaxID()+1;

            for(String strPath : inputPaths){
                Path path = PathDAO.select(strPath);
                if(path != null){
                    num = path.getPathID();
                }
                else{
                    num = maxNum;
                    maxNum++;
                }

                inputPathsNum.add(num.toString());
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getInputPaths(){
        return inputPathsNum;
    }
}

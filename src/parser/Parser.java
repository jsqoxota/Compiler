package parser;

import delegation.OutputToFile;

import java.io.File;
import java.io.IOException;

public class Parser {
    private static File inputFile;
    private static Parser instance;
    private static AnalysisTable analysisTable;
    private static OutputToFile setsOfItemsFile;
    private static OutputToFile analysisTableFile;

    private Parser(File inputFile){
        this.inputFile = inputFile;
    }

    public static Parser getInstance(File file) {
        if(instance == null){
            instance = new Parser(file);
            return instance;
        }
        else return instance;
    }

    public void getAnalysisTable()throws IOException{
        analysisTable = new AnalysisTable(inputFile);
        analysisTable.constructorAnalysisTable();
        setsOfItemsFile.OutputToFile(analysisTable.getSetsOfItems().toString());
        analysisTableFile.OutputToFile(analysisTable.toString());
    }

    public void delegate(OutputToFile setsOfItemsFile, OutputToFile analysisTableFile){
        this.setsOfItemsFile = setsOfItemsFile;
        this.analysisTableFile = analysisTableFile;
    }
}

package parser;

import delegation.OutputToFile;

import java.io.File;
import java.io.IOException;

public class Parser {
    private static File productionFile;
    private static File tokenFile;
    private static Parser instance;
    private static AnalysisTable analysisTable;
    private static OutputToFile setsOfItemsFile;
    private static OutputToFile analysisTableFile;
    private static OutputToFile analysisProcessFile;

    private Parser(File productionFile, File tokenFile){
        this.productionFile = productionFile;
        this.tokenFile = tokenFile;
    }

    public static Parser getInstance(File productionFile, File tokenFile) {
        if(instance == null){
            instance = new Parser(productionFile, tokenFile);
            return instance;
        }
        else return instance;
    }

    public void getAnalysisTable()throws IOException{
        analysisTable = new AnalysisTable(productionFile);
        analysisTable.constructorAnalysisTable();
        setsOfItemsFile.OutputToFile(analysisTable.getSetsOfItems().toString());
        analysisTableFile.OutputToFile(analysisTable.toString());
    }

    public void delegate(OutputToFile setsOfItemsFile, OutputToFile analysisTableFile, OutputToFile analysisProcessFile){
        this.setsOfItemsFile = setsOfItemsFile;
        this.analysisTableFile = analysisTableFile;
        this.analysisProcessFile = analysisProcessFile;
    }
}

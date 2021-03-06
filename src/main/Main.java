package main;

import lexer.Lexer;
import lexer.Token;
import parser.Parser;

import java.io.*;

public class Main {
    private static final String selectPathname = "7";
    private static final String pathnameSourceCode = "./src/test/SourceCode" + selectPathname +".txt";
    private static final String pathnameToken = "./src/test/Token" + selectPathname +".txt";
    private static final String pathnameProduction = "./src/test/Production" + selectPathname + ".txt";
    private static final String pathnameSetsOfItems = "./src/test/SetsOfItems" + selectPathname +".txt";
    private static final String pathnameAnalysisTable = "./src/test/AnalysisTable" + selectPathname + ".txt";
    private static final String pathnameAnalysisProcess = "./src/test/AnalysisProcess" + selectPathname +".txt";
    private static final String pathnameQuadruples = "./src/test/Quadruples" + selectPathname + ".txt";
    private static final String pathnameIntermediateCode = "./src/test/IntermediateCode" + selectPathname + ".txt";
    private static boolean flag = false;
    public static void main(String[] args)throws IOException {
        File sourceCodeFile = new File(pathnameSourceCode);
        File tokenFile = new File(pathnameToken);
        File productionFile = new File(pathnameProduction);
        File setsOfItemsFile = new File(pathnameSetsOfItems);
        File analysisTableFile = new File(pathnameAnalysisTable);
        File analysisProcessFile = new File(pathnameAnalysisProcess);
        File QuadruplesFile = new File(pathnameQuadruples);
        File IntermediateCodeFile = new File(pathnameIntermediateCode);

        //检查文件
        checkInputFile(sourceCodeFile);
        checkInputFile(productionFile);
        checkOutputFile(tokenFile);
        checkOutputFile(setsOfItemsFile);
        checkOutputFile(analysisTableFile);
        checkOutputFile(analysisProcessFile);
        checkOutputFile(QuadruplesFile);
        checkOutputFile(IntermediateCodeFile);
        if(flag)return;

        /******************词法分析************************/
        Lexer lexer = Lexer.getInstance(sourceCodeFile);
        //输出Token和错误到文件
        BufferedWriter bufferedWriter1 = new BufferedWriter(new FileWriter(tokenFile));
        lexer.delegate((msg)->{ bufferedWriter1.write(msg);bufferedWriter1.newLine();},()-> flag = true);
        Token token;
        while (true) {
            if ((token = lexer.scan()) != null) {
                String temp = token.getToken();
                bufferedWriter1.write(temp);
                bufferedWriter1.newLine();
            }
            if(flag)break;
        }
        bufferedWriter1.close();

        /******************语法分析 and 中间代码生成************************/
        Parser parser = Parser.getInstance(productionFile, tokenFile);
        //输出项集族和分析表到文件
        BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(analysisProcessFile));
        parser.delegate(
                msg ->{ BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(setsOfItemsFile));
                bufferedWriter.write(msg); bufferedWriter.close();},
                msg -> { BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(analysisTableFile));
                bufferedWriter.write(msg);bufferedWriter.close();},
                msg -> { bufferedWriter2.write(msg);},
                msg -> { BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(QuadruplesFile));
                bufferedWriter.write(msg);bufferedWriter.close();},
                msg -> { BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(IntermediateCodeFile));
                    bufferedWriter.write(msg);bufferedWriter.close();}
                );
        //获得分析表
        parser.getAnalysisTable();
        //获取词法单元
        parser.getTokens();
        //开始分析
        parser.analysis();
        bufferedWriter2.close();
    }

    //检测输出文件是否存在
    private static void checkOutputFile(File outputFile)throws IOException{
        if(outputFile.exists()){
            outputFile.delete();
            outputFile.createNewFile();
        }
        else outputFile.createNewFile();
    }

    //检查输入文件是否存在
    private static void checkInputFile(File inputFile){
        if(!inputFile.exists()){
            System.out.println("无法找到指定文件!\n");
            flag = true;
        }
    }
}

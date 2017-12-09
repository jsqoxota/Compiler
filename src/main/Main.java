package main;

import parser.Parser;

import java.io.*;

public class Main {
    private static final String pathnameInput = "./src/test/test3";
    private static final String pathnameOutput = "./src/test/result3.txt";
    private static final String pathnameProduction = "./src/test/Production4.txt";
    private static final String pathnameSetsOfItems = "./src/test/SetsOfItems4.txt";
    private static final String pathnameAnalysisTable = "./src/test/AnalysisTable4.txt";
    private static boolean flag = false;
    public static void main(String[] args)throws IOException {
        File productionFile = new File(pathnameProduction);
        File setsOfItemsFile = new File(pathnameSetsOfItems);
        File analysisTableFile = new File(pathnameAnalysisTable);
        Parser parser = Parser.getInstance(productionFile);

        //检查文件
        checkInputFile(productionFile);
        checkOutputFile(setsOfItemsFile);
        checkOutputFile(analysisTableFile);
        if(flag)return;

        //输出到文件
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(setsOfItemsFile));
        BufferedWriter bufferedWriter1 = new BufferedWriter(new FileWriter(analysisTableFile));
        parser.delegate(msg ->{ bufferedWriter.write(msg); bufferedWriter.close();},
                msg -> { bufferedWriter1.write(msg);bufferedWriter1.close();});


        parser.getAnalysisTable();
    }

//    public static void main(String[] args)throws IOException {
//        File inputFile = new File(pathnameInput);
//        File outputFile = new File(pathnameOutput);
//        //检查文件是否存在
//        checkFile(inputFile, outputFile);
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
//
//        Lexer lexer = Lexer.getInstance(inputFile);
//        //委托输出错误
//        lexer.delegate((s)->{
//            try {
//                bufferedWriter.write(s);
//                bufferedWriter.newLine();
//                System.out.println(s);
//            }
//            catch (IOException e){
//                e.printStackTrace();
//            }
//        },()-> flag = true);
//        Token token;
//
//        while (true) {
//            if ((token = lexer.scan()) != null) {
//                String temp = token.getToken();
//                //输出Token
//                bufferedWriter.write(temp);
//                bufferedWriter.newLine();
//                System.out.println(temp);
//            }
//            if(flag)break;
//        }
//        bufferedWriter.close();
//    }

    //检测输出文件是否存在
    private static void checkOutputFile(File outputFile)throws IOException{
        if(outputFile.exists()){
            outputFile.delete();
            outputFile.createNewFile();
        }
        else outputFile.createNewFile();
    }

    //检查输入文件是否存在
    private static void checkInputFile(File inputFile)throws IOException{
        if(!inputFile.exists()){
            System.out.println("无法找到指定文件!\n");
            flag = true;
        }
    }
}

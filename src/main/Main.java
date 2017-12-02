package main;

import lexer.Lexer;
import lexer.Token;
import setsOfItems.NonTerminals;
import setsOfItems.Production;
import setsOfItems.SetsOfItems;
import setsOfItems.Terminal;

import java.io.*;

public class Main {
    private static final String pathnameInput = "./src/test/test3";
    private static final String pathnameOutput = "./src/test/result3.txt";
    private static final String pathnameProduction = "./src/test/Production.txt";
    private static boolean flag = false;
    public static void main(String[] args)throws IOException {
        Terminal terminal = new Terminal('+');
        NonTerminals nonTerminals = new NonTerminals("S");
        Production production = new Production("S");
        SetsOfItems setsOfItems = new SetsOfItems(0);
        setsOfItems.addElement(production);
        production.addElement(nonTerminals);
        production.addElement(terminal);
        production.addElement(nonTerminals);
        System.out.println(setsOfItems.toString());
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

    //检测文件是否存在
    private static void checkFile(File inputFile, File outputFile)throws IOException{
        if(!inputFile.exists()){
            System.out.println("无法找到源文件");
        }
        if(outputFile.exists()){
            outputFile.delete();
            outputFile.createNewFile();
        }
        else outputFile.createNewFile();
    }
}

package main;

import lexer.Lexer;
import lexer.Token;

import java.io.*;

public class Main {
    private static final String pathnameInput = "./test.txt";
    private static final String pathnameOutput = "./result.txt";
    public static void main(String[] args)throws IOException {
        File inputFile = new File(pathnameInput);
        File outputFile = new File(pathnameOutput);
        checkFile(inputFile, outputFile);

        Lexer lexer = new Lexer(inputFile);
        Token token;

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
        while (true) {
            if((token = lexer.scan())==null)break;
            String temp = token.getToken();
            System.out.println(temp);
            bufferedWriter.write(temp);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

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

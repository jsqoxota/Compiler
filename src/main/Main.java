package main;

import lexer.Lexer;
import lexer.Token;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args)throws IOException {
        File fileInput = new File("./test.txt");
        File fileOutput = new File("./result.txt");

        if(!fileInput.exists()){
            System.out.println("无法找到源文件");
        }
        if(fileOutput.exists()){
            fileOutput.delete();
            fileOutput.createNewFile();
        }
        else fileOutput.createNewFile();

        Lexer lexer = new Lexer(fileInput);
        Token token;

        Token.setFile(fileOutput);
        while (true) {
            if((token = lexer.scan())==null)break;
            token.printOnConsole();
            token.outputToFile();
        }
        Token.closeFileWrite();
        lexer.closeFile();
    }
}

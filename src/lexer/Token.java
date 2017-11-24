package lexer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

abstract public class Token {
    protected   int tag;          //内部编码
    private static FileWriter fileWriter = null;

    public Token(int tag){
        this.tag = tag;
    }

    abstract public String toString();

    //控制台打印
    public void printOnConsole(){
        System.out.println("<"+ tag +",\t"+ this.toString() + ">");
    }

    //输出到文件
    public boolean outputToFile()throws IOException{
        if(fileWriter == null)return false;
        fileWriter.write("<"+ tag +",\t"+ this.toString() + ">"+ "\r\n");
        return true;
    }

    public static void setFile(File file)throws IOException{
        fileWriter = new FileWriter(file,true);
    }

    public static void closeFileWrite()throws IOException{
        fileWriter.close();
    }
}
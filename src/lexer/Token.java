package lexer;

abstract public class Token {
    protected   int tag;          //内部编码

    public Token(int tag){
        this.tag = tag;
    }

    abstract public String toString();

    //获取Token
    public String getToken(){
        return "<"+ this.toString() +", "+ this.getTag() + ">";
    }

    abstract public String getTag();
}
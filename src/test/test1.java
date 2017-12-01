package test;

public class test1 {
    public static void main(String []args){
        int a = 0, b = 100;
        a = a + b;a+=b;
        a = a - b;a-=b;
        a = a * b;a *= b;
        a= a/b; a/=b;
        a= a%b; a%= b;
        a++; b--;
        a = a | b; a |= b;
        a = a &b; a &=b;
        a = a^b; a^=b;
        a = ~b;
        a = a << b; a <<= b;
        a = a>>b;   a >>=b;
        a = a>>>b; a>>>=b;

        if(a>b&&a==b ||a>=b );
        if(a<b&&!(a<=b) || a != b);

        boolean f=false;
        a = f?1:4;
        System.out.println(a);
    }
}

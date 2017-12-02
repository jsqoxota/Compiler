package test;

import java.io.IOException;

public class test1 {
    static boolean d = false;
    public static void main(String []args)throws IOException{
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

        int portNumber = 1337;

        Runnable r = () -> System.out.println(portNumber);
    }
}

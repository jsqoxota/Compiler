package byteArrayConversion;

public class numToBytes {
    public static byte[] intToBytes(int num){
        byte[] bytes = new byte[4];
        for (int i = 3; i >= 0; i--){
            bytes[i] = (byte)(num & 0XFF);
            num >>>= 8;
        }
        return bytes;
    }

    public static byte[] shortToBytes(short num){
        byte[] bytes = new byte[2];
        for (int i = 1; i >= 0; i--){
            bytes[i] = (byte)(num & 0XFF);
            num >>>= 8;
        }
        return bytes;
    }

    public static byte[] longToBytes(long num){
        byte[] bytes = new byte[8];
        for (int i = 7; i >= 0; i--){
            bytes[i] = (byte)(num & 0XFF);
            num >>>= 8;
        }
        return bytes;
    }
}

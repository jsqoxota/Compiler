package byteArrayConversion;

//数据高位存放在低地址
public class BytesToNum {

    public static int bytesToInt(byte[] bytes, int index){
        int data = bytes[index] & 0xff;
        for (int j = 1; j <= 3; j++){
            data <<= 8;
            data += bytes[index + j]  & 0xff;
        }
        return data;
    }

    public static short bytesToShort(byte[] bytes, int index){
        short data = (short) (bytes[index] & 0xff);
        for (int j = 1; j <= 1; j++){
            data <<= 8;
            data += bytes[index + j]  & 0xff;
        }
        return data;
    }

    public static long bytesToLong(byte[] bytes, int index){
        long data = bytes[index] & 0xff;
        for (int j = 1; j <= 7; j++){
            data <<= 8;
            data += bytes[index + j]  & 0xff;
        }
        return data;
    }
}

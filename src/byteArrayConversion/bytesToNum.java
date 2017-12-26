package byteArrayConversion;

//数据高位存放在低地址
public class bytesToNum {

    public static int bytesToInt(byte[] bytes, int index){
        int datas = bytes[index] & 0xff;
        for (int j = 1; j <= 3; j++){
            datas <<= 8;
            datas += bytes[index + j]  & 0xff;
        }
        return datas;
    }

    public static short bytesToShort(byte[] bytes, int index){
        short datas = (short) (bytes[index] & 0xff);
        for (int j = 1; j <= 1; j++){
            datas <<= 8;
            datas += bytes[index + j]  & 0xff;
        }
        return datas;
    }

    public static long bytesToLong(byte[] bytes, int index){
        long datas = bytes[index] & 0xff;
        for (int j = 1; j <= 7; j++){
            datas <<= 8;
            datas += bytes[index + j]  & 0xff;
        }
        return datas;
    }
}

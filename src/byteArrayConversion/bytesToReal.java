package byteArrayConversion;

public class bytesToReal {
    public static float bytesToFloat(byte[] bytes, int index){
        return Float.intBitsToFloat(bytesToNum.bytesToInt(bytes, index));
    }

    public static double bytesToDouble(byte[] bytes, int index){
        return Double.longBitsToDouble(bytesToNum.bytesToLong(bytes, index));
    }
}

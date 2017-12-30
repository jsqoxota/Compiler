package byteArrayConversion;

public class BytesToReal {
    public static float bytesToFloat(byte[] bytes, int index){
        return Float.intBitsToFloat(BytesToNum.bytesToInt(bytes, index));
    }

    public static double bytesToDouble(byte[] bytes, int index){
        return Double.longBitsToDouble(BytesToNum.bytesToLong(bytes, index));
    }
}

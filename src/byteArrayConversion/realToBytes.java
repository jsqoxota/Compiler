package byteArrayConversion;

public class realToBytes {
    public static byte[] floatToBytes(float real){
        int num = Float.floatToIntBits(real);
        byte[] bytes = new byte[4];
        for (int i = 3; i >= 0; i--){
            bytes[i] = (byte)(num & 0XFF);
            num >>>= 8;
        }
        return bytes;
    }

    public static byte[] doubleToBytes(double real){
        long num = Double.doubleToLongBits(real);
        byte[] bytes = new byte[8];
        for (int i = 7; i >= 0; i--){
            bytes[i] = (byte)(num & 0XFF);
            num >>>= 8;
        }
        return bytes;
    }
}

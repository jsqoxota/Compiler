package inter;

import byteArrayConversion.NumToBytes;
import byteArrayConversion.RealToBytes;
import lexer.Identifier;
import symbol.Array;
import symbol.Type;

public class Id extends Expr {
    private int  offset;
    private byte[] value;
    public Id(Identifier id, Type p){
        super(id, p);
        offset = p.getWidth();
    }

    public void applySpace(){
        value = new byte[type.getWidth()];
    }

    public int getOffset() {
        return offset;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(String value){
        byte[] values = changeToBytes(value, type);
        for (int i = 0; i < values.length; i++){
            this.value[i] = values[i];
        }
    }

    public void setValue(String value, int width){
        byte[] values = changeToBytes(value, ((Array)type).getBasicType());
        for (int i = width; i < values.length + width; i++ ){
            this.value[i] = values[i - width];
        }
    }

    public byte[] changeToBytes(String value, Type type){
        byte[] values = null;
        if(type == Type.getInt()){
            values = NumToBytes.intToBytes(Integer.parseInt(value));
        }
        else if (type == Type.getShort()){
            values = NumToBytes.shortToBytes(Short.parseShort(value));
        }
        else if (type == Type.getLong()){
            values = NumToBytes.longToBytes(Long.parseLong(value));
        }
        else if(type == Type.getFloat()){
            values = RealToBytes.floatToBytes(Float.parseFloat(value));
        }
        else if (type == Type.getDouble()){
            values = RealToBytes.doubleToBytes(Double.parseDouble(value));
        }
        else if (type == Type.getByte()){
            values = new byte[1];
            values[0] = Byte.parseByte(value);
        }
        else if ((type == Type.getChar())){
            values = new byte[1];
            values[0] = (byte) value.charAt(0);
        }
        else if(type == Type.getBoolean()){
            values = new byte[1];
            if ("true".equals(value)){
                values[0] = 1;
            }
            else if("false".equals(value)){
                values[0] = 0;
            }
        }
        return values;
    }
}

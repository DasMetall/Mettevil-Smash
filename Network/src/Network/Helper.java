/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

/**
 *
 * @author Max Brauer
 */
public class Helper {

    public static byte[] intToByteArray(int value) {
        return new byte[]{
            (byte)(value >>> 24),
            (byte)(value >>> 16),
            (byte)(value >>> 8),
            (byte)value
        };
    }

    public static int byteArrayToInt(byte[] value) {
        return value[3] & 0xFF
                | (value[2] & 0xFF) << 8
                | (value[1] & 0xFF) << 16
                | (value[0] & 0xFF) << 24;
    }

    public static int byteArrayToInt(byte[] value, int offset) {
        return value[3 + offset] & 0xFF
                | (value[2 + offset] & 0xFF) << 8
                | (value[1 + offset] & 0xFF) << 16
                | (value[0 + offset] & 0xFF) << 24;
    }
    
    public static byte[] longToByteArray(long value) {
        return new byte[]{
            (byte)(value >>> 56),
            (byte)(value >>> 48),
            (byte)(value >>> 40),
            (byte)(value >>> 32),
            (byte)(value >>> 24),
            (byte)(value >>> 16),
            (byte)(value >>> 8),
            (byte)value
        };
    }

    public static long byteArrayToLong(byte[] value) {
        return (long)(value[7] & 0xFF)
                | (long)(value[6] & 0xFF) << 8
                | (long)(value[5] & 0xFF) << 16
                | (long)(value[4] & 0xFF) << 24
                | (long)(value[3] & 0xFF) << 32
                | (long)(value[2] & 0xFF) << 40
                | (long)(value[1] & 0xFF) << 48
                | (long)(value[0] & 0xFF) << 56;
    }

    public static long byteArrayToLong(byte[] value, int offset) {
        return (long)(value[7 + offset] & 0xFF)
                | (long)(value[6 + offset] & 0xFF) << 8
                | (long)(value[5 + offset] & 0xFF) << 16
                | (long)(value[4 + offset] & 0xFF) << 24
                | (long)(value[3 + offset] & 0xFF) << 32
                | (long)(value[2 + offset] & 0xFF) << 40
                | (long)(value[1 + offset] & 0xFF) << 48
                | (long)(value[0 + offset] & 0xFF) << 56;
    }

    public static void byteCopyTo(byte[] source, byte[] target, int sourceIndex,
            int targetIndex, int length) {
        for (int i = 0; i < length; ++i)
            target[targetIndex + i] = source[sourceIndex + i];
    }
}

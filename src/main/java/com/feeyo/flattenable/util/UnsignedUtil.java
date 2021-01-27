package com.feeyo.flattenable.util;

import java.nio.ByteBuffer;

public class UnsignedUtil {
	///
	// UnsignedInt
	public static long getUnsignedInt(ByteBuffer buffer) {
		return ((long) buffer.getInt() & 0xffffffffL);
	}

	public static void putUnsignedInt(ByteBuffer buffer, long value) {
		buffer.putInt((int) (value & 0xffffffffL));
	}

	public static long getUnsignedInt(ByteBuffer buffer, int position) {
		return ((long) buffer.getInt(position) & 0xffffffffL);
	}

	public static void putUnsignedInt(ByteBuffer buffer, int position, long value) {
		buffer.putInt(position, (int) (value & 0xffffffffL));
	}
	
	///
	// UnsignedShort
	public static int getUnsignedShort(ByteBuffer buffer) {
        return (buffer.getShort() & 0xffff);
    }

    public static void putUnsignedShort(ByteBuffer buffer, int value) {
        buffer.putShort((short) (value & 0xffff));
    }

    public static int getUnsignedShort(ByteBuffer buffer, int position) {
        return (buffer.getShort(position) & 0xffff);
    }

    public static void putUnsignedShort(ByteBuffer buffer, int position, int value) {
        buffer.putShort(position, (short) (value & 0xffff));
    }
    
    ///
    // UnsignedByte
    public static short getUnsignedByte(ByteBuffer buffer) {
        return ((short) (buffer.get() & 0xff));
    }

    public static void putUnsignedByte(ByteBuffer buffer, int value) {
        buffer.put((byte) (value & 0xff));
    }

    public static short getUnsignedByte(ByteBuffer buffer, int position) {
        return ((short) (buffer.get(position) & (short) 0xff));
    }

    public static void putUnsignedByte(ByteBuffer buffer, int position, int value) {
        buffer.put(position, (byte) (value & 0xff));
    }
}

package com.feeyo.flattenable;

import static com.google.flatbuffers.Constants.*;

import java.nio.ByteBuffer;

/**
 * 用于支持可变 FlatBuffer
 * 
 * @author zhuam
 *
 */
public class FlatBufferMutationHelper {
	
	//
	public static boolean putByte(ByteBuffer buffer, int position, int index, byte value) {
		int o = FlatBufferHelper.__offset(buffer, position, index);
		if (o != 0) {
			buffer.put(o, value);
			return true;
		}
		return false;
	}
	
	public static boolean putShort(ByteBuffer buffer, int position, int index, short value) {
		int o = FlatBufferHelper.__offset(buffer, position, index);
		if (o != 0) {
			buffer.putShort(o, value);
			return true;
		}
		return false;
	}
	
	public static boolean putInt(ByteBuffer buffer, int position, int index, int value) {
		//
		int o = FlatBufferHelper.__offset(buffer, position, index);
		if (o != 0) {
			buffer.putInt(o, value);
			return true;
		}
		return false;
	}

	public static boolean putLong(ByteBuffer buffer, int position, int index, long value) {
		int o = FlatBufferHelper.__offset(buffer, position, index);
		if (o != 0) {
			buffer.putLong(o, value);
			return true;
		}
		return false;
	}

	public static boolean putFloat(ByteBuffer buffer, int position, int index, float value) {
		int o = FlatBufferHelper.__offset(buffer, position, index);
		if (o != 0) {
			buffer.putFloat(o, value);
			return true;
		}
		return false;
	}

	public static boolean putDouble(ByteBuffer buffer, int position, int index, double value) {
		int o = FlatBufferHelper.__offset(buffer, position, index);
		if (o != 0) {
			buffer.putDouble(o, value);
			return true;
		}
		return false;
	}

	public static boolean putBoolean(ByteBuffer buffer, int position, int index, boolean value) {
		int o = FlatBufferHelper.__offset(buffer, position, index);
		if (o != 0) {
			buffer.put(o, (byte) (value ? 1 : 0));
			return true;
		}
		return false;
	}
	
	//
	// Fixed length and variable length
	public static boolean putString(ByteBuffer buffer, int position, int index, String value) {
		return putBytes(buffer, position, index, value.getBytes());
	}
	
	//
	public static boolean putBytes(ByteBuffer buffer, int position, int index, byte[] value) {
		int o = FlatBufferHelper.__offset(buffer, position, index);
		if (o != 0) {
			int voffset = o + buffer.getInt(o);
			int length = buffer.getInt(voffset);
			//
			// mutated Bytes
			int gap = length - value.length;
			if ( gap < 0 ) {
				// variable length
				throw new NotMutatedException("Not mutated, make sure the fixed length of the bytes is " + length);
			}
			
			//
			// fixed length
			int off = voffset + SIZEOF_INT;
			for (int i = off; i < off + length; i++)
				if ( i < off + value.length )
					buffer.put(i, value[i - off]);
				else if ( gap != 0 )
					buffer.put(i, (byte)' ');
			return true;
		}
		return false;
	}
	
	//
	// Vector
	//
	public static boolean putByte(ByteBuffer buffer, int position, int index, int vindex, byte value) {
		int o = FlatBufferHelper.__offset(buffer, position, index);
		if (o != 0) {
			int voffset = o + buffer.getInt(o) + SIZEOF_INT;
			buffer.put(voffset + vindex * SIZEOF_BYTE, value);
			return true;
		}
		return false;
	}
	
	public static boolean putShort(ByteBuffer buffer, int position, int index, int vindex, short value) {
		int o = FlatBufferHelper.__offset(buffer, position, index);
		if (o != 0) {
			int voffset = o + buffer.getInt(o) + SIZEOF_INT;
			buffer.putShort(voffset + vindex * SIZEOF_SHORT, value);
			return true;
		}
		return false;
	}
	
	public static boolean putInt(ByteBuffer buffer, int position, int index, int vindex, int value) {
		int o = FlatBufferHelper.__offset(buffer, position, index);
		if (o != 0) {
			int voffset = o + buffer.getInt(o) + SIZEOF_INT;
			buffer.putInt(voffset + vindex * SIZEOF_INT, value);
			return true;
		}
		return false;
	}
	
	public static boolean putLong(ByteBuffer buffer, int position, int index, int vindex, long value) {
		int o = FlatBufferHelper.__offset(buffer, position, index);
		if (o != 0) {
			int voffset = o + buffer.getInt(o) + SIZEOF_INT;
			buffer.putLong(voffset + vindex * SIZEOF_LONG, value);
			return true;
		}
		return false;
	}
	
	public static boolean putFloat(ByteBuffer buffer, int position, int index, int vindex, float value) {
		int o = FlatBufferHelper.__offset(buffer, position, index);
		if (o != 0) {
			int voffset = o + buffer.getInt(o) + SIZEOF_INT;
			buffer.putFloat(voffset + vindex * SIZEOF_FLOAT, value);
			return true;
		}
		return false;
	}
	
	public static boolean putDouble(ByteBuffer buffer, int position, int index, int vindex, double value) {
		//
		// offset An `int` index into the Table's ByteBuffer.
		int o = FlatBufferHelper.__offset(buffer, position, index);
		if (o != 0) {
			// Get the start data of a vector
			int voffset = o + buffer.getInt(o) + SIZEOF_INT;
			buffer.putDouble(voffset + vindex * SIZEOF_DOUBLE, value);
			return true;
		}
		return false;
	}
	
	public static boolean putBoolean(ByteBuffer buffer, int position, int index, int vindex, boolean value) {
		int o = FlatBufferHelper.__offset(buffer, position, index);
		if (o != 0) {
			int voffset = o + buffer.getInt(o) + SIZEOF_INT;
			buffer.put(voffset + vindex * SIZEOF_BYTE,  (byte) (value ? 1 : 0));
			return true;
		}
		return false;
	}
}
package com.feeyo.flattenable;

import com.google.flatbuffers.Utf8;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.google.flatbuffers.Constants.*;

/**
 * FlatBuffer 操作助手
 */
public final class FlatBufferHelper {

    /**
     * Root object position
     */
    public static int getRootObjectPosition(ByteBuffer buffer) {
        int position;
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        synchronized (buffer) {
            position = buffer.position();
        }
        //
        return buffer.getInt(position) + position;
    }

    /**
     * Child object position
     */
    public static int getChildObjectPosition(ByteBuffer buffer, int position, int index) {
        int offset = __offset(buffer, position, index);
        return offset != 0 ? __indirect(buffer, offset) : 0;
    }

    /**
     * Look up a field in the table.
     *
     * @param index field index in table.
     * @return Returns an offset into the object, or `0` if the field is not
     * present.
     */
    public static int __offset(ByteBuffer buffer, int position, int index) {
        int vtable_start = position - buffer.getInt(position);
        int vtable_size = buffer.getShort(vtable_start);
        int vtable_offset = (index * 2) + 4; // vtable_offset An `int` offset to the vtable in the Table's ByteBuffer.
        if (vtable_offset < vtable_size) { 
        	// Look up a field in the vtable.
            int o = buffer.getShort(vtable_start + vtable_offset); 
            if (o != 0) //
                return o + position; // field absolute offset
        }
        return 0;
    }

    /**
     * Retrieve a relative offset.
     *
     * @param offset An `int` index into the Table's ByteBuffer containing the relative offset.
     * @return Returns the relative offset stored at `offset`.
     */
	public static int __indirect(ByteBuffer buffer, int offset) {
		return offset + buffer.getInt(offset);
	}
    
    
    /**
     * 读取字节
     *
     * @param buffer   byte buffer
     * @param position parent object position
     * @param index    field number for object field
     * @param d        default value
     * @return
     */
    public static byte getByte(ByteBuffer buffer, int position, int index, byte d) {
        int off = __offset(buffer, position, index);
        return off != 0 ? buffer.get(off) : d;
    }

    public static short getShort(ByteBuffer buffer, int position, int index, short d) {
        int off = __offset(buffer, position, index);
        return off != 0 ? buffer.getShort(off) : d;
    }

    public static int getInt(ByteBuffer buffer, int position, int index, int d) {
        int off = __offset(buffer, position, index);
        return off != 0 ? buffer.getInt(off) : d;
    }

    public static long getLong(ByteBuffer buffer, int position, int index, long d) {
        int off = __offset(buffer, position, index);
        return off != 0 ? buffer.getLong(off) : d;
    }

    public static float getFloat(ByteBuffer buffer, int position, int index, float d) {
        int off = __offset(buffer, position, index);
        return off != 0 ? buffer.getFloat(off) : d;
    }

    public static double getDouble(ByteBuffer buffer, int position, int index, double d) {
        int off = __offset(buffer, position, index);
        return off != 0 ? buffer.getDouble(off) : d;
    }

    public static boolean getBoolean(ByteBuffer buffer, int position, int index) {
        int off = __offset(buffer, position, index);
        if (off == 0)
            return false;
        //
        if (buffer.get(off) == (byte) 1)
            return true;
        //
        return false;
    }

    public static String getString(ByteBuffer buffer, int position, int index) {
        int o = getChildObjectPosition(buffer, position, index);
        if (o != 0)
            return getString(buffer, o);
        return null;
    }

    /**
     * Create a Java `String` from UTF-8 data stored inside the FlatBuffer.
     * <p>
     * This allocates a new string and converts to wide chars upon each access,
     * which is not very efficient. Instead, each FlatBuffer string also comes
     * with an accessor based on __vector_as_bytebuffer below, which is much
     * more efficient, assuming your Java program can handle UTF-8 data
     * directly.
     *
     * @param index An `int` index into the Table's ByteBuffer.
     * @return Returns a `String` from the data stored inside the FlatBuffer at
     * `offset`.
     */
    public static String getString(ByteBuffer buffer, int index) {
        return Utf8.getDefault().decodeUtf8(buffer, index + SIZEOF_INT, buffer.getInt(index));
    }

    public static <T extends Flattenable> T getFlattenable(ByteBuffer buffer, int position, int index, Class<T> clazz) {
        int o = getChildObjectPosition(buffer, position, index);
        if (o == 0)
            return null;
        //
        try {
            T flattenable = clazz.newInstance();
            flattenable.initFromByteBuffer(buffer, o);
            return flattenable;
        } catch (Throwable e) {
            throw new RuntimeException("Not able to create object", e);
        }
    }
    
    //
    public static <T extends Flattenable> T getFlattenable(ByteBuffer buffer, int position, int index, VirtualFlattenableResolver<T> resolver) {
        int o = getChildObjectPosition(buffer, position, index);
        if (o == 0)
            return null;
        //
        try {
            T flattenable = resolver.newInstance();
            flattenable.initFromByteBuffer(buffer, o);
            return flattenable;
        } catch (Throwable e) {
            throw new RuntimeException("Not able to create object", e);
        }
    }
    
    //
    public static byte[] getByteArray(ByteBuffer buffer, int position, int index) {
        int o = getChildObjectPosition(buffer, position, index);
        if (o == 0)
            return null;
        //
        byte[] arr = new byte[getSize(buffer, o)];
        for (int i = 0; i < arr.length; i++) 
            arr[i] = buffer.get((i * SIZEOF_BYTE) + o + 4);
        return arr;
    }
    
    /**
     * TODO: Important, only for fixed size 
     */
    public static void getByteArray(ByteBuffer buffer, int position, int index, byte[] value) {
        int o = getChildObjectPosition(buffer, position, index);
        if (o == 0)
            return;
        //
        int size = getSize(buffer, o);
        if ( value.length < size )
        	throw new RuntimeException("Value byte array is not long enough, size=" +  size);
        //
        for (int i = 0; i < size; i++) 
        	value[i] = buffer.get((i * SIZEOF_BYTE) + o + 4);
    }
    
    //
    public static short[] getShortArray(ByteBuffer buffer, int position, int index) {
        int o = getChildObjectPosition(buffer, position, index);
        if (o == 0)
            return null;
        //
        short[] arr = new short[getSize(buffer, o)];
        for (int i = 0; i < arr.length; i++) 
            arr[i] = buffer.getShort((i * SIZEOF_SHORT) + o + 4);
        return arr;
    }
    
    /**
     * TODO: Important, only for fixed size
     */
    public static void getShortArray(ByteBuffer buffer, int position, int index, short[] value) {
        int o = getChildObjectPosition(buffer, position, index);
        if (o == 0)
            return;
        //
        int size = getSize(buffer, o);
        if ( value.length < size )
        	throw new RuntimeException("Value short array is not long enough, size=" +  size);
        //
        for (int i = 0; i < size; i++) 
        	value[i] = buffer.getShort((i * SIZEOF_SHORT) + o + 4);
    }

    public static int[] getIntArray(ByteBuffer buffer, int position, int index) {
        int o = getChildObjectPosition(buffer, position, index);
        if (o == 0)
            return null;
        //
        int[] arr = new int[getSize(buffer, o)];
        for (int i = 0; i < arr.length; i++) 
            arr[i] = buffer.getInt((i * SIZEOF_INT) + o + 4);
        return arr;
    }
    
    /**
     * TODO: Important, only for fixed size
     */
    public static void getIntArray(ByteBuffer buffer, int position, int index, int[] value) {
        int o = getChildObjectPosition(buffer, position, index);
        if (o == 0)
            return;
        //
        int size = getSize(buffer, o);
        if ( value.length < size )
        	throw new RuntimeException("Value int array is not long enough, size=" +  size);
        //
        for (int i = 0; i < size; i++) 
        	value[i] = buffer.getInt((i * SIZEOF_INT) + o + 4);
    }

    public static long[] getLongArray(ByteBuffer buffer, int position, int index) {
        int o = getChildObjectPosition(buffer, position, index);
        if (o == 0)
            return null;
        //
        long[] arr = new long[getSize(buffer, o)];
        for (int i = 0; i < arr.length; i++) 
            arr[i] = buffer.getLong((i * SIZEOF_LONG) + o + 4);
        return arr;
    }
    
    /**
     * TODO: Important, only for fixed size
     */
    public static void getLongArray(ByteBuffer buffer, int position, int index, long[] value) {
        int o = getChildObjectPosition(buffer, position, index);
        if (o == 0)
            return;
        //
        int size = getSize(buffer, o);
        if ( value.length < size )
        	throw new RuntimeException("Value long array is not long enough, size=" +  size);
        //
        for (int i = 0; i < size; i++) 
        	value[i] = buffer.getLong((i * SIZEOF_LONG) + o + 4);
    }

    public static float[] getFloatArray(ByteBuffer buffer, int position, int index) {
        int o = getChildObjectPosition(buffer, position, index);
        if (o == 0)
            return null;
        //
        float[] arr = new float[getSize(buffer, o)];
        for (int i = 0; i < arr.length; i++) 
            arr[i] = buffer.getFloat((i * SIZEOF_FLOAT) + o + 4);
        return arr;
    }
    
    /**
     * TODO: Important, only for fixed size
     */
    public static void getFloatArray(ByteBuffer buffer, int position, int index, float[] value) {
        int o = getChildObjectPosition(buffer, position, index);
        if (o == 0)
            return;
        //
        int size = getSize(buffer, o);
        if ( value.length < size )
        	throw new RuntimeException("Value float array is not long enough, size=" +  size);
        //
        for (int i = 0; i < size; i++) 
        	value[i] = buffer.getFloat((i * SIZEOF_FLOAT) + o + 4);
    }

    public static double[] getDoubleArray(ByteBuffer buffer, int position, int index) {
        int o = getChildObjectPosition(buffer, position, index);
        if (o == 0)
            return null;
        //
        double[] arr = new double[getSize(buffer, o)];
        for (int i = 0; i < arr.length; i++) 
            arr[i] = buffer.getDouble((i * SIZEOF_DOUBLE) + o + 4);
        return arr;
    }
    //
    public static void getDoubleArray(ByteBuffer buffer, int position, int index, double[] value) {
        int o = getChildObjectPosition(buffer, position, index);
        if (o == 0)
            return;
        //
        int size = getSize(buffer, o);
        if ( value.length < size )
        	throw new RuntimeException("Value double array is not long enough, size=" +  size);
        //
        for (int i = 0; i < size; i++) 
        	value[i] = buffer.getDouble((i * SIZEOF_DOUBLE) + o + 4);
    }

    public static String[] getStringArray(ByteBuffer buffer, int position, int index) {
        int o = getChildObjectPosition(buffer, position, index);
        if (o == 0)
            return null;
        //
        try {
            int size = getSize(buffer, o);
            String[] arr = new String[size];
            //
            for (int i = 0; i < size; i++) {
                int i4 = o + SIZEOF_INT + (i * SIZEOF_INT);
                int i5 = buffer.getInt(i4);
                if (i5 == 0) {
                    arr[i] = null;
                } else {
                    arr[i] = getString(buffer, i4 + i5);
                }
            }
            return arr;

        } catch (Throwable e) {
            throw new RuntimeException("Not able to create string", e);
        }
    }
    
    public static <T extends Flattenable> FlattenableIterator<T> getIteratorFromByteBuffer(ByteBuffer buffer, Class<T> clazz) {
        int position = getRootObjectPosition(buffer);
        int o = getChildObjectPosition(buffer, position, 0);
        int offset = o + SIZEOF_INT;
        int size = getSize(buffer, o);
        //
        FlattenableIterator<T> iterator = new FlattenableIterator<T>(buffer, offset, size, clazz);
        return iterator;
    }

    public static <T extends Flattenable> FlattenableIterator<T> getIteratorFromByteBuffer(ByteBuffer buffer, VirtualFlattenableResolver<T> resolver) {
        int position = getRootObjectPosition(buffer);
        int o = getChildObjectPosition(buffer, position, 0);
        int size = getSize(buffer, o);
        //
        FlattenableIterator<T> iterator = new FlattenableIterator<T>(buffer, o, size, resolver);
        return iterator;
    }

    //
    public static int getSize(ByteBuffer buffer, int index) {
        return buffer.getInt(index);
    }

    //
    // -------------------- Byte compare --------------
    //
	public static boolean isByteArrayEquals(ByteBuffer buffer, int position, int index, byte[] value) {
		if (value == null) 
			throw new RuntimeException("Value byte array can not be null");
		//
		int o = getChildObjectPosition(buffer, position, index);
		if (o == 0)
			throw new RuntimeException("Not able to get byte array");
		//
		int size = getSize(buffer, o);
		if (size != value.length) 
			return false;
		//
		for (int i = 0; i < size; i++) {
			if (value[i] != buffer.get((i * SIZEOF_BYTE) + o + 4)) 
				return false;
		}
		return true;
	}
	
	public static boolean isPrefixByteArrayMatch(ByteBuffer buffer, int position, int index, byte[] prefix) {
		if (prefix == null) 
			throw new RuntimeException("Prefix byte array can not be null");
		//
		int o = getChildObjectPosition(buffer, position, index);
		if (o == 0)
			throw new RuntimeException("Not able to get byte array");
		//
		int size = getSize(buffer, o);
		if (size < prefix.length) 
			return false;
		//
		for (int i = 0; i < prefix.length; i++) {
			if (prefix[i] != buffer.get((i * SIZEOF_BYTE) + o + 4)) 
				return false;
		}
		return true;
	}
}
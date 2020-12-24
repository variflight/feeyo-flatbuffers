package com.feeyo.flattenable;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.flatbuffers.FlatBufferBuilder;
import static com.google.flatbuffers.Constants.*;

public class FlattenableHelper {
	//
	public static <T extends Flattenable> int flattenToBuffer(FlatBufferBuilder flatBufferBuilder, T flattenable) {
		if (flattenable == null)
			return 0;
		return flattenable.flattenToBuffer(flatBufferBuilder);
	}
	//
	public static <T extends Flattenable> int flattenToBuffer(FlatBufferBuilder flatBufferBuilder, List<T> list) {
		int x = createFlattenableVector(flatBufferBuilder, list);
		return createTable(flatBufferBuilder, x);
	}
	//
	public static int flattenToBuffer(FlatBufferBuilder flatBufferBuilder, String[] strArray) {
		int x = createStringVector(flatBufferBuilder, strArray);
		return createTable(flatBufferBuilder, x);
	}

	public static int flattenToBuffer(FlatBufferBuilder flatBufferBuilder, int[] intArray) {
		int x = createIntVector(flatBufferBuilder, intArray);
		return createTable(flatBufferBuilder, x);
	}

	public static int flattenToBuffer(FlatBufferBuilder flatBufferBuilder, long[] longArray) {
		int x = createLongVector(flatBufferBuilder, longArray);
		return createTable(flatBufferBuilder, x);
	}

	public static int flattenToBuffer(FlatBufferBuilder flatBufferBuilder, double[] doubleArray) {
		int x = createDoubleVector(flatBufferBuilder, doubleArray);
		return createTable(flatBufferBuilder, x);
	}

	public static int flattenToBuffer(FlatBufferBuilder flatBufferBuilder, float[] floatArray) {
		int x = createFloatVector(flatBufferBuilder, floatArray);
		return createTable(flatBufferBuilder, x);
	}

	public static int flattenToBuffer(FlatBufferBuilder flatBufferBuilder, short[] shortArray) {
		int x = createShortVector(flatBufferBuilder, shortArray);
		return createTable(flatBufferBuilder, x);
	}

	//
	public static <T extends Flattenable> int flattenToBuffer(FlatBufferBuilder flatBufferBuilder, Map<String, T> map) {
		if (map == null || map.isEmpty())
			return 0;
		//
		String[] keyArray = new String[map.size()];
		map.keySet().toArray(keyArray);

		int x1 = createStringVector(flatBufferBuilder, keyArray);
		int x2 = createFlattenableVector(flatBufferBuilder, map.values());
		//
		// table
		flatBufferBuilder.startTable(2);
		flatBufferBuilder.addOffset(0, x1, 0);
		flatBufferBuilder.addOffset(1, x2, 0); //
		int x = flatBufferBuilder.endTable();
		//
		// table
		flatBufferBuilder.startTable(1);
		flatBufferBuilder.addOffset(0, x, 0);
		x = flatBufferBuilder.endTable();
		if (x < 0) {
			return x;
		}
		//
		flatBufferBuilder.finish(x);
		return x;
	}

	//
	public static int createTable(FlatBufferBuilder flatBufferBuilder, int x) {
		flatBufferBuilder.startTable(1);
		flatBufferBuilder.addOffset(0, x, 0);
		int o = flatBufferBuilder.endTable();
		if (o < 0)
			return -1;
		//
		flatBufferBuilder.finish(o);
		return o;
	}

	//////
	//
	public static int createVector(FlatBufferBuilder builder, ByteBuffer offsetBuffer) {
		int size = offsetBuffer.position() / Integer.BYTES;
		builder.startVector(SIZEOF_INT, size, SIZEOF_INT);
		for (int i = size - 1; i >= 0; i--)
			builder.addOffset(offsetBuffer.getInt(i * Integer.BYTES));
		return builder.endVector();
	}

	public static int createVector(FlatBufferBuilder flatBufferBuilder, int[] offsets) {
		flatBufferBuilder.startVector(SIZEOF_INT, offsets.length, SIZEOF_INT);
		for (int j = offsets.length - 1; j >= 0; j--)
			flatBufferBuilder.addOffset(offsets[j]);
		return flatBufferBuilder.endVector();
	}
	
	public static int createVector(FlatBufferBuilder flatBufferBuilder, int[] offsets, int off, int length) {
		flatBufferBuilder.startVector(SIZEOF_INT, length, SIZEOF_INT);
		for (int j = (length + off - 1); j >= off; j--)
			flatBufferBuilder.addOffset(offsets[j]);
		return flatBufferBuilder.endVector();
	}

	public static <T extends Flattenable> int createFlattenableVector(FlatBufferBuilder flatBufferBuilder, Collection<T> flattenables) {
		if (flattenables == null || flattenables.size() == 0)
			return 0;
		//
		int[] offsets = new int[flattenables.size()];
		int i = 0;
		for (T t : flattenables) {
			offsets[i] = t != null ? t.flattenToBuffer(flatBufferBuilder) : 0;
			i++;
		}
		return createVector(flatBufferBuilder, offsets);
	}
	
	//
	public static <T extends Flattenable> int createFlattenableVector(FlatBufferBuilder flatBufferBuilder, IFlattenableList<T> flattenables) {
		if (flattenables == null || flattenables.size() == 0)
			return 0;
		//
		int size = flattenables.size();
		int[] offsets = new int[ size ];
		for (int i = 0; i <  size; i++) {
			T t = flattenables.get(i);
			offsets[i] = t != null ? t.flattenToBuffer(flatBufferBuilder) : 0;
		}
		return createVector(flatBufferBuilder, offsets);
	}

	//
	public static int createShortVector(FlatBufferBuilder flatBufferBuilder, short[] shortArray) {
		if (shortArray == null || shortArray.length == 0)
			return 0;
		//
		int size = shortArray.length;
		flatBufferBuilder.startVector(SIZEOF_SHORT, size, SIZEOF_SHORT);
		for (int i = size - 1; i >= 0; i--)
			flatBufferBuilder.putShort(shortArray[i]);
		return flatBufferBuilder.endVector();
	}

	//
	public static int createIntVector(FlatBufferBuilder flatBufferBuilder, int[] intArray) {
		if (intArray == null || intArray.length == 0)
			return 0;
		//
		int size = intArray.length;
		flatBufferBuilder.startVector(SIZEOF_INT, size, SIZEOF_INT);
		for (int i = size - 1; i >= 0; i--)
			flatBufferBuilder.putInt(intArray[i]);
		return flatBufferBuilder.endVector();
	}

	public static int createLongVector(FlatBufferBuilder flatBufferBuilder, long[] longArray) {
		if (longArray == null || longArray.length == 0)
			return 0;
		int size = longArray.length;
		flatBufferBuilder.startVector(SIZEOF_LONG, size, SIZEOF_LONG);
		for (int i = size - 1; i >= 0; i--)
			flatBufferBuilder.putLong(longArray[i]);
		return flatBufferBuilder.endVector();
	}

	public static int createFloatVector(FlatBufferBuilder flatBufferBuilder, float[] floatArray) {
		if (floatArray == null || floatArray.length == 0)
			return 0;
		//
		int size = floatArray.length;
		flatBufferBuilder.startVector(SIZEOF_FLOAT, size, SIZEOF_FLOAT);
		for (int i = size - 1; i >= 0; i--)
			flatBufferBuilder.putFloat(floatArray[i]);
		return flatBufferBuilder.endVector();
	}

	public static int createDoubleVector(FlatBufferBuilder flatBufferBuilder, double[] doubleArray) {
		if (doubleArray == null || doubleArray.length == 0)
			return 0;
		int size = doubleArray.length;
		flatBufferBuilder.startVector(SIZEOF_DOUBLE, size, SIZEOF_DOUBLE);
		for (int i = size - 1; i >= 0; i--)
			flatBufferBuilder.putDouble(doubleArray[i]);
		return flatBufferBuilder.endVector();
	}

	public static int createStringVector(FlatBufferBuilder flatBufferBuilder, String[] strArray) {
		if (strArray == null || strArray.length == 0)
			return 0;
		//
		int[] offsets = new int[strArray.length];
		int i = 0;
		for (String b : strArray) {
			offsets[i] = flatBufferBuilder.createString(b);
			i++;
		}
		return createVector(flatBufferBuilder, offsets);
	}

}

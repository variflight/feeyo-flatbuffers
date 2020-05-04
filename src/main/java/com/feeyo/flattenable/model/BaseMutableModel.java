package com.feeyo.flattenable.model;

import com.feeyo.flattenable.FlatBufferMutationHelper;

/**
 * Mutation supported
 * 
 * @author zhuam
 *
 */
public abstract class BaseMutableModel extends BaseModel {

	//
	// mutate base field
	//
	public boolean putByte(int index, byte value) {
		return FlatBufferMutationHelper.putByte(buffer, position, index, value);
	}

	public boolean putShort(int index, short value) {
		return FlatBufferMutationHelper.putShort(buffer, position, index, value);
	}

	public boolean putInt(int index, int value) {
		return FlatBufferMutationHelper.putInt(buffer, position, index, value);
	}

	public boolean putLong(int index, long value) {
		return FlatBufferMutationHelper.putLong(buffer, position, index, value);
	}

	public boolean putFloat(int index, float value) {
		return FlatBufferMutationHelper.putFloat(buffer, position, index, value);
	}

	public boolean putDouble(int index, double value) {
		return FlatBufferMutationHelper.putDouble(buffer, position, index, value);
	}

	public boolean putBoolean(int index, boolean value) {
		return FlatBufferMutationHelper.putBoolean(buffer, position, index, value);
	}

	public boolean putString(int index, String value) {
		return FlatBufferMutationHelper.putString(buffer, position, index, value);
	}
	
	public boolean putBytes(int index, byte[] value) {
		return FlatBufferMutationHelper.putBytes(buffer, position, index, value);
	}

	//
	// mutate vector
	//
	public boolean putByte(int index, int vindex, byte value) {
		return FlatBufferMutationHelper.putByte(buffer, position, index, vindex, value);
	}

	public boolean putShort(int index, int vindex, short value) {
		return FlatBufferMutationHelper.putShort(buffer, position, index, vindex, value);
	}

	public boolean putInt(int index, int vindex, int value) {
		return FlatBufferMutationHelper.putInt(buffer, position, index, vindex, value);
	}

	public boolean putLong(int index, int vindex, long value) {
		return FlatBufferMutationHelper.putLong(buffer, position, index, vindex, value);
	}

	public boolean putFloat(int index, int vindex, float value) {
		return FlatBufferMutationHelper.putFloat(buffer, position, index, vindex, value);
	}

	public boolean putDouble(int index, int vindex, double value) {
		return FlatBufferMutationHelper.putDouble(buffer, position, index, vindex, value);
	}

	public boolean putBoolean(int index, int vindex, boolean value) {
		return FlatBufferMutationHelper.putBoolean(buffer, position, index, vindex, value);
	}
	
	//
	// TODO: make a (deep) copy of bytes before the mutation
	@SuppressWarnings("unchecked")
	public <T extends BaseMutableModel> T clone(Cloner<T> cloner) {
		return cloner.deepClone( (T)this );
	}
	
	//
	public static interface Cloner<T extends BaseMutableModel> {
		public T deepClone(T model);
	}
}
package com.feeyo.flattenable;

import java.nio.ByteBuffer;

import com.google.flatbuffers.FlatBufferBuilder;

/**
 * Flatten 列表
 * 
 * @author zhuam
 *
 * @param <E>
 */
public interface IFlattenableList<E extends Flattenable> {
	//
	public void initFromByteBuffer(ByteBuffer buffer);
	public void initFromByteBuffer(ByteBuffer buffer, int offset, int size);
	public void initFromFlatBufferBuilder(FlatBufferBuilder flatBufferBuilder, int[] offsets);
	
	//
	public ByteBuffer getByteBuffer();
	//
	public int add(FlatBufferBuilder flatBufferBuilder, E e);
	public E get(int index);
	public int size();
}

package com.feeyo.flattenable;

import com.google.flatbuffers.FlatBufferBuilder;

import java.nio.ByteBuffer;

import static com.google.flatbuffers.Constants.SIZEOF_INT;

public class FlattenableList<E extends Flattenable> implements IFlattenableList<E> {
	//
	protected ByteBuffer buffer;
	//
	private final Class<E> clazz;
	private final VirtualFlattenableResolver<E> resolver;
	protected int off;
	protected int size;
	
	//
	// 是否已初始化
	private boolean isInitialized = false;

	public FlattenableList(Class<E> clazz) {
		this.clazz = clazz;
		this.resolver = null;
	}
	
	public FlattenableList(VirtualFlattenableResolver<E> resolver) {
		this.resolver = resolver;
		this.clazz = null;
	}

	@Override
	public void initFromByteBuffer(ByteBuffer buffer) {
		this.buffer = buffer;
		//
		int position = FlatBufferHelper.getRootObjectPosition(buffer);
		int o = FlatBufferHelper.getChildObjectPosition(buffer, position, 0);
		this.off = o + SIZEOF_INT;
		this.size = FlatBufferHelper.getSize(buffer, o);
		this.isInitialized = true;
	}
	
	@Override
	public void initFromByteBuffer(ByteBuffer buffer, int offset, int size) {
		this.buffer = buffer;
		//
		this.off = offset;
		this.size = size;
		this.isInitialized = true;
	}

	//
	@Override
	public void initFromFlatBufferBuilder(FlatBufferBuilder flatBufferBuilder, int[] offsets) {
		//
		int x = FlattenableHelper.createVector(flatBufferBuilder, offsets);
		FlattenableHelper.createTable(flatBufferBuilder, x);
		//
		ByteBuffer byteBuffer = flatBufferBuilder.dataBuffer();
		this.initFromByteBuffer(byteBuffer);
	}
	
	@Override
	public ByteBuffer getByteBuffer() {
		return buffer;
	}

	public int add(FlatBufferBuilder flatBufferBuilder, E e) {
		if ( isInitialized )
			return -1;
		//
		return e.flattenToBuffer(flatBufferBuilder);
	}

	public E get(int index) {
		if (index < 0 || index >= this.size)
			return null;
		//
		int i = this.off + (index * SIZEOF_INT);
		int i2 = buffer.getInt(i);
		if (i2 == 0)
			return null;
		//
		try {
			if (this.clazz != null) {
				E flattenable = this.clazz.newInstance();
				flattenable.initFromByteBuffer(buffer, i + i2);
				return flattenable;
			} else if (this.resolver != null) {
				E flattenable = this.resolver.newInstance();
				flattenable.initFromByteBuffer(buffer, i + i2);
				return flattenable;
			} else {
	            throw new RuntimeException("Either clazz or resolver should be provided");
	        }
		} catch (Throwable e) {
			throw new RuntimeException("Not able to create flattenable", e);
		}
	}

	public int positionOf(int index) {
		if (index < 0 || index >= this.size)
			return -1;
		int i = this.off + (index * SIZEOF_INT);
		int i2 = buffer.getInt(i);
		if (i2 == 0)
			return -1;
		return i + i2;
	}
	
	@Override
	public int size() {
		return size;
	}
}
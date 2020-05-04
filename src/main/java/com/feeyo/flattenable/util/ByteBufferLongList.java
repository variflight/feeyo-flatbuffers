package com.feeyo.flattenable.util;

import java.nio.ByteBuffer;

public class ByteBufferLongList {

	private final ByteBuffer buffer;
	private final int maxSize;
	private int size;

	public ByteBufferLongList(ByteBuffer buffer, int maxSize) {
		this.buffer = buffer;
		this.maxSize = maxSize;
		this.size = 0;
		//
		if (buffer.capacity() < (maxSize * Long.BYTES)) {
			throw new IllegalArgumentException(String.format("buffer for list is too small, was [%s] bytes, but need [%s] bytes.", buffer.capacity(), maxSize * Long.BYTES));
		}
	}

	public void add(long val) {
		if (size == maxSize) 
			throw new IndexOutOfBoundsException(String.format("List is full with %d elements.", maxSize));
		//
		buffer.putLong(size * Long.BYTES, val);
		size++;
	}

	public void set(int index, long val) {
		buffer.putLong(index * Long.BYTES, val);
	}

	public long get(int index) {
		return buffer.getLong(index * Long.BYTES);
	}
	
	public int size() {
		return size;
	}

	public void reset() {
		size = 0;
	}
}
package com.feeyo.flattenable.util;

import java.nio.ByteBuffer;

public class ByteBufferIntList {

	private final ByteBuffer buffer;
	private final int maxSize;
	private int size;

	public ByteBufferIntList(ByteBuffer buffer, int maxSize) {
		this.buffer = buffer;
		this.maxSize = maxSize;
		this.size = 0;
		//
		if ( buffer.capacity() < (maxSize * Integer.BYTES)) {
			throw new IllegalArgumentException(String.format("buffer for list is too small, was [%s] bytes, but need [%s] bytes.",
							buffer.capacity(), maxSize * Integer.BYTES));
		}
	}

	public void add(int val) {
		if (size == maxSize) 
			throw new IndexOutOfBoundsException(String.format("List is full with %d elements.", maxSize));
		//
		buffer.putInt(size * Integer.BYTES, val);
		size++;
	}

	public void set(int index, int val) {
		buffer.putInt(index * Integer.BYTES, val);
	}

	public int get(int index) {
		return buffer.getInt(index * Integer.BYTES);
	}
	
	public int size() {
		return size;
	}

	public void reset() {
		size = 0;
	}
}
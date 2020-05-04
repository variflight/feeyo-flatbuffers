package com.feeyo.flattenable.util;

import java.nio.ByteBuffer;

public class ByteBufferDoubleList {

	private final ByteBuffer buffer;
	private final int maxSize;
	private int size;

	public ByteBufferDoubleList(ByteBuffer buffer, int maxSize) {
		this.buffer = buffer;
		this.maxSize = maxSize;
		this.size = 0;
		//
		if (buffer.capacity() < (maxSize * Double.BYTES)) {
			throw new IllegalArgumentException(String.format("buffer for list is too small, was [%s] bytes, but need [%s] bytes.",
							buffer.capacity(), maxSize * Double.BYTES));
		}
	}

	public void add(double val) {
		if (size == maxSize) 
			throw new IndexOutOfBoundsException(String.format("List is full with %d elements.", maxSize));
		//
		buffer.putDouble(size * Double.BYTES, val);
		size++;
	}

	public void set(int index, double val) {
		buffer.putDouble(index * Double.BYTES, val);
	}

	public double get(int index) {
		return buffer.getDouble(index * Double.BYTES);
	}
	
	public int size() {
		return size;
	}

	public void reset() {
		size = 0;
	}
}
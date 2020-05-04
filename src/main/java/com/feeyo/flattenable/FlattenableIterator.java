package com.feeyo.flattenable;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.google.flatbuffers.Constants.*;

public class FlattenableIterator<T extends Flattenable> implements Iterator<T> {
	//
	private final ByteBuffer byteBuffer;
	private final int offset;
	private final int size;
	//
	private final Class<T> clazz;
    private final VirtualFlattenableResolver<T> resolver;
	private int index = 0;

	public FlattenableIterator(ByteBuffer byteBuffer, int offset, int size, Class<T> clazz) {
		this.byteBuffer = byteBuffer;
		this.offset = offset;
		this.size = size;
		this.clazz = clazz;
		this.resolver = null;
	}
	
	public FlattenableIterator(ByteBuffer byteBuffer, int offset, int size, VirtualFlattenableResolver<T> resolver) {
        this.byteBuffer = byteBuffer;
        this.offset = offset;
        this.size = size;
        this.clazz = null;
        this.resolver = resolver;
    }

	@Override
	public boolean hasNext() {
		return this.index < this.size;
	}

	@Override
	public T next() {
		if (this.index < 0 || this.index >= this.size)
			throw new NoSuchElementException("Out of bound for iteration");
		//
		this.index++;
		//
		int i = this.offset + (this.index * SIZEOF_INT);
		int i2 = byteBuffer.getInt(i);
		if (i2 == 0)
			return null;
		//
		try {
			if (this.clazz != null) {
				T flattenable = this.clazz.newInstance();
				flattenable.initFromByteBuffer(byteBuffer, i + i2);
				return flattenable;
			} else if (this.resolver != null) {
				T flattenable = this.resolver.newInstance();
				flattenable.initFromByteBuffer(byteBuffer, i + i2);
				return flattenable;
			} else {
	            throw new RuntimeException("Either clazz or resolver should be provided");
	        }
			
		} catch (Throwable e) {
			throw new RuntimeException("Not able to create flattenable", e);
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
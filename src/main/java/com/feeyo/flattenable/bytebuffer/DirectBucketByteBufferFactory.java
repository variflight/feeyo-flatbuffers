package com.feeyo.flattenable.bytebuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

import com.feeyo.buffer.BufferPool;
import com.feeyo.buffer.bucket.BucketBufferPool;

public class DirectBucketByteBufferFactory extends AbstractByteBufferFactory {
	//
	private final BufferPool bufferPool;
    
    public DirectBucketByteBufferFactory(long initialCapacity, long maxCapacity, int[] chunkSizes) {
    	this.bufferPool = new BucketBufferPool(initialCapacity, maxCapacity, chunkSizes, ByteOrder.LITTLE_ENDIAN);
    }

	@Override
	public ByteBuffer newByteBuffer(int capacity) {
		return bufferPool.allocate(capacity);
	}

	@Override
	public void releaseByteBuffer(ByteBuffer theBuf) {
		bufferPool.recycle(theBuf);
	}
	

	@Override
	public Map<String, Object> getStatistics() {
		return bufferPool.getStatistics();
	}
}

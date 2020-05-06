package com.feeyo.flattenable.bytebuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

import com.feeyo.buffer.BufferPool;
import com.feeyo.buffer.page.PageBufferPool;

/**
 * 基于堆外内存
 * 
 * @author zhuam
 *
 */
public class DirectPageByteBufferFactory extends AbstractByteBufferFactory {
	//
	private final BufferPool bufferPool;
    
    public DirectPageByteBufferFactory(long capacity, int chunkSize) {
    	this.bufferPool = new PageBufferPool(capacity, capacity, new int[]{ chunkSize }, ByteOrder.LITTLE_ENDIAN);
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

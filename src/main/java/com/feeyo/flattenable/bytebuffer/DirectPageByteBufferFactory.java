package com.feeyo.flattenable.bytebuffer;

import java.nio.ByteBuffer;
import java.util.Map;

import com.feeyo.flattenable.bytebuffer.page.Pages;
import com.google.common.collect.Maps;

/**
 * 基于堆外内存
 * 
 * @author zhuam
 *
 */
public class DirectPageByteBufferFactory extends AbstractByteBufferFactory {
	//
	private final Pages pages;
    
    public DirectPageByteBufferFactory(long capacity, int chunkSize) {
    	this.pages = new Pages(capacity, chunkSize);
    	this.pages.initialize();
    }

	@Override
	public ByteBuffer newByteBuffer(int capacity) {
		return pages.allocate(capacity);
	}

	@Override
	public void releaseByteBuffer(ByteBuffer theBuf) {
		pages.recycle(theBuf);
	}
	
	//
	public void destroy() {
		pages.destroy();
	}

	@Override
	public Map<String, Object> getStatistics() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("buffer.factory.capacity", pages.getCapacity());
		map.put("buffer.factory.usedSize", pages.getUsageSize());
		map.put("buffer.factory.usedCnt", pages.getUsageCount());
		map.put("buffer.factory.sharedCnt", pages.getSharedOptsCount());
		return map;
	}
}

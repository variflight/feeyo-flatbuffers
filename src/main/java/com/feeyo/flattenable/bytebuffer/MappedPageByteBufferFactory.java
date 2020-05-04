package com.feeyo.flattenable.bytebuffer;

import java.nio.ByteBuffer;
import java.util.Map;

import com.feeyo.flattenable.bytebuffer.page.PagesOfMmap;
import com.google.common.collect.Maps;

/**
 * 基于文件映射 ByteBuffer
 * 
 * @author zhuam
 */
public class MappedPageByteBufferFactory extends AbstractByteBufferFactory {
	//
	private PagesOfMmap pages;
	
	public MappedPageByteBufferFactory(String path, long capacity, int chunkSize) {
		this.pages = new PagesOfMmap(path, capacity, chunkSize);
		this.pages.initialize();
	}

	@Override
	public ByteBuffer newByteBuffer(int capacity) {
		return pages.allocate(capacity);
	}

	@Override
	public void releaseByteBuffer(ByteBuffer theBuf) {
		this.pages.recycle(theBuf);
	}
	
	public void destroy() {
		pages.destroy();
	}
	
	//
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
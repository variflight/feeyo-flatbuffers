package com.feeyo.flattenable.bytebuffer;

import java.nio.ByteBuffer;
import java.util.Map;

import com.feeyo.flattenable.bytebuffer.bucket.AbstractBucket;
import com.feeyo.flattenable.bytebuffer.bucket.BucketPool;
import com.google.common.collect.Maps;

public class DirectBucketByteBufferFactory extends AbstractByteBufferFactory {
	//
	private final BucketPool bucketPool;
    
    public DirectBucketByteBufferFactory(long initialCapacity, long maxCapacity, int[] chunkSizes) {
    	this.bucketPool = new BucketPool(initialCapacity, maxCapacity, chunkSizes);
    }

	@Override
	public ByteBuffer newByteBuffer(int capacity) {
		return bucketPool.allocate(capacity);
	}

	@Override
	public void releaseByteBuffer(ByteBuffer theBuf) {
		bucketPool.recycle(theBuf);
	}
	
	//
	public void destroy() {
		bucketPool.destroy();
	}


	@Override
	public Map<String, Object> getStatistics() {
		//
		Map<String, Object> map = Maps.newHashMap();
		map.put("buffer.factory.min", bucketPool.getMinBufferSize());
		map.put("buffer.factory.used", bucketPool.getUsedBufferSize());
		map.put("buffer.factory.max", bucketPool.getMaxBufferSize());
		//
		AbstractBucket[] buckets = bucketPool.buckets();
		for (AbstractBucket b: buckets) {
			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append(" chunkSize=").append( b.getChunkSize() ).append(",");
			sBuffer.append(" queueSize=").append( b.getQueueSize() ).append( ", " );
			sBuffer.append(" count=").append( b.getCount() ).append( ", " );
			sBuffer.append(" useCount=").append( b.getUsedCount() ).append( ", " );
			sBuffer.append(" shared=").append( b.getShared() );		
			//
			map.put("buffer.factory.bucket." + b.getChunkSize(),  sBuffer.toString());
		}
		return map;
	}
}

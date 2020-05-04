package com.feeyo.flattenable.bytebuffer.bucket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 堆外内存池
 */
public class BucketPool {
	
	private static Logger LOGGER = LoggerFactory.getLogger( BucketPool.class );
	//
	//
	protected long minBufferSize;
	protected long maxBufferSize;
	protected AtomicLong usedBufferSize = new AtomicLong(0); 
	//
	protected int minChunkSize = 0;
	protected int maxChunkSize = 0;
	//
	private List<AbstractBucket> _buckets;
	
	public BucketPool(long minBufferSize, long maxBufferSize, int[] chunkSizes) {
		//
		this.minBufferSize = minBufferSize;
		this.maxBufferSize = maxBufferSize;
		
		this._buckets = new ArrayList<AbstractBucket>();
		//
		// 平均分配初始化的桶size 
		Arrays.sort( chunkSizes );
		long bucketCapacity = minBufferSize / chunkSizes.length;
		for (int i = 0; i < chunkSizes.length; i++) {
			int chunkSize = chunkSizes[i];
			int chunkCount = (int) (bucketCapacity / chunkSize);
			
			//
			if ( minChunkSize == 0 || chunkSize < minChunkSize )
				minChunkSize = chunkSize;
			//
			if ( maxChunkSize == 0 || chunkSize > maxChunkSize )
				maxChunkSize = chunkSize;
			//
			AbstractBucket bucket = new ArrayBucket(this, chunkSize, chunkCount);
			this._buckets.add(i, bucket);
		}
		//
    	LOGGER.info("Buckets initialize, minBufferSize={}, maxBufferSize={} ", minBufferSize, maxBufferSize);
	}
	
	//根据size寻找 桶
	private AbstractBucket bucketFor(int size) {
		if (size > maxChunkSize)
			return null;
		
		//
		for(int i = 0; i < _buckets.size(); i++) {
			AbstractBucket bucket =  _buckets.get(i);
			if (bucket.getChunkSize() >= size)
				return bucket;
		}
		return null;
	}
	
	//TODO : debug err, TMD, add temp synchronized
	public ByteBuffer allocate(int size) {		
	    	
		ByteBuffer byteBuf = null;
		
		// 根据容量大小size定位到对应的桶Bucket
		AbstractBucket bucket = bucketFor(size);
		if ( bucket != null) {
			byteBuf = bucket.allocate();
			if (byteBuf != null) {
				byteBuf.order(ByteOrder.LITTLE_ENDIAN);
				return byteBuf;
			}
		}
		
		// 堆内
		if (byteBuf == null) {
			byteBuf =  ByteBuffer.allocate( size );
			byteBuf.order(ByteOrder.LITTLE_ENDIAN);
			//
			LOGGER.warn("Allocate heap buffer {}", size);
		}
		return byteBuf;

	}

	public void recycle(ByteBuffer buf) {
		if (buf == null || !buf.isDirect() ) 
			return;
      	//
		AbstractBucket bucket = bucketFor( buf.capacity() );
		if (bucket != null) {
			bucket.recycle( buf );
		} else {
			LOGGER.warn("Trying to put a buffer, not created by this pool! Will be just ignored");
		}
	}
	
	public void destroy() {
		for(AbstractBucket b: _buckets) {
			b.containerClear();
		}
	}

	public synchronized AbstractBucket[] buckets() {
		AbstractBucket[] tmp = new AbstractBucket[ _buckets.size() ];
		int i = 0;
		for(AbstractBucket b: _buckets) {
			tmp[i] = b;
			i++;
		}
		return tmp;
	}
	
	public long getMinBufferSize() {
		return minBufferSize;
	}

	public long getMaxBufferSize() {
		return maxBufferSize;
	}

	public AtomicLong getUsedBufferSize() {
		return usedBufferSize;
	}
}
package com.feeyo.flattenable.bytebuffer;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;


public class ByteBufferFactoryTest {
	
	@SuppressWarnings("restriction")
	public static void main(String[] args) {
		
        int chunkSize = 20 * 1024;
        long capacity = 1024 * 1024 * 1024L;
        AbstractByteBufferFactory byteBufferFactory = new DirectPageByteBufferFactory(capacity, chunkSize);
		
		
		int i = 0;
		int total = 0;
		int max = 0;
		int min = 0;
		int avg = 0;
		for(;;) {
			long t1 = System.currentTimeMillis();
			ByteBuffer buffer = byteBufferFactory.newByteBuffer( 18528 );
			System.out.println( buffer instanceof MappedByteBuffer );
			
			long t2 = System.currentTimeMillis();
			int diff = (int) (t2 - t1);
			if ( diff > max )
				max = diff;
			if ( diff < min )
				min = diff;
			//
			total += diff;
			if ( !(buffer instanceof sun.nio.ch.DirectBuffer)  ) {
				break;
			}
			i++;
		}
		avg = total / i;
		
		System.out.println("total=" +  total + ", max=" + max + ", min=" + min + ", avg=" + avg + ", i=" +i);
		
		
		byteBufferFactory = new MappedPageByteBufferFactory("/Users/zhuam/git/feeyo/feeyoflatbuffers/test", capacity, chunkSize);
		System.out.println( byteBufferFactory.newByteBuffer(1024) instanceof MappedByteBuffer );


	}

}

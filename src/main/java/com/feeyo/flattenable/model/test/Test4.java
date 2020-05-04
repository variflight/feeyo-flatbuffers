package com.feeyo.flattenable.model.test;

import java.nio.ByteBuffer;

import com.feeyo.flattenable.FlatBufferHelper;
import com.feeyo.flattenable.FlattenableHelper;
import com.google.flatbuffers.FlatBufferBuilder;

public class Test4 {
	
	public static void main(String[] args) {
		
		FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(128);
		
		short[] shorts = new short[] { 'a', 'b', 'z' };
		int x = FlattenableHelper.flattenToBuffer(flatBufferBuilder, shorts);
		
		//
		ByteBuffer buffer = flatBufferBuilder.dataBuffer();
		int position = FlatBufferHelper.getRootObjectPosition(buffer);
		
		
		short[] short2 = FlatBufferHelper.getShortArray(buffer, position, 0);
		
		System.out.println(" ---------------------------------- ");
		for(short s : short2) {
			System.out.println( (char)s + ", " + s );
		}
		
		
		
		
	}

}

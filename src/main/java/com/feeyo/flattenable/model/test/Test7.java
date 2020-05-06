package com.feeyo.flattenable.model.test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;

import com.feeyo.flattenable.model.BaseMutableFileModel;
import com.feeyo.flattenable.model.BaseMutableModel;
import com.google.flatbuffers.FlatBufferBuilder;

public class Test7 {
	
	//
	public static void main(String[] args) throws IOException {
		
//		int size = (int)(1024 * 1024 * 1024 * 1.9);
//		MappedByteBufferFactory byteBufferFactory = new MappedByteBufferFactory("/Users/zhuam/git/feeyo/feeyoflatbuffers/xx7.buf", size);
//		FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(128, byteBufferFactory);
//		
//		User u1 = new User();
//		u1.id = 198;
//		u1.name = "u1u2";
//		u1.flattenToBuffer(flatBufferBuilder);
//		int position = u1.flattenToFile(flatBufferBuilder, "/Users/zhuam/git/feeyo/feeyoflatbuffers/xxxxx7.flat");
//		
//		//
//		byteBufferFactory.close();
		
		//
		BaseMutableFileModel.FlatBufferFileLoader fileLoader = new BaseMutableFileModel.FlatBufferFileLoader();
		MappedByteBuffer byteBuffer = fileLoader.build("/Users/zhuam/git/feeyo/feeyoflatbuffers/xxxxx7.flat", 0, 36);
		byteBuffer.order( ByteOrder.LITTLE_ENDIAN );
		
		User u2 = new User();
		u2.initFromByteBuffer( byteBuffer );
		System.out.println( u2.id + ", " + u2.name);
		
		
	}

	
	public static class User extends BaseMutableFileModel{
		
		private int id;
		private String name;
		
		@Override
	    public void initFromByteBuffer(ByteBuffer buffer) {
			super.initFromByteBuffer(buffer);
			this.id = getInt(0);
			this.name = getString(1);
		}
		
		@Override
		public int flattenToBuffer(FlatBufferBuilder flatBufferBuilder) {
			int nameOffset = flatBufferBuilder.createString(name);
			flatBufferBuilder.startTable(2);
			flatBufferBuilder.addInt(0, id, 0);
			flatBufferBuilder.addOffset(1, nameOffset, 0);
			int end = flatBufferBuilder.endTable();
			flatBufferBuilder.finish(end);
			return end;
		}

	}
	
}

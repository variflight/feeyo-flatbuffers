package com.feeyo.flattenable.model.test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.feeyo.flattenable.FlatBufferHelper;
import com.feeyo.flattenable.FlattenableHelper;
import com.feeyo.flattenable.FlattenableIterator;
import com.feeyo.flattenable.model.BaseModel;
import com.google.flatbuffers.FlatBufferBuilder;

public class Test2 {

	public static void main(String[] args) {
		
		FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(128);
		//
		List<User> m1List = new ArrayList<>();
		m1List.add( new User(1, "x1") );
		m1List.add( new User(2, "x2中国") );
		m1List.add( new User(3, "x3") );
		m1List.add( new User(4, "x4美国") );
		m1List.add( new User(5, "x5") );
		m1List.add( new User(6, "x6") );
		
		int x = FlattenableHelper.flattenToBuffer(flatBufferBuilder, m1List);
		System.out.println("x=" + x);
		ByteBuffer buffer = flatBufferBuilder.dataBuffer();
		
		
		int position = FlatBufferHelper.getRootObjectPosition(buffer);
        int o = FlatBufferHelper.getChildObjectPosition(buffer, position, 0);
      //  int offset = o + 4;
        int offset = o;
        int size = FlatBufferHelper.getSize(buffer, o);
        //
        FlattenableIterator<User> iter = new FlattenableIterator<User>(buffer, offset, size, User.class);
        while( iter.hasNext() ) {
        	User m  = iter.next();
        	System.out.println( m.getId() + ", " +  m.getName() );
        }

	}

	public static class User extends BaseModel {

		private int id;
		private String name;
		
		public User(){
			//
		}

		public User(int id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public int getId() {
			return getInt(0);
		}
		
		public String getName() {
			return getString(1);
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

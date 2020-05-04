package com.feeyo.flattenable.model.test;

import java.nio.ByteBuffer;

import com.feeyo.flattenable.FlattenableHelper;
import com.feeyo.flattenable.model.BaseModel;
import com.google.flatbuffers.FlatBufferBuilder;

public class Test3 {
	
	public static void main(String[] args) {
		
		FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(128);
		//
		Address address = new Address(11, "合肥", new String[]{"长江西路", "上海路", "潜山路"}, new long[] { 9999911L, 22L, 33L, 44L, 55L } );
		//
		int x = FlattenableHelper.flattenToBuffer(flatBufferBuilder, address);
		System.out.println("x=" + x);
		ByteBuffer byteBuffer = flatBufferBuilder.dataBuffer();
		
		
		Address address2 = new Address();
		address2.initFromByteBuffer(byteBuffer);
		
		System.out.println( address2.getCityName() ) ;
		//
		for(String a: address2.getAddress() ) {
			System.out.println( a ) ;
		}

		//
		for(long d: address2.getDistances() ) {
			System.out.println( d ) ;
		}
	}

	public static class Address extends BaseModel {

		private int id;
		private String cityName;
		private String[] address;
		private long[] distances;
		
		public Address(){
			//
		}

		public Address(int id, String cityName, String[] address, long[] distances) {
			this.id = id;
			this.cityName = cityName;
			this.address = address;
			this.distances = distances;
		}
		
		public int getId() {
			return getInt(0);
		}
		
		public String getCityName() {
			return getString(1);
		}
		
		public String[] getAddress() {
			return getStringArray(position, 2);
		}

		public long[] getDistances() {
			return getLongArray(position, 3);
		}

		@Override
		public int flattenToBuffer(FlatBufferBuilder flatBufferBuilder) {
			int nameOffset = flatBufferBuilder.createString(cityName);
			int addressOffset = FlattenableHelper.createStringVector(flatBufferBuilder, address);
			int distancesOffset = FlattenableHelper.createLongVector(flatBufferBuilder, distances);
			
			flatBufferBuilder.startTable(4);
			flatBufferBuilder.addInt(0, id, 0);
			flatBufferBuilder.addOffset(1, nameOffset, 0);
			flatBufferBuilder.addOffset(2, addressOffset, 0);
			flatBufferBuilder.addOffset(3, distancesOffset, 0);
			int end = flatBufferBuilder.endTable();
			flatBufferBuilder.finish(end);
			return end;
		}

	}

}

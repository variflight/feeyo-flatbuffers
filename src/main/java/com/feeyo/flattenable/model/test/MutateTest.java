package com.feeyo.flattenable.model.test;

import java.nio.ByteBuffer;

import com.feeyo.flattenable.FlattenableHelper;
import com.feeyo.flattenable.NotMutatedException;
import com.feeyo.flattenable.model.BaseMutableModel;
import com.feeyo.flattenable.model.BaseMutableModel.Cloner;
import com.google.flatbuffers.FlatBufferBuilder;

public class MutateTest {
	
	//
	public static void main(String[] args) {
		
		FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(128);
		int x = Address.createAddress(flatBufferBuilder, 11, "合肥", new String[]{"长江西路", "上海路", "潜山路"}, new long[] { 9999911L, 22L, 33L, 44L, 55L } );
		System.out.println("x=" + x);
		ByteBuffer byteBuffer = flatBufferBuilder.dataBuffer();
		
		//
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
		
		boolean b1 = address2.putInt(0, 2222);
		boolean b2 = address2.putLong(3, 1, 959);
		boolean b3 = address2.putString(1, "南");	// fixed length
		System.out.println("b1=" + b1 + ",, " + address2.getId());
		System.out.println("b2=" + b2 + ",, " + address2.getDistances()[0] + ", "+ address2.getDistances()[1]);
		System.out.println("b3=" + b3 + ",, " + address2.getCityName());
		
		boolean b4 = address2.putString(1, "北京"); // fixed length
		System.out.println("b4=" + b4 + ",, " + address2.getCityName());
		
		try {
			boolean b5 = address2.putString(1, "美国洛杉矶"); // variable length
			System.out.println("b5=" + b5 + ",, " + address2.getCityName());
		} catch(NotMutatedException e) {
			//
			// deep clone
			FlatBufferBuilder flatBufferBuilder2 =  new FlatBufferBuilder(128);
			Address address3 = address2.clone( new Cloner<Address>() {
				@Override
				public Address deepClone(Address model) {
					
					int nameOffset = flatBufferBuilder2.createString( "美国洛杉矶" );
					int addressOffset = FlattenableHelper.createStringVector(flatBufferBuilder2, model.getAddress());
					int distancesOffset = FlattenableHelper.createLongVector(flatBufferBuilder2, model.getDistances());
					flatBufferBuilder2.startTable(4);
					flatBufferBuilder2.addInt(0, model.getId(), 0);
					flatBufferBuilder2.addOffset(1, nameOffset, 0);
					flatBufferBuilder2.addOffset(2, addressOffset, 0);
					flatBufferBuilder2.addOffset(3, distancesOffset, 0);
					int end = flatBufferBuilder2.endTable();
					flatBufferBuilder2.finish(end);
					//
					Address addr = new Address();
					addr.initFromByteBuffer( flatBufferBuilder2.dataBuffer() );
					return addr;
				}
			});
			
			System.out.println("-------------------- deep clone -------------------- ");
			System.out.println( address3.getId() + ",, " + address3.getCityName() + ",, " +  address3.getDistances()[1] );
			
		}
	}

	public static class Address extends BaseMutableModel {

		public Address() {}

		public static int createAddress(FlatBufferBuilder flatBufferBuilder, int id, String cityName, String[] address,
				long[] distances) {
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
			return createAddress(flatBufferBuilder, getId(), getCityName(), getAddress(), getDistances());
		}

	}

}

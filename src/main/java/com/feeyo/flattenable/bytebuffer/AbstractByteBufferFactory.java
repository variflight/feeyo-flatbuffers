package com.feeyo.flattenable.bytebuffer;

import java.util.Map;

import com.google.flatbuffers.FlatBufferBuilder;

public abstract class AbstractByteBufferFactory extends FlatBufferBuilder.ByteBufferFactory {
	//
	public abstract Map<String, Object> getStatistics();

}

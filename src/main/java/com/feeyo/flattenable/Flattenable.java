package com.feeyo.flattenable;

import com.google.flatbuffers.FlatBufferBuilder;

import java.nio.ByteBuffer;

public interface Flattenable {
    //
	void initFromByteBuffer(ByteBuffer byteBuffer, int position);
    void initFromByteBuffer(ByteBuffer byteBuffer);
    int getPositionInFlatBuffer();
    ByteBuffer getByteBuffer();
    byte[] toByteArray();
    
    //
    int flattenToBuffer(FlatBufferBuilder flatBufferBuilder);
}
package com.feeyo.flattenable;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;

import com.google.flatbuffers.FlatBufferBuilder;

public interface IFlattenableMap<K, V extends Flattenable> {
	//
	public void initFromByteBuffer(ByteBuffer buffer);
	public void initFromFlatBufferBuilder(FlatBufferBuilder flatBufferBuilder, Map<K, V> map);
	public ByteBuffer getByteBuffer();
	//
	public Flattenable get(K key);
	public boolean containsKey(K key);
	public Collection<K> keys();
	public Collection<V> values();
	public int size();

}

package com.feeyo.flattenable;

import static com.google.flatbuffers.Constants.SIZEOF_INT;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.flatbuffers.FlatBufferBuilder;

public class FlattenableMap<V extends Flattenable> implements IFlattenableMap<String, V> {
	//
	private Class<V> clazz;
	private final VirtualFlattenableResolver<V> resolver;

	private ByteBuffer buffer;
	//
	private Map<String, Integer> mapOfKeys = new HashMap<>();
	private int keyPosition;
	private int valuePosition;
	//
	private int size;

	public FlattenableMap(Class<V> clazz) {
		this.clazz = clazz;
		this.resolver = null;
	}
	
	public FlattenableMap(VirtualFlattenableResolver<V> resolver) {
		this.resolver = resolver;
		this.clazz = null;
	}

	@Override
	public void initFromByteBuffer(ByteBuffer buffer) {
		//
		this.buffer = buffer;
		//
		int rootPosition = FlatBufferHelper.getRootObjectPosition(buffer);
		int fieldPosition = FlatBufferHelper.getChildObjectPosition(buffer, rootPosition, 0);
		if (fieldPosition != 0) {
			this.keyPosition = FlatBufferHelper.getChildObjectPosition(buffer, fieldPosition, 0);
			this.valuePosition = FlatBufferHelper.getChildObjectPosition(buffer, fieldPosition, 1);
			this.size = FlatBufferHelper.getSize(buffer, keyPosition);
			//
			for (int i = 0; i < size; i++) {
				mapOfKeys.put( getKeyByIndex(i), i );
			}
		}
	}

	@Override
	public void initFromFlatBufferBuilder(FlatBufferBuilder flatBufferBuilder, Map<String, V> map) {
		FlattenableHelper.flattenToBuffer(flatBufferBuilder, map);
		initFromByteBuffer(flatBufferBuilder.dataBuffer());
	}
	
	@Override
	public ByteBuffer getByteBuffer() {
		return buffer;
	}

	@Override
	public V get(String key) {

		Integer index = mapOfKeys.get(key);
		if (index == null)
			return null;
		//
		return getValueByIndex(index);
	}

	@Override
	public boolean containsKey(String key) {
		return mapOfKeys.containsKey(key);
	}

	@Override
	public Collection<String> keys() {
		return mapOfKeys.keySet();
	}

	@Override
	public Collection<V> values() {
		Collection<V> values = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			V e = getValueByIndex(i);
			values.add(e);
		}
		return values;
	}
	
	private String getKeyByIndex(int index) {
		int off = (index * SIZEOF_INT) +  keyPosition + SIZEOF_INT;
		int pos = buffer.getInt(off);
		return FlatBufferHelper.getString(buffer, off + pos);
	}
	
	//
	private V getValueByIndex(int index) {
		int off = (index * SIZEOF_INT) + valuePosition + SIZEOF_INT;
		int pos = buffer.getInt(off);
		//
		try {
			//
			if (this.clazz != null) {
				//
				V flattenable = clazz.newInstance();
				flattenable.initFromByteBuffer(buffer, off + pos);
				return flattenable;
			//
			} else if (this.resolver != null) {
				//
				V flattenable = this.resolver.newInstance();
				flattenable.initFromByteBuffer(buffer, off + pos);
				return flattenable;
			//
			} else {
				throw new RuntimeException("Either clazz or resolver should be provided");
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Not able to create flattenable: ", e);
		}
	}

	@Override
	public int size() {
		return size;
	}
}
package com.feeyo.flattenable.model;


import java.nio.ByteBuffer;
import java.util.Iterator;

import com.feeyo.flattenable.Flattenable;
import com.feeyo.flattenable.FlattenableList;
import com.feeyo.flattenable.VirtualFlattenableResolver;

/**
 * 支持模型内 ByteBuffer的压缩 & 解压
 * 
 * @author zhuam
 *
 */
public abstract class BaseCompressibleModel extends BaseMutableModel {
	//
	public static final byte UNCOMPRESSED = 0;
	public static final byte COMPRESSED = 1;
	
	protected byte isCompressed = UNCOMPRESSED;
	//
	public boolean isCompressed() {
		return isCompressed == COMPRESSED;
	}
	
	public void setCompressed(byte isCompressed) {
		this.isCompressed = isCompressed;
	}

	//
	public void setBuffer(ByteBuffer buffer) {
		this.buffer = buffer;
	}
	
	//
	private void checkCompressed() {
		if ( isCompressed == COMPRESSED )
			throw new UndecompressedException("The buffer block of the model is not decompressed");
	}

	//
	// -------------------------------------------------------------------------------------
	//		Write method
	//
	@Override
	public boolean putByte(int index, byte value) {
		checkCompressed();
		return super.putByte(index, value);
	}

	@Override
	public boolean putShort(int index, short value) {
		checkCompressed();
		return super.putShort(index, value);
	}

	@Override
	public boolean putInt(int index, int value) {
		checkCompressed();
		return super.putInt(index, value);
	}

	@Override
	public boolean putLong(int index, long value) {
		checkCompressed();
		return super.putLong(index, value);
	}

	@Override
	public boolean putFloat(int index, float value) {
		checkCompressed();
		return super.putFloat(index, value);
	}

	@Override
	public boolean putDouble(int index, double value) {
		checkCompressed();
		return super.putDouble(index, value);
	}

	@Override
	public boolean putBoolean(int index, boolean value) {
		checkCompressed();
		return super.putBoolean(index, value);
	}

	@Override
	public boolean putString(int index, String value) {
		checkCompressed();
		return super.putString(index, value);
	}
	
	@Override
	public boolean putBytes(int index, byte[] value) {
		checkCompressed();
		return super.putBytes(index, value);
	}

	//
	// mutate vector
	//
	@Override
	public boolean putByte(int index, int vindex, byte value) {
		checkCompressed();
		return super.putByte(index, vindex, value);
	}

	@Override
	public boolean putShort(int index, int vindex, short value) {
		checkCompressed();
		return super.putShort(index, vindex, value);
	}

	@Override
	public boolean putInt(int index, int vindex, int value) {
		checkCompressed();
		return super.putInt(index, vindex, value);
	}

	@Override
	public boolean putLong(int index, int vindex, long value) {
		checkCompressed();
		return super.putLong(index, vindex, value);
	}

	@Override
	public boolean putFloat(int index, int vindex, float value) {
		checkCompressed();
		return super.putFloat(index, vindex, value);
	}

	@Override
	public boolean putDouble(int index, int vindex, double value) {
		checkCompressed();
		return super.putDouble(index, vindex, value);
	}

	@Override
	public boolean putBoolean(int index, int vindex, boolean value) {
		checkCompressed();
		return super.putBoolean(index, vindex, value);
	}
	
	//
	// -------------------------------------------------------------------------------------
	//		Read method
	//
	
	@Override
	protected byte getByte(int index) {
		checkCompressed();
		return super.getByte(index);
	}

	@Override
    protected short getShort(int index) {
		checkCompressed();
       return super.getShort(index);
    }
    
	@Override
    protected int getInt(int index) {
		checkCompressed();
    	return super.getInt(index);
    }
    
	@Override
    protected String getString(int index) {
		checkCompressed();
    	return super.getString(index);
    }

	@Override
    protected long getLong(int index) {
		checkCompressed();
    	return super.getLong(index);
    }
    
	@Override
    protected float getFloat(int index) {
		checkCompressed();
    	return super.getFloat(index);
    }
    
    @Override
    protected double getDouble(int index) {
    	checkCompressed();
    	return super.getDouble(index);
    }

    @Override
    protected byte[] getBytes(int index) {
    	checkCompressed();
        return super.getBytes(index);
    }

    @Override
    protected <T extends Flattenable> T getFlattenable(int index, Class<T> clazz) {
    	checkCompressed();
        return super.getFlattenable(index, clazz);
    }
    
    @Override
    protected <T extends Flattenable> T getFlattenable(int index, VirtualFlattenableResolver<T> resolver) {
    	checkCompressed();
        return super.getFlattenable(index, resolver);
    }
    
    @Override
    protected <T extends Flattenable> FlattenableList<T> getFlattenableList(int index, VirtualFlattenableResolver<T> resolver) {
    	checkCompressed();
        return super.getFlattenableList(index, resolver);
    }
    
    @Override
    protected <T extends Flattenable> Iterator<T> newFlattenableIterator(int position, int index, Class<T> clazz) {
    	checkCompressed();
        return super.newFlattenableIterator(position, index, clazz);
    }
    
    @Override
    protected <T extends Flattenable> Iterator<T> newFlattenableIterator(int position, int index, VirtualFlattenableResolver<T> resolver) {
    	checkCompressed();
        return super.newFlattenableIterator(position, index, resolver);
    }
    
    @Override
    protected int[] getIntArray(int position, int index) {
    	checkCompressed();
    	return super.getIntArray(position, index);
	}
    
    @Override
    protected long[] getLongArray(int position, int index) {
    	checkCompressed();
    	return super.getLongArray(position, index);
    }
    
    @Override
    protected float[] getFloatArray(int position, int index) {
    	checkCompressed();
    	return super.getFloatArray(position, index);
    }
    
    @Override
    protected double[] getDoubleArray(int position, int index) {
    	checkCompressed();
    	return super.getDoubleArray(position, index);
    }    
    
    @Override
    protected String[] getStringArray(int position, int index) {
    	checkCompressed();
    	return super.getStringArray(position, index);
    }
    
    @Override
	protected boolean isByteArrayEquals(int index, byte[] value) {
    	checkCompressed();
		return super.isByteArrayEquals(index, value);
	}
    
    @Override
	protected boolean isPrefixByteArrayMatch(int index, byte[] prefix) {
    	checkCompressed();
		return super.isPrefixByteArrayMatch(index, prefix);
	}
	
	//
	@Override
	public byte[] toByteArray() {
		checkCompressed();
		return super.toByteArray();
	}
}

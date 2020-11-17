package com.feeyo.flattenable.model;

import java.nio.ByteBuffer;
import java.util.Iterator;

import com.feeyo.flattenable.FlatBufferHelper;
import com.feeyo.flattenable.Flattenable;
import com.feeyo.flattenable.FlattenableIterator;
import com.feeyo.flattenable.FlattenableList;
import com.feeyo.flattenable.VirtualFlattenableResolver;

import static com.google.flatbuffers.Constants.SIZEOF_INT;

public abstract class BaseModel implements Flattenable {
	//
    protected ByteBuffer buffer;
    protected int position;

    @Override
    public void initFromByteBuffer(ByteBuffer buffer, int position) {
        this.buffer = buffer;
        this.position = position;
    }

    @Override
    public void initFromByteBuffer(ByteBuffer buffer) {
        int position = buffer.position();
        initFromByteBuffer(buffer, position + buffer.getInt(position));
    }
    
    //
    public int getPositionInFlatBuffer() {
    	return this.position;
    }
    
    @Override
    public ByteBuffer getByteBuffer() {
        return buffer;
    }
    
    //
	public void reset() {
		this.buffer = null;
		this.position = 0;
	}
    
	protected byte getByte(int index) {
		try {
			return FlatBufferHelper.getByte(buffer, position, index, (byte) 0);
		} catch (IndexOutOfBoundsException e) {
			throw e;
		}
	}

    protected short getShort(int index) {
        try {
            return FlatBufferHelper.getShort(buffer, position, index, (short)0);
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }
    
    //
    protected int getInt(int index) {
    	try {
    		return FlatBufferHelper.getInt(buffer, position, index, 0);
    	} catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }
    
    protected String getString(int index) {
    	try {
    		return FlatBufferHelper.getString(buffer, position, index);
    	} catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }

    protected long getLong(int index) {
    	try {
    		return FlatBufferHelper.getLong(buffer, position, index, 0L);
    	} catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }
    
    protected double getFloat(int index) {
    	try {
    		return FlatBufferHelper.getFloat(buffer, position, index, 0F);
    	} catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }
    
    protected double getDouble(int index) {
    	try {
    		return FlatBufferHelper.getDouble(buffer, position, index, 0D);
    	} catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }

    //
    protected byte[] getBytes(int index) {
        try {
            return FlatBufferHelper.getByteArray(buffer, position, index);
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }
    
    //
    protected void getBytes(int index, byte[] value) {
        try {
            FlatBufferHelper.getByteArray(buffer, position, index, value);
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }

    protected <T extends Flattenable> T getFlattenable(int index, Class<T> clazz) {
        try {
        	return FlatBufferHelper.getFlattenable(buffer, position, index, clazz);
        } catch (Throwable e) {
            throw new RuntimeException("Not able to create flattenable", e);
        }
    }
    
    protected <T extends Flattenable> T getFlattenable(int index, VirtualFlattenableResolver<T> resolver) {
        try {
        	return FlatBufferHelper.getFlattenable(buffer, position, index, resolver);
        } catch (Throwable e) {
            throw new RuntimeException("Not able to create flattenable", e);
        }
    }
    
    //
    protected <T extends Flattenable> FlattenableList<T> getFlattenableList(int index, VirtualFlattenableResolver<T> resolver) {
        try {
            int o = FlatBufferHelper.getChildObjectPosition(buffer, position, index);
            if (o == 0) 
                return null;
            //
            int off = o + SIZEOF_INT;
            int size = FlatBufferHelper.getSize(buffer, o);
            //
            FlattenableList<T> list =  new FlattenableList<>( resolver);
            list.initFromByteBuffer(buffer, off, size);
            return list;
            //
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }
    
    protected <T extends Flattenable> Iterator<T> newFlattenableIterator(int position, int index, Class<T> clazz) {
        try {
            int o = FlatBufferHelper.getChildObjectPosition(buffer, position, index);
            if (o == 0) 
                return null;
            //
            int off = o + SIZEOF_INT;
            int size = FlatBufferHelper.getSize(buffer, o);
            return new FlattenableIterator<>(buffer, off, size, clazz);
            //
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }
    
    protected <T extends Flattenable> Iterator<T> newFlattenableIterator(int position, int index, VirtualFlattenableResolver<T> resolver) {
        try {
            int o = FlatBufferHelper.getChildObjectPosition(buffer, position, index);
            if (o == 0) 
                return null;
            //
            int off = o + SIZEOF_INT;
            int size = FlatBufferHelper.getSize(buffer, o);
            return new FlattenableIterator<>(buffer, off, size, resolver);
            //
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }
    
    protected int[] getIntArray(int position, int index) {
		try {
			return FlatBufferHelper.getIntArray(buffer, position, index);
		} catch (IndexOutOfBoundsException e) {
			throw e;
		}
	}
    
    protected long[] getLongArray(int position, int index) {
    	try {
			return FlatBufferHelper.getLongArray(buffer, position, index);
		} catch (IndexOutOfBoundsException e) {
			throw e;
		}
    }
    
    protected float[] getFloatArray(int position, int index) {
    	try {
			return FlatBufferHelper.getFloatArray(buffer, position, index);
		} catch (IndexOutOfBoundsException e) {
			throw e;
		}
    }
    
    protected double[] getDoubleArray(int position, int index) {
    	try {
			return FlatBufferHelper.getDoubleArray(buffer, position, index);
		} catch (IndexOutOfBoundsException e) {
			throw e;
		}
    }    
    
    protected String[] getStringArray(int position, int index) {
    	try {
			return FlatBufferHelper.getStringArray(buffer, position, index);
		} catch (IndexOutOfBoundsException e) {
			throw e;
		}
    }
    
    //
	protected boolean isByteArrayEquals(int index, byte[] value) {
		return FlatBufferHelper.isByteArrayEquals(buffer, position, index, value);
	}
    
	//
	protected boolean isPrefixByteArrayMatch(int index, byte[] prefix) {
		return FlatBufferHelper.isPrefixByteArrayMatch(buffer, position, index, prefix);
	}
	
	//
	@Override
	public byte[] toByteArray() {
	    // BaseModel 中的position并不是实际 ByteBuffer 的 position
	    int position = buffer.position();
	    int capacity = buffer.capacity();
        byte[] oriValue = new byte[capacity - position];
        // 不修改 ByteBuffer 的 position
        for (int i = 0; i < oriValue.length; i++) 
            oriValue[i] = buffer.get(position + i);
        return oriValue;
	}
}
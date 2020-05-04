package com.feeyo.flattenable.model.test2;

import com.feeyo.flattenable.model.BaseModel;
import com.google.flatbuffers.FlatBufferBuilder;

public class Entry extends BaseModel {
    private int index;
    private int term;
    private byte[] data;

    @Override
    public int flattenToBuffer(FlatBufferBuilder flatBufferBuilder) {
        return createEntry(flatBufferBuilder, getIndex(), getTerm(), getData());
    }

    public static int createEntry(FlatBufferBuilder flatBufferBuilder, int index, int term, byte[] data) {
        int dataOffset = flatBufferBuilder.createByteVector(data);

        flatBufferBuilder.startTable(3);
        flatBufferBuilder.addInt(0, index, 0);
        flatBufferBuilder.addInt(1, term, 0);
        flatBufferBuilder.addOffset(2, dataOffset, 0);
        int end = flatBufferBuilder.endTable();
        flatBufferBuilder.finish(end);
        return end;
    }

    public int getIndex() {
        if (index == 0) {
            index = getInt(0);
        }
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTerm() {
        if (term == 0) {
            term = getInt(1);
        }
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public byte[] getData() {
        if (data == null) {
            data = getBytes(2);
        }
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
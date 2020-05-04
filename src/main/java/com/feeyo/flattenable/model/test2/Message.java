package com.feeyo.flattenable.model.test2;

import com.feeyo.flattenable.model.BaseModel;
import com.google.flatbuffers.FlatBufferBuilder;

public class Message extends BaseModel {
    private int from;
    private int to;
    private Entry entry;
    // private EntryList entryList;
    private int term;

    @Override
    public int flattenToBuffer(FlatBufferBuilder flatBufferBuilder) {
        // return createMessage(flatBufferBuilder, getFrom(), getTo(), getEntryList());
        Entry entry = getEntry();
        int entryOffset = entry.flattenToBuffer(flatBufferBuilder);
        return createMessage(flatBufferBuilder, getFrom(), getTo(), entryOffset, getTerm());
    }

    public static int createMessage(FlatBufferBuilder flatBufferBuilder, int from, int to, int entryOffset, int term) {
        flatBufferBuilder.startTable(4);
        flatBufferBuilder.addInt(0, from, 0);
        flatBufferBuilder.addInt(1, to, 0);
        flatBufferBuilder.addOffset(2, entryOffset, 0);
        flatBufferBuilder.addInt(3, term, 0);
        int end = flatBufferBuilder.endTable();
        flatBufferBuilder.finish(end);
        return end;
    }

    // public static int createMessage(FlatBufferBuilder flatBufferBuilder, int from, int to, int entryListOffset) {
    //     flatBufferBuilder.startTable(3);
    //     flatBufferBuilder.addInt(0, from, 0);
    //     flatBufferBuilder.addInt(1, to, 0);
    //     flatBufferBuilder.addOffset(2, entryListOffset, 0);
    //     int end = flatBufferBuilder.endTable();
    //     flatBufferBuilder.finish(end);
    //     return end;
    // }

    public int getFrom() {
        if (from == 0) {
            from = getInt(0);
        }
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        if (to == 0) {
            to = getInt(1);
        }
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public Entry getEntry() {
        if (entry == null) {
            entry = getFlattenable(2, Entry.class);
        }
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    // public EntryList getEntryList() {
    //     if (entryList == null) {
    //         entryList = (EntryList) getModelList(2);
    //     }
    //     return entryList;
    // }
    //
    // public void setEntryList(EntryList entryList) {
    //     this.entryList = entryList;
    // }

    public int getTerm() {
        if (term == 0) {
            term = getInt(3);
        }
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }
}
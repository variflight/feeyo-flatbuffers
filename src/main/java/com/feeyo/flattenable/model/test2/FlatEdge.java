package com.feeyo.flattenable.model.test2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feeyo.flattenable.model.JsonModel;
import com.google.flatbuffers.FlatBufferBuilder;

public class FlatEdge extends JsonModel {

    private long id;
    private int startVertex;
    private int endVertex;
    private long depTime;
    private long arrTime;
    private int type;
    private int org;
    private int dst;
    private int numOffset;
    private double distance;
    private int edgeType;

    public FlatEdge() {}

    public static int createFlatEdge(FlatBufferBuilder flatBufferBuilder, long id, int startVertex, int endVertex, long depTime, //
                                     long arrTime, int type, int org, int dst, int numOffset, double distance, int edgeType) {
        flatBufferBuilder.startTable(11);
        flatBufferBuilder.addLong(0, id, 0);
        flatBufferBuilder.addInt(1, startVertex, 0);
        flatBufferBuilder.addInt(2, endVertex, 0);
        flatBufferBuilder.addLong(3, depTime, 0);
        flatBufferBuilder.addLong(4, arrTime, 0);
        flatBufferBuilder.addInt(5, type, 0);
        flatBufferBuilder.addInt(6, org, 0);
        flatBufferBuilder.addInt(7, dst, 0);
        flatBufferBuilder.addInt(8, numOffset, 0);
        flatBufferBuilder.addDouble(9, distance, 0);
        flatBufferBuilder.addInt(10, edgeType, 0);
        int end = flatBufferBuilder.endTable();
        flatBufferBuilder.finish(end);
        return end;
    }

    @Override
    public int flattenToBuffer(FlatBufferBuilder flatBufferBuilder) {
        return FlatEdge.createFlatEdge(flatBufferBuilder, getId(), getStartVertex(), getEndVertex(), getDepTime(), getArrTime(), getType(),
                getOrg(), getDst(), getNumOffset(), getDistance(), getEdgeType());
    }

    @Override
    public void initFromJSON(JSONObject jsonObject) {
    	throw new UnsupportedOperationException("initFromJSON is not supported");
    }
    
    //
    @Override
    public void initFromJSONString(String jsonString) {
        FlatEdge other = JSON.parseObject(jsonString, FlatEdge.class);
        this.id = other.id;
        this.startVertex = other.startVertex;
        this.endVertex = other.endVertex;
        this.depTime = other.depTime;
        this.arrTime = other.arrTime;
        this.type = other.type;
        this.org = other.org;
        this.dst = other.dst;
        this.numOffset = other.numOffset;
        this.distance = other.distance;
        this.edgeType = other.edgeType;
    }
    
    @Override
    public JSONObject toJSON() {
    	throw new UnsupportedOperationException("flattenToJSON is not supported");
    }

    @Override
    public String toJSONString() {
        return JSON.toJSONString(this, FEATURES);
    }
    
    
    public long getId() {
        if (id == 0) {
            id = getLong(0);
        }
        return id;
    }

    public int getStartVertex() {
        if (startVertex == 0) {
            startVertex = getInt(1);
        }
        return startVertex;
    }

    public int getEndVertex() {
        if (endVertex == 0) {
            endVertex = getInt(2);
        }
        return endVertex;
    }

    public long getDepTime() {
        if (depTime == 0) {
            depTime = getLong(3);
        }
        return depTime;
    }

    public long getArrTime() {
        if (arrTime == 0) {
            arrTime = getLong(4);
        }
        return arrTime;
    }

    public int getType() {
        if (type == 0) {
            type = getInt(5);
        }
        return type;
    }

    public int getOrg() {
        if (org == 0) {
            org = getInt(6);
        }
        return org;
    }

    public int getDst() {
        if (dst == 0) {
            dst = getInt(7);
        }
        return dst;
    }

    public int getNumOffset() {
        if (numOffset == 0) {
            numOffset = getInt(8);
        }
        return numOffset;
    }

    public double getDistance() {
        if (distance == 0) {
            distance = getDouble(9);
        }
        return distance;
    }

    public int getEdgeType() {
        if (edgeType == 0) {
            edgeType = getInt(10);
        }
        return edgeType;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStartVertex(int startVertex) {
        this.startVertex = startVertex;
    }

    public void setEndVertex(int endVertex) {
        this.endVertex = endVertex;
    }

    public void setDepTime(long depTime) {
        this.depTime = depTime;
    }

    public void setArrTime(long arrTime) {
        this.arrTime = arrTime;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setOrg(int org) {
        this.org = org;
    }

    public void setDst(int dst) {
        this.dst = dst;
    }

    public void setNumOffset(int numOffset) {
        this.numOffset = numOffset;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setEdgeType(int edgeType) {
        this.edgeType = edgeType;
    }
}
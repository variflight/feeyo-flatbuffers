package com.feeyo.flattenable.model.test2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feeyo.flattenable.model.JsonModel;
import com.google.flatbuffers.FlatBufferBuilder;

public class FlatVertex extends JsonModel {
	//
    private int id;
    private String name;
    private String code;
    private String namePy;
    private double lng;
    private double lat;
    private int bizCode;
    private String belong;

    public FlatVertex() {}

    //
    public static int createFlatVertex(
    		FlatBufferBuilder flatBufferBuilder, //
    		int id, String name, String code, //
            String namePy, double lng, double lat, int bizCode, String belong) {
    	//
        int nameOffset = flatBufferBuilder.createString(name);
        int codeOffset = flatBufferBuilder.createString(code);
        int namePyOffset = flatBufferBuilder.createString(namePy);
        int belongOffset = flatBufferBuilder.createString(belong);

        flatBufferBuilder.startTable(8);
        flatBufferBuilder.addInt(0, id, 0);
        flatBufferBuilder.addOffset(1, nameOffset, 0);
        flatBufferBuilder.addOffset(2, codeOffset, 0);
        flatBufferBuilder.addOffset(3, namePyOffset, 0);
        flatBufferBuilder.addDouble(4, lng, 0);
        flatBufferBuilder.addDouble(5, lat, 0);
        flatBufferBuilder.addInt(6, bizCode, 0);
        flatBufferBuilder.addOffset(7, belongOffset, 0);
        int end = flatBufferBuilder.endTable();
        flatBufferBuilder.finish(end);
        return end;
    }

    //
    @Override
    public int flattenToBuffer(FlatBufferBuilder flatBufferBuilder) {
        return FlatVertex.createFlatVertex(flatBufferBuilder, getId(), getName(), getCode(), getNamePy(), getLng(), getLat(), getBizCode(), getBelong());
    }
    

    @Override
    public void initFromJSON(JSONObject jsonObject) {
    	throw new UnsupportedOperationException("initFromJSON is not supported");
    }
    
    @Override
    public void initFromJSONString(String jsonString) {
        FlatVertex other = JSON.parseObject(jsonString, FlatVertex.class);
        this.id = other.id;
        this.name = other.name;
        this.code = other.code;
        this.namePy = other.namePy;
        this.lng = other.lng;
        this.lat = other.lat;
        this.bizCode = other.bizCode;
        this.belong = other.belong;
    }

    @Override
    public JSONObject toJSON() {
    	throw new UnsupportedOperationException("flattenToJSON is not supported");
    }

    @Override
    public String toJSONString() {
        return JSON.toJSONString(this, FEATURES);
    }

    public int getId() {
        if (id == 0) {
            id = getInt(0);
        }
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        if (name == null) {
            name = getString(1);
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        if (code == null) {
            code = getString(2);
        }
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNamePy() {
        if (namePy == null) {
            namePy = getString(3);
        }
        return namePy;
    }

    public void setNamePy(String namePy) {
        this.namePy = namePy;
    }

    public double getLng() {
        if (lng == 0) {
            lng = getDouble(4);
        }
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        if (lat == 0) {
            lat = getDouble(5);
        }
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getBizCode() {
        if (bizCode == 0) {
            bizCode = getInt(6);
        }
        return bizCode;
    }

    public void setBizCode(int bizCode) {
        this.bizCode = bizCode;
    }

    public String getBelong() {
        if (belong == null) {
            belong = getString(7);
        }
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }
}
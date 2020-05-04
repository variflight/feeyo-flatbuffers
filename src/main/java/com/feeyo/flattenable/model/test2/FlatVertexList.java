package com.feeyo.flattenable.model.test2;

import com.feeyo.flattenable.FlattenableList;

public class FlatVertexList extends FlattenableList<FlatVertex> {

    public FlatVertexList(Class<FlatVertex> clazz) {
		super(clazz);
	}

//    @Override
//    public String flattenToJson() {
//        int length = length();
//        FlatVertex[] vertices = new FlatVertex[length];
//        for (int i = 0; i < length; i++) {
//            vertices[i] = (FlatVertex) get(i);
//        }
//        return JSON.toJSONString(vertices);
//    }
}
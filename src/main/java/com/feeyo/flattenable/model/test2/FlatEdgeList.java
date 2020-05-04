package com.feeyo.flattenable.model.test2;

import com.feeyo.flattenable.FlattenableList;

public class FlatEdgeList extends FlattenableList<FlatEdge> {

    public FlatEdgeList(Class<FlatEdge> clazz) {
		super(clazz);
	}


//    @Override
//    public String flattenToJson() {
//        int length = length();
//        FlatEdge[] flatEdges = new FlatEdge[length];
//        for (int i = 0; i < length; i++) {
//            flatEdges[i] = (FlatEdge) get(i);
//        }
//        return JSON.toJSONString(flatEdges);
//    }
}
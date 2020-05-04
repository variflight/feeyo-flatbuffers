package com.feeyo.flattenable.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public abstract class JsonModel extends BaseMutableModel {
	//
	public abstract void initFromJSON(JSONObject jsonObject);
	public abstract void initFromJSONString(String jsonString);
	//
	public abstract JSONObject toJSON();
	public abstract String toJSONString();

	//
	protected static final SerializerFeature[] FEATURES = new SerializerFeature[] {
	        SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.IgnoreNonFieldGetter
	};
}
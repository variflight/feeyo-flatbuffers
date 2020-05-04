package com.feeyo.flattenable.model;

import com.google.protobuf.MessageLite;

public abstract class ProtobufModel<M extends MessageLite> extends BaseMutableModel {
	//
	public abstract void initFromMessage(M message);
	//
	public abstract M toMessage();
	
}

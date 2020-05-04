package com.feeyo.flattenable;

public interface VirtualFlattenableResolver<T extends Flattenable> {
	//
	T newInstance();
}

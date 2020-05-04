package com.feeyo.flattenable.model.test2;

import com.feeyo.flattenable.FlattenableList;

public class EntryList extends FlattenableList<Entry> {

	public EntryList(Class<Entry> clazz) {
		super(clazz);
	}

}
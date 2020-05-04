package com.feeyo.flattenable.model.test2;

import com.feeyo.flattenable.model.BaseModel;
import com.google.flatbuffers.FlatBufferBuilder;

public class Error extends BaseModel {
    private int errorCode;
    private String errorMessage;

    @Override
    public int flattenToBuffer(FlatBufferBuilder flatBufferBuilder) {
        return createError(flatBufferBuilder, getErrorCode(), getErrorMessage());
    }

    public static int createError(FlatBufferBuilder flatBufferBuilder, int errorCode, String errorMessage) {
        int errorMessageOffset = flatBufferBuilder.createString(errorMessage);
        flatBufferBuilder.startTable(2);
        flatBufferBuilder.addOffset(1, errorMessageOffset, 0);
        flatBufferBuilder.addInt(0, errorCode, 0);
        int end = flatBufferBuilder.endTable();
        flatBufferBuilder.finish(end);
        return end;
    }

    public int getErrorCode() {
        if (errorCode == 0) {
            errorCode = getInt(0);
        }
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        if (errorMessage == null) {
            errorMessage = getString(1);
        }
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "Error{" + "errorCode=" + getErrorCode() + ", errorMessage='" + getErrorMessage() + '\'' + '}';
    }
}
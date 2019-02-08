package com.aequilibrium.transformersbattle.general.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorResponse implements ITransformersErrorResponse {

    public static final String ERROR_LIST = "messageList";
    public static final String ERROR_MSG = "message";
    public static final String ERROR_INFO = "errorInfo";
    public static final String[] ERROR_KEY = {ERROR_LIST, ERROR_MSG, ERROR_INFO};


    @SerializedName(ERROR_LIST)
    public List<ErrorMsg> messageList;
    @SerializedName(ERROR_INFO)
    public List<ErrorInfo> errorList;
    @SerializedName(ERROR_MSG)
    public String message;

    public class ErrorMsg {
        @SerializedName("reasonCode")
        public String reasonCode;
        @SerializedName("message")
        public String message;
    }

    public class ErrorInfo {
        @SerializedName("code")
        public String reasonCode;
        @SerializedName("detail")
        public ErrorDetail detail;

        public class ErrorDetail {
            @SerializedName("cause")
            public String cause;
        }
    }

    @Override
    public String getMessage() {
        if (errorList != null && errorList.size() > 0 && errorList.get(0).reasonCode != null && errorList.get(0).detail != null) {
            return errorList.get(0).detail.cause;
        } else if (errorList != null &&
                errorList.size() > 0
                && errorList.get(0).reasonCode != null
                && errorList.get(0).detail == null) {

            return errorList.get(0).reasonCode;

        } else {
            if (messageList != null && messageList.size() > 0) {
                return messageList.get(0).message;
            }
            return "";
        }

    }

    @Override
    public String getErrorCode() {

        if (errorList != null && errorList.size() > 0) {
            return errorList.get(0).reasonCode;
        } else {
            if (messageList != null && messageList.size() > 0) {
                return messageList.get(0).reasonCode;
            }
            return "";
        }

    }
}

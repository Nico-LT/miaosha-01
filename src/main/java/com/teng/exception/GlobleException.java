package com.teng.exception;

import com.teng.common.enums.ResultStatus;

public class GlobleException extends RuntimeException {//全局异常捕获
   //因为是枚举类型的
    private ResultStatus resultStatus;

    public GlobleException(ResultStatus resultStatus){
        super();
        this.resultStatus = resultStatus;
    }


    public ResultStatus getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }
}

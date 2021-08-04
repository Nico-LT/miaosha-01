package com.teng.common;


import com.teng.common.enums.ResultStatus;
import lombok.Data;

@Data
public class ErrorResponseEntity {

    private int code;
    private String message;
    private String data;

  public   ErrorResponseEntity(ResultStatus resultStatus) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();

    }

    public ErrorResponseEntity(ResultStatus resultStatus, String data) {

        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
        this.data = data;

    }

    public  ErrorResponseEntity(String message) {

        this.message = message;
    }
}

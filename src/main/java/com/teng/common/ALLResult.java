package com.teng.common;


import com.teng.common.enums.ResultStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ALLResult {
    private int code;
    private String data;
    private String message;


   public ALLResult(ResultStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }
    public ALLResult(ResultStatus status,String data) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.data = data;
    }

    public ALLResult SUCCESS(ResultStatus status) {
        if (this.data == "" || this.data == null) {

            return new ALLResult(status);
        } else {
            return new ALLResult(status, this.data);
        }
    }
    public ALLResult SUCCESS(ResultStatus status ,String data) {
        return new ALLResult(status, data);
    }


    public ALLResult ERROR(ResultStatus status) {
        return new ALLResult(status);
    }
}

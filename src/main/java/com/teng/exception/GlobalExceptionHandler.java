package com.teng.exception;

import com.teng.common.ErrorResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.teng.common.enums.ResultStatus.*;
import static com.teng.common.enums.ResultStatus.RESULT_TOKEN_ERROR;

@RestControllerAdvice//
//@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

 /*   @ExceptionHandler(value = MethodArgumentNotValidException.class)

    public String  errorMessages(MethodArgumentNotValidException e){
        for (ObjectError s : e.getBindingResult().getAllErrors()) {
            return s.getDefaultMessage();
        }
        return e.getMessage();
    }*/

    /**
     * 定义异常
     * 全局异常捕获返回数据
     */
    @ExceptionHandler(value = Exception.class)
    public ErrorResponseEntity exceptionHandler(Exception e) {
        e.printStackTrace();
        if (e instanceof GlobleException) {

            return new ErrorResponseEntity(((GlobleException) e).getResultStatus());

        } else if (e instanceof MethodArgumentNotValidException) {
            e.printStackTrace();
           // System.out.println("BindExceptiony衣惨AnGg");
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;

            BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            allErrors.forEach(objectError -> {
                System.out.println(objectError.getDefaultMessage());
            });


            log.error(allErrors.get(0).getDefaultMessage());

            return new ErrorResponseEntity(RESULT_TOKEN_ERROR);

        } else if (e instanceof RedisConnectionFailureException) {

            return new ErrorResponseEntity(RESULT_REDIS_ERROR);
        } else {
            return new ErrorResponseEntity(RESULT_SYSTEM_ERROR);
        }


    }

}

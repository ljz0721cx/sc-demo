package com.xn.demo.log.exception;


import com.thclouds.commons.base.exceptions.CheckedException;
import com.thclouds.commons.sop.response.SopResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(basePackages={"com.xn.demo"})
public class GlobalExceptionHandler {


    /**
     * 服务异常
     *
     * @param e the e
     * @return SopResponse
     */
    @ExceptionHandler(CheckedException.class)
    @ResponseStatus(HttpStatus.OK)
    public SopResponse handleInspectionFailedException(CheckedException e) {
        System.out.println("服务异常信息 ex="+e.getMessage());
        return new SopResponse(e);
    }

}

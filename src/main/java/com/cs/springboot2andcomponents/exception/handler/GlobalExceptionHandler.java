package com.cs.springboot2andcomponents.exception.handler;

import com.cs.springboot2andcomponents.exception.BadRequestException;
import com.cs.springboot2andcomponents.exception.EntityExistException;
import com.cs.springboot2andcomponents.exception.EntityNotFoundException;
import com.cs.springboot2andcomponents.model.Result;
import com.cs.springboot2andcomponents.util.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * 全局rest接口异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Result> handleException(Throwable e){
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return buildResponseEntity(Result.resultError(e.getMessage()));
    }

//    /**
//     * 与Spring security配合使用
//     * BadCredentialsException
//     */
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<Result> badCredentialsException(BadCredentialsException e){
//        // 打印堆栈信息
//        String message = "坏的凭证".equals(e.getMessage()) ? "用户名或密码不正确" : e.getMessage();
//        log.error(message);
//        return buildResponseEntity(Result.error(message));
//    }

    /**
     * 处理自定义异常
     */
	@ExceptionHandler(value = BadRequestException.class)
	public ResponseEntity<Result> badRequestException(BadRequestException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return buildResponseEntity(Result.resultError(e.getStatus(),e.getMessage()));
	}

    /**
     * 处理 EntityExist
     */
    @ExceptionHandler(value = EntityExistException.class)
    public ResponseEntity<Result> entityExistException(EntityExistException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return buildResponseEntity(Result.resultError(e.getMessage()));
    }

    /**
     * 处理 EntityNotFound
     */
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Result> entityNotFoundException(EntityNotFoundException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return buildResponseEntity(Result.resultError(NOT_FOUND.value(),e.getMessage()));
    }

    /**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String msg = "不能为空";
        if(msg.equals(message)){
            message = str[1] + ":" + message;
        }
        return buildResponseEntity(Result.resultError(message));
    }

    /**
     * 处理算数错误
     */
    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<Result> arithmeticException(ArithmeticException e){
        log.error(ThrowableUtil.getStackTrace(e));
        return buildResponseEntity(Result.resultError("算数运算错误"));
    }

    /**
     * 统一返回（都返回200）
     */
    private ResponseEntity<Result> buildResponseEntity(Result result) {
//        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

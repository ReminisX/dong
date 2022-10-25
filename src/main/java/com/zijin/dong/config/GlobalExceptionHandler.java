package com.zijin.dong.config;

import cn.dev33.satoken.exception.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ZhangXD
 * @Date 2021/11/25 11:18
 * @Description
 */
@ControllerAdvice
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse validationErrorHandler(MethodArgumentNotValidException ex) throws JsonProcessingException {
        //1.此处先获取BindingResult
        BindingResult bindingResult = ex.getBindingResult();
        //2.获取错误信息
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        //3.组装异常信息
        Map<String,String> message = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            message.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        //4.将map转换为JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(message);
        //5.返回错误信息
        return ResponseUtil.faliure().addData(json);
    }

    @ResponseBody
    @ExceptionHandler(value = SaTokenException.class)
    public BaseResponse handlerException(SaTokenException e, HttpServletRequest request, HttpServletResponse response){
        logger.error(e.getLocalizedMessage());
        logger.warn("token为:" + request.getHeader("satoken"));
        String errMsg = "";
        if (e instanceof NotLoginException){
            errMsg = e.getLocalizedMessage();
        }else if (e instanceof NotRoleException){
            errMsg = "无此角色权限: " + ((NotRoleException) e).getRole();
        }else if (e instanceof NotPermissionException){
            errMsg = "无此权限: " + ((NotPermissionException) e).getCode();
        }else if (e instanceof DisableLoginException){
            errMsg = "账号被封禁: " + ((DisableLoginException) e).getDisableTime() + "秒后解封";
        }else{
            errMsg = e.getLocalizedMessage();
        }
        if (errMsg.length() > 100){
            errMsg = errMsg.substring(0, 100);
        }
        return ResponseUtil.faliure().setMessage(errMsg);
    }
}

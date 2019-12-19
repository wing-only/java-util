package com.wing.java.util;

import com.wing.java.util.exception.BusinessException;
import com.wing.java.util.exception.ExceptionConstant;
import com.wing.java.util.param.http.HttpRespParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Set;

/**
 * controller层统一异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     *
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "wing");
    }


    @ExceptionHandler(Exception.class)
    public HttpRespParam handlerException(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return new HttpRespParam(0, new HashMap(), ExceptionConstant.ERROR_MSG);
    }

    @ExceptionHandler(BusinessException.class)
    public HttpRespParam handlerException(BusinessException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return new HttpRespParam(e.getCode() == -1 ? 0 : e.getCode(), new HashMap(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpRespParam handlerException(MethodArgumentNotValidException e) {
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error(defaultMessage);
        return new HttpRespParam(0, new HashMap(), defaultMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public HttpRespParam handlerException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            System.out.println(constraintViolation.getMessage());
            log.error(constraintViolation.getMessage());
            return new HttpRespParam(0, new HashMap(), constraintViolation.getMessage());
        }
        log.error(e.getMessage());
        return new HttpRespParam(0, new HashMap(), "error");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public HttpRespParam handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return new HttpRespParam(0, new HashMap(), "已存在该记录");
    }

    @ExceptionHandler(AuthenticationException.class)
    public HttpRespParam handleAuthorizationException(AuthenticationException e) {
        log.error(e.getMessage(), e);
        return new HttpRespParam(ExceptionConstant.SC_UNAUTHORIZED, new HashMap(), "请重新登录");
    }

    @ExceptionHandler(AuthorizationException.class)
    public HttpRespParam handleAuthorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return new HttpRespParam(ExceptionConstant.SC_FORBIDDEN, new HashMap(), "权限不足，请联系管理员授权");
    }


}

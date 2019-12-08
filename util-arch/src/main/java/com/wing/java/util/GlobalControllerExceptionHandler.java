package com.wing.java.util;

import com.wing.java.util.exception.BusinessException;
import com.wing.java.util.param.http.HttpRespParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * controller层统一异常处理
 */
@ControllerAdvice
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
        model.addAttribute("author", "Magical Sam");
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public HttpRespParam handlerException(Exception e) {
        e.printStackTrace();
        return new HttpRespParam(0, "请求失败");
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public HttpRespParam handlerException(BusinessException e) {
        e.printStackTrace();
        return new HttpRespParam(e.getCode() == -1 ? 0 : e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public HttpRespParam handlerException(MethodArgumentNotValidException e) {
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new HttpRespParam(0, defaultMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public HttpRespParam handlerException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            System.out.println(constraintViolation.getMessage());
            return new HttpRespParam(0, constraintViolation.getMessage());
        }
        return new HttpRespParam(0);
    }
}

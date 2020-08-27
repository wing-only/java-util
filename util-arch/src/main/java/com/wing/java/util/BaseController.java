package com.wing.java.util;

import com.wing.java.util.exception.ExceptionConstant;
import com.wing.java.util.param.http.HttpRspParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Slf4j
public class BaseController {

    protected HttpRspParam success(String msg) {
        return new HttpRspParam(ExceptionConstant.SUCCESS, new HashMap<>(), msg);
    }

    protected HttpRspParam success(Object data) {
        return new HttpRspParam(ExceptionConstant.SUCCESS, data, "success");
    }

    protected HttpRspParam success(Object data, String msg) {
        return new HttpRspParam(ExceptionConstant.SUCCESS, data, msg);
    }

    protected HttpRspParam error(Object data) {
        return new HttpRspParam(ExceptionConstant.ERROR, data, "error");
    }

    protected HttpRspParam error(String msg) {
        return new HttpRspParam(ExceptionConstant.ERROR, new HashMap(), msg);
    }

    protected HttpRspParam error(int code, Object data) {
        return new HttpRspParam(code, data, "error");
    }

    protected HttpRspParam error(int code, String msg) {
        return new HttpRspParam(code, new HashMap(), msg);
    }

    protected HttpRspParam error(int code, Object data, String msg) {
        return new HttpRspParam(code, data, msg);
    }


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new MyDateEditor());
    }

    private class MyDateEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = format.parse(text);
            } catch (ParseException e) {
                format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = format.parse(text);
                } catch (ParseException e1) {
                    format = new SimpleDateFormat("HH:mm:ss");
                    try {
                        date = format.parse(text);
                    } catch (ParseException e2) {
                        e2.printStackTrace();
                    }
                }
            }
            setValue(date);
        }
    }
}

package com.wing.java.util;

import com.wing.java.util.param.http.HttpRespParam;
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

    protected HttpRespParam success(String msg) {
        return new HttpRespParam(1, new HashMap<>(), msg);
    }

    protected HttpRespParam success(Object data) {
        return new HttpRespParam(1, data, "success");
    }

    protected HttpRespParam success(Object data, String msg) {
        return new HttpRespParam(1, data, msg);
    }

    protected HttpRespParam error(Object data) {
        return new HttpRespParam(0, data, "error");
    }

    protected HttpRespParam error(String msg) {
        return new HttpRespParam(0, new HashMap(), msg);
    }

    protected HttpRespParam error(int code, Object data) {
        return new HttpRespParam(code, data, "error");
    }

    protected HttpRespParam error(int code, String msg) {
        return new HttpRespParam(code, new HashMap(), msg);
    }

    protected HttpRespParam error(int code, Object data, String msg) {
        return new HttpRespParam(code, data, msg);
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

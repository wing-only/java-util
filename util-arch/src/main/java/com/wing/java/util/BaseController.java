package com.wing.java.util;

import com.wing.java.util.param.http.HttpRespParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class BaseController {

    protected HttpRespParam success(Object data) {
        return new HttpRespParam(1, data);
    }

    protected HttpRespParam error(Object data) {
        return new HttpRespParam(0, data);
    }

    protected HttpRespParam error(int code, Object data) {
        return new HttpRespParam(code, data);
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

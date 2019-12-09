package com.wing.java.util.param;

import com.wing.java.util.exception.ExceptionConstant;
import org.apache.http.HttpStatus;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wing
 * @create 2018-12-09 10:15
 */
public class Result extends RespParam<HashMap<String, Object>> {

    public Result() {
        this.code = 1;
        this.msg = "success";
        this.data = new HashMap();
    }

    public static Result error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, ExceptionConstant.ERROR_MSG);
    }

    public static Result error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static Result error(int code, String msg) {
        Result r = new Result();
        r.code = code;
        r.msg = msg;
        return r;
    }

    public static Result error(int code, String msg, Object... arguments) {
        Result r = new Result();
        r.code = code;
        r.msg = MessageFormat.format(msg, arguments);
        return r;
    }

    public static Result ok(String msg) {
        Result r = new Result();
        r.msg = msg;
        return r;
    }

    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        r.data.putAll(map);
        return r;
    }

    public static Result ok() {
        Result r = new Result();
        return r;
    }

    public Result put(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

}

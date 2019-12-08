package com.wing.java.util.param.http;

import com.wing.java.util.param.RespParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * web层接口返回
 * @author wing
 * @param <E>
 */
@ApiModel(value = "REST返回数据封装")
@Data
public class HttpRespParam<E> extends RespParam<E> {

    public HttpRespParam() {
        super();
    }

    public HttpRespParam(int code) {
        this.code = code;
    }

    public HttpRespParam(int code, E data) {
        this.code = code;
        this.data = data;
    }

}

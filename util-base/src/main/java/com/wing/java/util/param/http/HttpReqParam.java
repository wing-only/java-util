package com.wing.java.util.param.http;

import com.wing.java.util.param.ReqParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * http 请求参数
 * @author wing
 * @param <E>
 */
@Deprecated
@ApiModel(value = "REST请求参数封装")
@Data
public class HttpReqParam<E> extends ReqParam<E> {

    public HttpReqParam() {
        super();
    }

    public HttpReqParam(E data) {
        this.data = data;
    }

}

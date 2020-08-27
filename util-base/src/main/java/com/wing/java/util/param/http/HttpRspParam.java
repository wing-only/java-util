package com.wing.java.util.param.http;

import com.wing.java.util.param.RspParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * web层接口返回
 * @author wing
 * @param <E>
 */
@ApiModel(value = "REST返回数据封装")
@Data
@NoArgsConstructor
public class HttpRspParam<E> extends RspParam<E> {

//    public HttpRespParam() {
//        super();
//    }
//
//    public HttpRespParam(int code) {
//        this.code = code;
//        this.msg = "";
//    }
//
//    public HttpRespParam(int code, E data) {
//        this.code = code;
//        this.msg = "";
//        this.data = data;
//    }
//
//    public HttpRespParam(int code, String msg) {
//        this.code = code;
//        this.msg = msg;
//    }

    public HttpRspParam(int code, E data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

}

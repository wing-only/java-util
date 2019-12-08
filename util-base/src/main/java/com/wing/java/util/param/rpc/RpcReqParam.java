package com.wing.java.util.param.rpc;

import com.wing.java.util.param.ReqParam;
import lombok.Data;

import java.util.Map;

@Data
public class RpcReqParam<E> extends ReqParam<E> {

    protected Map ext;

}

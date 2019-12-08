package com.wing.java.util.param.rpc;

import com.wing.java.util.param.RespParam;
import lombok.Data;

import java.util.Map;

@Data
public class RpcRespParam<E> extends RespParam<E> {

	protected Map ext;

}

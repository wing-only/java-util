package com.wing.java.util.param.rpc;

import com.wing.java.util.param.RspParam;
import lombok.Data;

import java.util.Map;

@Data
public class RpcRspParam<E> extends RspParam<E> {

	protected Map ext;

}

package com.wing.java.util.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @param <E>
 * @author wing
 */
@ApiModel(value = "返回数据封装")
@Data
public class RespParam<E> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "返回码", name = "code", notes = "1 代表成功，0或非1代表失败", required = true, example = "1")
    protected int code = 0;

    @ApiModelProperty(notes = "返回的数据,可能是空或是单个数据或是json对象或是数组", required = false, example = "{'userName':'wing'}")
    protected E data;

    @ApiModelProperty(notes = "返回消息提示", required = false, example = "success")
    protected String msg;
}

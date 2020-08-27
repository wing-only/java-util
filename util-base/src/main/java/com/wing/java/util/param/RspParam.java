package com.wing.java.util.param;

import com.wing.java.util.exception.ExceptionConstant;
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
public class RspParam<E> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "返回码", name = "code", notes = "0 代表成功，1或非0代表失败", required = true, example = "1")
    protected int code = ExceptionConstant.SUCCESS;

    @ApiModelProperty(notes = "返回的数据,可能是空或是单个数据或是json对象或是数组", required = false, example = "{'userName':'wing'}")
    protected E data;

    @ApiModelProperty(notes = "返回消息提示", required = false, example = "success")
    protected String msg;
}

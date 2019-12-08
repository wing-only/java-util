package com.wing.java.util.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wing
 * @param <E>
 */
@ApiModel(value = "返回数据封装")
@Data
public class RespParam<E> implements Serializable {
	@ApiModelProperty(notes="返回码 1成功 0失败",required=true,example="1")
	protected int code = -1;

	@ApiModelProperty(notes="返回的数据,可能是空或是单个数据或是json对象或是数组",required=false,example="success")
	protected E data;


}

package com.wing.java.util.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础请求参数
 * @author wing
 * @param <E>
 */
@ApiModel(value = "请求参数封装")
@Data
public class ReqParam<E> implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes="请求参数", required=true, example="{\"name\":\"wing\"}")
	protected E data;

}

package com.wing.java.util.param.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wing
 * @create 2018-09-03 10:18
 */
@ApiModel(value = "分页对象")
@Data
public class Page<SE, WP> implements Serializable {
    @ApiModelProperty(notes="分页参数", required=true, example="{\"pageIndex\":\"1\"}")
    protected PageParam page;

    @ApiModelProperty(notes="请求参数", required=true, example="{\"name\":\"wing\"}")
    protected WP param;

    @ApiModelProperty(hidden = true, notes="返回分页列表", required=false)
    private List<SE> dataList;              // 查询结果分页数据
}


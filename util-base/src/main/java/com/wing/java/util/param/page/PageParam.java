package com.wing.java.util.param.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "分页参数")
@Data
public class PageParam implements Serializable {
    @ApiModelProperty(notes="当前页", required=true, example="1")
    private long pageIndex = 1;             // 请求参数：第几页

    @ApiModelProperty(notes="每页数量", required=true, example="10")
    private int pageSize = 10;             // 请求参数：每页条数

    @ApiModelProperty(notes="是否统计总数", required=true, example="false")
    private boolean showCount = false;

    @ApiModelProperty(hidden = true, notes="返回总数量", required=false, example="100")
    private long totalCount = 0;            // 总条数

    @ApiModelProperty(hidden = true, notes="返回总页数", required=false, example="10")
    private long totalPage = 0;             // 总页数
}

package com.wing.java.util.model;

import com.wing.java.util.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class BaseEntity implements Serializable {

    @ApiModelProperty(value="创建人ID",name="createUserId",example="1001")
    @Null(message="创建人ID必须为空", groups = {UpdateGroup.class})
    private Long createUserId;

    @ApiModelProperty(value="创建时间",name="createTime",example="2019-09-24 13:25:01")
    @Null(message="创建时间必须为空", groups = {UpdateGroup.class})
    private Timestamp createTime;

    @ApiModelProperty(value="修改人ID",name="updateUserId",example="1")
    private Long updateUserId;

    @ApiModelProperty(value="修改时间",name="updateTime",example="2019-09-24 13:25:01")
    private Timestamp updateTime;

}

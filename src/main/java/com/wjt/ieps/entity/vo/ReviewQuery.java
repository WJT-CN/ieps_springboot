package com.wjt.ieps.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;

@Data
public class ReviewQuery {
    @ApiModelProperty("项目名称")
    private String itemName;

    @ApiModelProperty("评审意见")
    private String reviewOption;

    @ApiModelProperty("评审类型：1：立项评审；2：中期检查；3：结题评审")
    private String reviewType;

    @ApiModelProperty("评审时间")
    private Date reviewTime;
}

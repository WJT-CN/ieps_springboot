package com.wjt.ieps.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AllItemQuery {
    @ApiModelProperty("项目编号")
    private String itemNum;

    @ApiModelProperty("项目类别：1:一般项目;2:重点支持领域项目")
    private String itemCategory;

    @ApiModelProperty("项目类型：1：创新训练；2：创业训练；3：创业实践")
    private String itemType;

    @ApiModelProperty("项目名称")
    private String itemName;

    @ApiModelProperty("所属学院")
    private String itemCollege;

    @ApiModelProperty("项目负责人")
    private String leaderName;

    @ApiModelProperty("项目级别：1: 无；2：校级；3：省区级；4：国家级")
    private String itemLevel;

    @ApiModelProperty("申报时间")
    private String itemDate;
}

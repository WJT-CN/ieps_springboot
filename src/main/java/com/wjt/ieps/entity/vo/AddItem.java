package com.wjt.ieps.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddItem {

    @ApiModelProperty("项目类型：1：创新训练；2：创业训练；3：创业实践")
    private String itemType;

    @ApiModelProperty("项目名称")
    private String itemName;

    @ApiModelProperty("项目类别：1:一般项目;2:重点支持领域项目")
    private String itemCategory;

    @ApiModelProperty("所属重点领域")
    private String itemTerritory;

    @ApiModelProperty("所属学院")
    private String itemCollege;

    @ApiModelProperty("项目负责人")
    private String leaderName;

    @ApiModelProperty("项目负责人学号")
    private String leaderNum;

    @ApiModelProperty("项目负责人联系电话")
    private String leaderPhone;

    @ApiModelProperty("项目简介")
    private String summary;

    private String teamNum1;
    private String teamNum2;
    private String teamNum3;
    private String teamNum4;
    private String teacherNo1;
    private String teacherNo2;
    private String teacherNo3;
    private String fileName;
    private String fileUrl;
}

package com.wjt.ieps.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddChange {

    @ApiModelProperty("项目id")
    private Integer itemId;

    @ApiModelProperty("学院")
    private String college;

    @ApiModelProperty("变更类型")
    private String type;

    private String note;

    @ApiModelProperty("理由")
    private String reason;

    private String leader;

    private String stu1;
    private String stu2;
    private String stu3;
    private String stu4;

    private String tea1;
    private String tea2;
    private String tea3;
}

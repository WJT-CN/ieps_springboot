package com.wjt.ieps.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author wjt
 * @since 2022-06-03
 */
@TableName("ieps_change")
@ApiModel(value = "Change对象", description = "")
public class Change implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("项目id")
    private Integer itemId;

    private Integer status;

    @ApiModelProperty("学院意见")
    private String collegeOption;

    @ApiModelProperty("学校意见")
    private String schoolOption;

    @ApiModelProperty("学院")
    private String college;

    @ApiModelProperty("变更类型")
    private String type;

    private String note;

    @ApiModelProperty("理由")
    private String reason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getCollegeOption() {
        return collegeOption;
    }

    public void setCollegeOption(String collegeOption) {
        this.collegeOption = collegeOption;
    }
    public String getSchoolOption() {
        return schoolOption;
    }

    public void setSchoolOption(String schoolOption) {
        this.schoolOption = schoolOption;
    }
    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Change{" +
            "id=" + id +
            ", itemId=" + itemId +
            ", status=" + status +
            ", collegeOption=" + collegeOption +
            ", schoolOption=" + schoolOption +
            ", college=" + college +
            ", type=" + type +
            ", note=" + note +
            ", reason=" + reason +
        "}";
    }
}

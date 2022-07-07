package com.wjt.ieps.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author wjt
 * @since 2022-05-05
 */
@Data
@TableName("ieps_item")
@ApiModel(value = "Item对象", description = "")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("项目id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("项目编号")
    private String itemNum;

    @ApiModelProperty("项目类别：1:一般项目;2:重点支持领域项目")
    private String itemCategory;

    @ApiModelProperty("项目类型：1：创新训练；2：创业训练；3：创业实践")
    private String itemType;

    @ApiModelProperty("项目名称")
    private String itemName;

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

    private Integer itemScore;

    private Integer itemAssign;

    @ApiModelProperty("<!-- 项目状态：1：申请中；2：立项评审；3：已立项；4：立项失败；5：中期检查; 6: 待结题；7：结题评审；8：结题成功；9：结题失败-->")
    private String itemStatus;

    @ApiModelProperty("项目级别：1: 无；2：校级；3：省区级；4：国家级")
    private String itemLevel;

    @ApiModelProperty("申报时间")
    private String itemDate;

    @ApiModelProperty("项目简介")
    private String summary;

    @ApiModelProperty("备注")
    private String note;

    @TableField(exist = false)
    private String student;
    @TableField(exist = false)
    private String teacher;
    @TableField(exist = false)
    private File file;

    @Override
    public String toString() {
        return "Item{" +
            "id=" + id +
            ", itemNum=" + itemNum +
            ", itemCategory=" + itemCategory +
            ", itemType=" + itemType +
            ", itemName=" + itemName +
            ", itemTerritory=" + itemTerritory +
            ", itemCollege=" + itemCollege +
            ", leaderName=" + leaderName +
            ", leaderNum=" + leaderNum +
            ", leaderPhone=" + leaderPhone +
            ", itemAssign=" + itemAssign +
            ", itemStatus=" + itemStatus +
            ", itemLevel=" + itemLevel +
            ", itemDate=" + itemDate +
            ", summary=" + summary +
            ", note=" + note +
        "}";
    }
}

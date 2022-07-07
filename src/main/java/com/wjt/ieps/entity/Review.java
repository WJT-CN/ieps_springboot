package com.wjt.ieps.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
@TableName("ieps_review")
@ApiModel(value = "Review对象", description = "")
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评审id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("评委职工号")
    private String userNum;

    @ApiModelProperty("项目id")
    private Integer itemNum;

    @ApiModelProperty("评委打分")
    private Integer reviewScore;

    @ApiModelProperty("评审意见")
    private String reviewOption;

    @ApiModelProperty("评审等级")
    private String reviewGrade;

    private Integer reviewStatus;

    @ApiModelProperty("评审类型：1：立项评审；2：中期检查；3：结题评审")
    private String reviewType;

    @ApiModelProperty("评审级别： 1：院级评审；2：校级评审；3：省区级评审；4：国家级评审")
    private String reviewLevel;

    @ApiModelProperty("评审时间")
    private java.sql.Date reviewTime;

    @TableField(exist = false)
    private Item item;

    @Override
    public String toString() {
        return "Review{" +
            "id=" + id +
            ", userNum=" + userNum +
            ", itemNum=" + itemNum +
            ", reviewScore=" + reviewScore +
            ", reviewOption=" + reviewOption +
            ", reviewGrade=" + reviewGrade +
            ", reviewStatus=" + reviewStatus +
            ", reviewType=" + reviewType +
            ", reviewLevel=" + reviewLevel +
            ", reviewTime=" + reviewTime +
        "}";
    }
}

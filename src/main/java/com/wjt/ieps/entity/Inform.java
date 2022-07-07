package com.wjt.ieps.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author wjt
 * @since 2022-04-30
 */
@TableName("ieps_inform")
@ApiModel(value = "Inform对象", description = "")
@Data
public class Inform implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("系统通知id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("通知标题")
    private String head;

    @ApiModelProperty("发布通知者")
    private String publisher;

    @ApiModelProperty("通知主题")
    private String subject;

    @ApiModelProperty("通知内容")
    private String content;

    private String files;

    private String url;

    @ApiModelProperty("发布时间")
    private java.sql.Date pubdate;

    @Override
    public String toString() {
        return "Inform{" +
            "id=" + id +
            ", head=" + head +
            ", publisher=" + publisher +
            ", subject=" + subject +
            ", content=" + content +
            ", files=" + files +
            ", pubdate=" + pubdate +
        "}";
    }
}

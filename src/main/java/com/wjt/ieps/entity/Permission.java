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
 * @since 2022-04-25
 */
@Data
@TableName("ieps_permission")
@ApiModel(value = "Permission对象", description = "")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("功能id")
    @TableId(value = "perm_id", type = IdType.AUTO)
    private Integer permId;

    @ApiModelProperty("功能名称")
    private String permName;

    @ApiModelProperty("权限类型（menu/permission）")
    private String permType;

    @ApiModelProperty("功能链接")
    private String url;

    @ApiModelProperty("父菜单id")
    private Integer parentId;

    @ApiModelProperty("具体权限")
    private String permCode;

    @TableField(exist = false)
    private List<Permission> children;
    @Override
    public String toString() {
        return "Permission{" +
            "permId=" + permId +
            ", permName=" + permName +
            ", permType=" + permType +
            ", url=" + url +
            ", parentId=" + parentId +
            ", permCode=" + permCode +
        "}";
    }
}

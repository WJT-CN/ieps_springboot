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
 * @since 2022-04-25
 */
@TableName("ieps_role_perm")
@ApiModel(value = "RolePerm对象", description = "")
public class RolePerm implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色权限id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("角色id")
    private Integer roleId;

    @ApiModelProperty("权限id")
    private Integer permId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    public Integer getPermId() {
        return permId;
    }

    public void setPermId(Integer permId) {
        this.permId = permId;
    }

    @Override
    public String toString() {
        return "RolePerm{" +
            "id=" + id +
            ", roleId=" + roleId +
            ", permId=" + permId +
        "}";
    }
}

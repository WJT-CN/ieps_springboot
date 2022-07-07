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
 * @since 2022-05-05
 */
@TableName("ieps_user_item")
@ApiModel(value = "UserItem对象", description = "")
public class UserItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户项目id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户id")
    private String userNum;

    @ApiModelProperty("项目id")
    private Integer itemId;

    @ApiModelProperty("身份标识：负责人/成员/指导老师")
    private Integer identity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
        return "UserItem{" +
            "id=" + id +
            ", userNum=" + userNum +
            ", itemId=" + itemId +
            ", identity=" + identity +
        "}";
    }
}

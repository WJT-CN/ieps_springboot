package com.wjt.ieps.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("ieps_file")
@ApiModel(value = "File对象", description = "")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer itemId;

    private String fileName;

    private String fileType;

    private String fileUrl;


    @Override
    public String toString() {
        return "File{" +
            "id=" + id +
            ", username=" + itemId +
            ", fileName=" + fileName +
            ", fileType=" + fileType +
        "}";
    }
}

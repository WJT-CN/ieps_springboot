package com.wjt.ieps.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author wjt
 * @since 2022-04-25
 */
@TableName("ieps_teacher")
@ApiModel(value = "Teacher对象", description = "")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String teacherNo;

    private String tName;

    private String tSex;

    private String tCollege;

    private String tProfession;

    private Integer tRank;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(String teacherNo) {
        this.teacherNo = teacherNo;
    }
    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }
    public String gettSex() {
        return tSex;
    }

    public void settSex(String tSex) {
        this.tSex = tSex;
    }
    public String gettCollege() {
        return tCollege;
    }

    public void settCollege(String tCollege) {
        this.tCollege = tCollege;
    }
    public String gettProfession() {
        return tProfession;
    }

    public void settProfession(String tProfession) {
        this.tProfession = tProfession;
    }
    public Integer gettRank() {
        return tRank;
    }

    public void settRank(Integer tRank) {
        this.tRank = tRank;
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + id +
            ", teacherNo=" + teacherNo +
            ", tName=" + tName +
            ", tSex=" + tSex +
            ", tCollege=" + tCollege +
            ", tProfession=" + tProfession +
            ", tRank=" + tRank +
        "}";
    }
}

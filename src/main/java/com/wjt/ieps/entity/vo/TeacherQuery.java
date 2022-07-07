package com.wjt.ieps.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TeacherQuery {
    private String teacherNo;

    @JsonProperty
    private String tName;
    @JsonProperty
    private String tCollege;
    @JsonProperty
    private String tProfession;
}

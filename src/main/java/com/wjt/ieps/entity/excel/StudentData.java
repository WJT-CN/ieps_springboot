package com.wjt.ieps.entity.excel;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class StudentData {
    @ExcelProperty(index = 0)
    private String stuNo;
    @ExcelProperty(index = 1)
    private String name;
    @ExcelProperty(index = 2)
    private String sex;
    @ExcelProperty(index = 3)
    private String grade;
    @ExcelProperty(index = 4)
    private String college;
    @ExcelProperty(index = 5)
    private String major;
    @ExcelProperty(index = 6)
    private String phone;
    @ExcelProperty(index = 7)
    private Integer rank;
}

package com.wjt.ieps.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjt.ieps.entity.Student;
import com.wjt.ieps.entity.User;
import com.wjt.ieps.entity.UserRole;
import com.wjt.ieps.entity.excel.StudentData;
import com.wjt.ieps.exception.IepsException;
import com.wjt.ieps.service.StudentService;
import com.wjt.ieps.service.UserRoleService;
import com.wjt.ieps.service.UserService;

public class StudentExcelListener  extends AnalysisEventListener<StudentData> {
    //StudentExcelListener，需要自己new，不能注入其他对象
    //不能实现数据库操作
    public StudentService studentService;
    public UserService userService;
    public UserRoleService userRoleService;

    public StudentExcelListener() {}
    public StudentExcelListener(StudentService studentService,UserService userService, UserRoleService userRoleService) {
        this.studentService = studentService;
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    public void invoke(StudentData studentData, AnalysisContext analysisContext) {
        if(studentData == null) {
            throw new IepsException(20001,"文件数据为空");
        }

        //一行一行读取
        //判断一级分类是否重复
        Student existStudent = this.existStudent(studentService, studentData.getStuNo());
        if(existStudent == null) { //没有相同一级分类，进行添加
            existStudent = new Student();
            existStudent.setStuNo(studentData.getStuNo());
            existStudent.setName(studentData.getName());
            existStudent.setSex(studentData.getSex());
            existStudent.setGrade(studentData.getGrade());
            existStudent.setCollege(studentData.getCollege());
            existStudent.setMajor(studentData.getMajor());
            existStudent.setPhone(studentData.getPhone());
            existStudent.setRank(studentData.getRank());
            User user = new User();
            user.setUsername(studentData.getStuNo());
            user.setPassword("123456");
            user.setStatus(0);
            userService.save(user);
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(202205);
            studentService.save(existStudent);
            userRoleService.save(userRole);
        }

    }

    private Student existStudent(StudentService studentService, String stuNo) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("stu_no",stuNo);
        Student student = studentService.getOne(wrapper);
        return student;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

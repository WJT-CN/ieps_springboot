package com.wjt.ieps.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjt.ieps.entity.Teacher;
import com.wjt.ieps.entity.User;
import com.wjt.ieps.entity.UserRole;
import com.wjt.ieps.entity.excel.TeacherData;
import com.wjt.ieps.exception.IepsException;
import com.wjt.ieps.service.TeacherService;
import com.wjt.ieps.service.UserRoleService;
import com.wjt.ieps.service.UserService;

public class TeacherExcelListener extends AnalysisEventListener<TeacherData> {

    public TeacherService teacherService;
    public UserService userService;
    public UserRoleService userRoleService;

    public TeacherExcelListener() {}
    public TeacherExcelListener(TeacherService teacherService, UserService userService, UserRoleService userRoleService) {
        this.teacherService = teacherService;
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    public void invoke(TeacherData teacherData, AnalysisContext analysisContext) {
        if(teacherData == null) {
            throw new IepsException(20001,"文件数据为空");
        }

        //一行一行读取
        //判断一级分类是否重复
        Teacher existTeacher = this.existTeacher(teacherService, teacherData.getTeacherNo());
        if(existTeacher == null) { //没有相同一级分类，进行添加
            existTeacher = new Teacher();
            existTeacher.setTeacherNo(teacherData.getTeacherNo());
            existTeacher.settName(teacherData.getTeacherName());
            existTeacher.settSex(teacherData.getTeacherSex());
            existTeacher.settCollege(teacherData.getTeacherCollege());
            existTeacher.settProfession(teacherData.getTeacherProfession());
            existTeacher.settRank(teacherData.getTeacherRank());
            User user = new User();
            user.setUsername(teacherData.getTeacherNo());
            user.setPassword("123456");
            user.setStatus(1);
            userService.save(user);
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(202204);
            teacherService.save(existTeacher);
            userRoleService.save(userRole);
        }

    }

    private Teacher existTeacher(TeacherService teacherService, String teacherNo) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_no",teacherNo);
        Teacher teacher = teacherService.getOne(wrapper);
        return teacher;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

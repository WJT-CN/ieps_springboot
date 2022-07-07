package com.wjt.ieps.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjt.ieps.entity.Student;
import com.wjt.ieps.entity.User;
import com.wjt.ieps.entity.UserRole;
import com.wjt.ieps.entity.excel.StudentData;
import com.wjt.ieps.listener.StudentExcelListener;
import com.wjt.ieps.mapper.StudentMapper;
import com.wjt.ieps.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjt.ieps.service.UserRoleService;
import com.wjt.ieps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjt
 * @since 2022-04-25
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @Override
    public void saveSubject(MultipartFile file, StudentService studentService, UserService userService, UserRoleService userRoleService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, StudentData.class,new StudentExcelListener(studentService,userService,userRoleService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteStudent(String id) {
        Student student = baseMapper.selectById(id);
        String username = student.getStuNo();
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        Integer uid = user.getId();
        int b1 = baseMapper.deleteById(id);
        boolean b = userService.remove(new QueryWrapper<User>().eq("username", username));
        boolean b2 = userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", uid));
        if(b1 > 0 && b && b2){
            return true;
        }
        return false;
    }
}

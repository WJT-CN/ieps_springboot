package com.wjt.ieps.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjt.ieps.entity.Student;
import com.wjt.ieps.entity.Teacher;
import com.wjt.ieps.entity.User;
import com.wjt.ieps.result.Result;
import com.wjt.ieps.service.StudentService;
import com.wjt.ieps.service.TeacherService;
import com.wjt.ieps.service.UserService;
import com.wjt.ieps.utils.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wjt
 * @since 2022-04-25
 */
@RestController
@RequestMapping("/ieps/user")
@CrossOrigin //解决跨域
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;

    @PostMapping("/login")
    public Result login(@RequestParam String username,
                        @RequestParam String password, HttpServletResponse response){
        String token = "";
        try {
            User user = userService.getOne(new QueryWrapper<User>().eq("username", username));

            if (!user.getPassword().equals(password)) {
                return Result.error().message("密码错误");
            }
            //生成token
             token = JwtUtils.sign(username);
            //写入header
            response.setHeader("Access-Token", token);
            response.setHeader("Access-Control-Expose-Headers", "Access-Token");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误");
            return Result.ok().message("密码错误");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误");
            return Result.ok().message("用户名错误");
        }
        return Result.ok().data("token",token);
    }

    //info
    @GetMapping("/info")
    public Result info() {
        Subject subject = SecurityUtils.getSubject();
        String name = "";
        if(subject.hasRole("校级管理员")){
            name = "校级管理员";
        }else if(subject.hasRole("院级管理员")){
            name = "院级管理员";
        }else if(subject.hasRole("教师")){
            name = "教师";
        }else if(subject.hasRole("学生")){
            name = "学生";
        }
        return Result.ok().data("roles","[admin]").data("name",name).data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    @GetMapping("/role")
    public Result role() {
        Subject subject = SecurityUtils.getSubject();
        String name = "";
        if(subject.hasRole("校级管理员")){
            name = "校级管理员";
        }else if(subject.hasRole("院级管理员")){
            name = "院级管理员";
        }else if(subject.hasRole("教师")){
            name = "教师";
        }else if(subject.hasRole("学生")){
            name = "学生";
        }
        return Result.ok().data("name",name);
    }

    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }

    @GetMapping("getUserInfo")
    public Result getUserInfo(){
        Subject subject = SecurityUtils.getSubject();
        String principal = (String) subject.getPrincipal();
        User user = userService.getOne(new QueryWrapper<User>().eq("username", principal));
        if(user.getStatus() == 0){
            Student student = studentService.getOne(new QueryWrapper<Student>().eq("stu_no", principal));
            user.setStudent(student);
        }else{
            Teacher teacher = teacherService.getOne(new QueryWrapper<Teacher>().eq("teacher_no", principal));
            user.setTeacher(teacher);
        }
        return Result.ok().data("user",user);
    }

    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestParam String oldPassword,
                        @RequestParam String newPassword){
        Subject subject = SecurityUtils.getSubject();
        String principal = (String) subject.getPrincipal();
        User user = userService.getOne(new QueryWrapper<User>().eq("username", principal));
        if(user.getPassword().equals(oldPassword)){
            user.setPassword(newPassword);
            boolean b = userService.updateById(user);
            if(b){
                return Result.ok();
            }else {
                return Result.error().message("修改失败");
            }
        }else {
            return Result.error().message("密码错误");
        }
    }

    @RequiresRoles("校级管理员")
    @GetMapping("/admin")
    public Result admin(){
        Subject subject = SecurityUtils.getSubject();
        boolean b = subject.hasRole("校级管理员");
        if(b){
            return Result.ok().message("校级管理员登录");
        }
        return Result.ok().message("xxxxxxx");
    }
}

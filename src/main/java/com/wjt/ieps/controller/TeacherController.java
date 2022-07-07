package com.wjt.ieps.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjt.ieps.entity.Role;
import com.wjt.ieps.entity.Teacher;
import com.wjt.ieps.entity.User;
import com.wjt.ieps.entity.UserRole;
import com.wjt.ieps.entity.vo.TeacherQuery;
import com.wjt.ieps.result.Result;
import com.wjt.ieps.service.TeacherService;
import com.wjt.ieps.service.UserRoleService;
import com.wjt.ieps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wjt
 * @since 2022-04-25
 */
@RestController
@RequestMapping("/ieps/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/list/{current}/{limit}")
    public Result getTeacherList(@PathVariable long current, @PathVariable long limit,
                                 @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<Teacher> pageTeacher = new Page<>(current,limit);
        //构建条件
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        if(teacherQuery != null){
            String name = teacherQuery.getTName();
            String stuNo = teacherQuery.getTeacherNo();
            String college = teacherQuery.getTCollege();
            String profession = teacherQuery.getTProfession();
            if(!StringUtils.isEmpty(name)){
                wrapper.like("t_name",name);
            }
            if(!StringUtils.isEmpty(stuNo)){
                wrapper.eq("teacher_no",stuNo);
            }
            if(!StringUtils.isEmpty(college)){
                wrapper.eq("t_college",college);
            }
            if(!StringUtils.isEmpty(profession)){
                wrapper.eq("t_profession",profession);
            }
        }

        Page<Teacher> page = teacherService.page(pageTeacher, wrapper);

        long total = page.getTotal();
        List<Teacher> records = page.getRecords();
        return Result.ok().data("total",total).data("rows",records);
    }

    @DeleteMapping("{id}")
    public Result removeTeacher(@PathVariable String id) {
        boolean flag = teacherService.deleteTeacherById(id);
        if(flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @PostMapping("addTeacher")
    public Result addTeacher(MultipartFile file) {
        //上传过来excel文件
        teacherService.saveSubject(file,teacherService,userService,userRoleService);
        return Result.ok();
    }

    @GetMapping("getTeacher/{id}")
    public Result getTeacher(@PathVariable Integer id){
        Teacher teacher = teacherService.getById(id);
        return Result.ok().data("teacher",teacher);
    }

    @PostMapping("uploadTeacher")
    public Result uploadTeacher(@RequestBody Teacher teacher){
        boolean b = teacherService.updateById(teacher);
        if(b){
            return Result.ok();
        }
        return Result.error();
    }

    @PostMapping("setRole")
    public Result setRole(@RequestParam String username, @RequestParam String roles){
        String[] split = roles.split(",");
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        Integer id = user.getId();
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id",id));

        for (String role : split) {
            int i = Integer.parseInt(role);
            UserRole userRole = new UserRole();
            userRole.setUserId(id);
            userRole.setRoleId(i);
            userRoleService.save(userRole);
        }
        return Result.ok();
    }

    @GetMapping("getRoles")
    public Result getRoles(String username){
        User user = userService.findRolesByUsername(username);
        List<Role> roles = user.getRoles();
        List<Integer> role = new ArrayList<>();
        for (Role role1 : roles) {
            role.add(role1.getRoleId());
        }
        return Result.ok().data("roles",role);
    }

    @GetMapping("getName")
    public Result getName(String teacherNo){
        Teacher teacher = teacherService.getOne(new QueryWrapper<Teacher>().eq("teacher_no", teacherNo));
        return Result.ok().data("teacher",teacher);
    }

    @GetMapping("getTeacherByNo")
    public Result getTeacherByNo(String teacherNo){
        Teacher teacher = teacherService.getOne(new QueryWrapper<Teacher>().eq("teacher_no", teacherNo));
        return Result.ok().data("teacher",teacher);
    }
}

package com.wjt.ieps.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjt.ieps.entity.Student;
import com.wjt.ieps.entity.Teacher;
import com.wjt.ieps.entity.vo.StudentQuery;
import com.wjt.ieps.result.Result;
import com.wjt.ieps.service.StudentService;
import com.wjt.ieps.service.UserRoleService;
import com.wjt.ieps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/ieps/student")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/list/{current}/{limit}")
    public Result getStudentList(@PathVariable long current, @PathVariable long limit,
                                 @RequestBody(required = false) StudentQuery studentQuery){
        //创建page对象
        Page<Student> pageStudent = new Page<>(current,limit);
        //构建条件
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        if(studentQuery != null){
            String name = studentQuery.getName();
            String stuNo = studentQuery.getStuNo();
            String college = studentQuery.getCollege();
            String major = studentQuery.getMajor();
            if(!StringUtils.isEmpty(name)){
                wrapper.like("name",name);
            }
            if(!StringUtils.isEmpty(stuNo)){
                wrapper.eq("stu_no",stuNo);
            }
            if(!StringUtils.isEmpty(college)){
                wrapper.eq("college",college);
            }
            if(!StringUtils.isEmpty(major)){
                wrapper.eq("major",major);
            }
        }

        Page<Student> page = studentService.page(pageStudent, wrapper);

        long total = page.getTotal();
        List<Student> records = page.getRecords();
        return Result.ok().data("total",total).data("rows",records);
    }

    @DeleteMapping("{id}")
    public Result removeTeacher(@PathVariable String id) {
        boolean flag = studentService.deleteStudent(id);
        if(flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @PostMapping("addStudent")
    public Result addStudent(MultipartFile file) {
        //上传过来excel文件
        studentService.saveSubject(file,studentService,userService,userRoleService);
        return Result.ok();
    }

    @GetMapping("getStudent/{id}")
    public Result getStudent(@PathVariable Integer id){
        Student student = studentService.getById(id);
        return Result.ok().data("student",student);
    }

    @PostMapping("uploadStudent")
    public Result uploadStudent(@RequestBody Student student){
        boolean b = studentService.updateById(student);
        if(b){
            return Result.ok();
        }
        return Result.error();
    }

    @GetMapping("getStudentByNo")
    public Result getStudentByNo(String studentNo){
        Student student = studentService.getOne(new QueryWrapper<Student>().eq("stu_no", studentNo));
        return Result.ok().data("student",student);
    }
}

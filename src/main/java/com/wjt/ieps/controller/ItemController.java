package com.wjt.ieps.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjt.ieps.entity.*;
import com.wjt.ieps.entity.vo.AddItem;
import com.wjt.ieps.entity.vo.AllItemQuery;
import com.wjt.ieps.result.Result;
import com.wjt.ieps.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wjt
 * @since 2022-05-05
 */
@RestController
@CrossOrigin
@RequestMapping("/ieps/item")
public class ItemController {
    @Value("${myfile.road}")
    String road;
    @Value("${file.location}")
    String location;
    @Autowired
    ItemService itemService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    FileService fileService;
    @Autowired
    UserItemService userItemService;
    @Autowired
    StudentService studentService;

    @PostMapping("addItem")
    public Result addItem(@RequestBody AddItem addItem){
        boolean b = itemService.addItem(addItem);
        if(b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @PostMapping("uploadFile")
    public Result uploadFile(MultipartFile file,String type) {
        List<String> list = itemService.uploadFileAvatar(file,type);
        return Result.ok().data("url",list.get(0)).data("files",list.get(1));
    }

    @DeleteMapping("deleteFile")
    public Result deleteFile(String url){
        java.io.File file = new java.io.File(url);
        // 删除文件
        boolean value = file.delete();
        if(value) {
            return Result.ok();
        }
        else {
            return Result.error();
        }
    }

    @GetMapping("getItem/{id}")
    public Result getItem(@PathVariable Integer id, String type){
        Item item = itemService.getItemById(id,type);
        return Result.ok().data("item",item);
    }

    @GetMapping("downItem")
    public Result downItem(@RequestParam Integer id, @RequestParam String type,HttpServletResponse response){
        itemService.downItem(id,type,response);
        return Result.ok();
    }

    @PostMapping("updateScore")
    public Result updateScore(@RequestBody Item item){
        boolean b = itemService.updateById(item);
        if(b){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @DeleteMapping("deleteById")
    public Result deleteById(Integer id){
        itemService.deleteById(id);
        return Result.ok();
    }

    @PostMapping("/list/{current}/{limit}")
    public Result getAllItem(@PathVariable long current, @PathVariable long limit,@RequestParam(required = false) String status,
                                @RequestBody(required = false) AllItemQuery itemQuery){
        Subject subject = SecurityUtils.getSubject();
        String principal = (String) subject.getPrincipal();
        //创建page对象
        Page<Item> pageInform = new Page<>(current,limit);
        //构建条件
        QueryWrapper<Item> wrapper = new QueryWrapper<>();
        if(subject.hasRole("院级管理员")){
            Teacher teacher = teacherService.getOne(new QueryWrapper<Teacher>().eq("teacher_no", principal));
            wrapper.eq("item_college",teacher.gettCollege());
        }else if(subject.hasRole("教师") || subject.hasRole("评委")) {
            Teacher teacher = teacherService.getOne(new QueryWrapper<Teacher>().eq("teacher_no", principal));
            List<UserItem> userItems = userItemService.list(new QueryWrapper<UserItem>().eq("user_num", teacher.getTeacherNo()));
            List<Integer> ids = new ArrayList<>();
            if (userItems != null) {
                for (UserItem userItem : userItems) {
                    ids.add(userItem.getItemId());
                }
                wrapper.in("id", ids);
            } else {
                wrapper.eq("id", 0);
            }
        }else if(subject.hasRole("学生")){
            Student student = studentService.getOne(new QueryWrapper<Student>().eq("stu_no", principal));
            List<UserItem> userItems = userItemService.list(new QueryWrapper<UserItem>().eq("user_num", student.getStuNo()));
            List<Integer> ids = new ArrayList<>();
            if(userItems != null) {
                for (UserItem userItem : userItems) {
                    ids.add(userItem.getItemId());
                }
                wrapper.in("id", ids);
            }else{
                wrapper.eq("id",0);
            }
        }
        if(itemQuery != null){
            String num = itemQuery.getItemNum();
            String category = itemQuery.getItemCategory();
            String college = itemQuery.getItemCollege();
            String level = itemQuery.getItemLevel();
            String name = itemQuery.getItemName();
            String date = itemQuery.getItemDate();
            String type = itemQuery.getItemType();
            String leaderName = itemQuery.getLeaderName();
            if(!StringUtils.isEmpty(num)){
                wrapper.like("item_Num",num);
            }
            if(!StringUtils.isEmpty(category)){
                wrapper.like("item_Category",category);
            }
            if(!StringUtils.isEmpty(college)){
                wrapper.like("item_College",college);
            }
            if(!StringUtils.isEmpty(level)){
                wrapper.like("item_Level",level);
            }
            if(!StringUtils.isEmpty(name)){
                wrapper.like("item_name",name);
            }
            if(!StringUtils.isEmpty(date)){
                wrapper.eq("item_date",date);
            }
            if(!StringUtils.isEmpty(type)){
                wrapper.eq("item_type",type);
            }
            if(!StringUtils.isEmpty(leaderName)){
                wrapper.like("leader_name",leaderName);
            }
        }
        if(status != null){
            wrapper.eq("item_status",status);
        }
        wrapper.orderByDesc("item_date");

        Page<Item> page = itemService.page(pageInform, wrapper);

        long total = page.getTotal();
        List<Item> records = page.getRecords();
        return Result.ok().data("total",total).data("rows",records);
    }

    @PostMapping("setScore")
    public Result setScore(@RequestParam Integer id,@RequestParam Integer score){
        itemService.setScore(id,score);
        return Result.ok();
    }

    @PostMapping("setPanels")
    public Result setPanels(@RequestParam Integer id,@RequestParam String group ,@RequestParam String type){
        itemService.setPanels(id,group,type);
        return Result.ok();
    }

    @PostMapping("setState")
    public Result setState(@RequestBody Item item){
        itemService.updateById(item);
        return Result.ok();
    }
}

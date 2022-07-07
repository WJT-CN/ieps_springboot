package com.wjt.ieps.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjt.ieps.entity.Inform;
import com.wjt.ieps.entity.Student;
import com.wjt.ieps.entity.vo.StudentQuery;
import com.wjt.ieps.result.Result;
import com.wjt.ieps.service.InformService;
import com.wjt.ieps.utils.OssUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wjt
 * @since 2022-04-30
 */
@RestController
@RequestMapping("/ieps/inform")
@CrossOrigin
public class InformController {
    @Value("${myfile.road}")
    String road;
    @Value("${file.location}")
    String location;
    @Autowired
    InformService informService;

    @PostMapping("/list/{current}/{limit}")
    public Result getInformList(@PathVariable long current, @PathVariable long limit,
                                 @RequestParam(required = false) String head){
        //创建page对象
        Page<Inform> pageInform = new Page<>(current,limit);
        //构建条件
        QueryWrapper<Inform> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(head)){
            wrapper.like("head",head);
        }
        wrapper.orderByDesc("pubdate");

        Page<Inform> page = informService.page(pageInform, wrapper);

        long total = page.getTotal();
        List<Inform> records = page.getRecords();
        return Result.ok().data("total",total).data("rows",records);
    }

    @DeleteMapping("{id}")
    public Result removeInform(@PathVariable String id) {
        boolean flag = informService.deleteInfo(id);
        if(flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @PostMapping("addInform")
    public Result addInform(@RequestBody Inform inform) {
        boolean save = informService.save(inform);
        if(save) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @GetMapping("getInform/{id}")
    public Result getInform(@PathVariable Integer id){
        Inform inform = informService.getById(id);
        return Result.ok().data("inform",inform);
    }

    @PostMapping("uploadInform")
    public Result uploadInform(@RequestBody Inform inform){
        boolean b = informService.updateById(inform);
        if(b){
            return Result.ok();
        }
        return Result.error();
    }

    @PostMapping("uploadFile")
    public Result uploadFile(MultipartFile file) {
        if (file.isEmpty()){
            Result.error();
        }
        String fileName = file.getOriginalFilename();
        String filePath = location+"/inform/";
        File imageFolder = new File(filePath);
        if(!imageFolder.exists()){
            imageFolder.mkdir();
        }
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        String storeAvatorPath = road+"/inform/"+fileName;
        try {
            file.transferTo(dest);
            return Result.ok().data("url",storeAvatorPath).data("files",fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @DeleteMapping("deleteFile")
    public Result deleteFile(String files){
        String url = location+"/inform/"+files;
        File file = new File(url);
        // 删除文件
        boolean value = file.delete();
        if(value) {
            return Result.ok();
        }
        else {
            return Result.error();
        }
    }

}

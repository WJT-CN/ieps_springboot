package com.wjt.ieps.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjt.ieps.entity.File;
import com.wjt.ieps.result.Result;
import com.wjt.ieps.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

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
@RequestMapping("/ieps/file")
public class FileController {
    @Autowired
    FileService fileService;

    @PostMapping("saveFile")
    public Result saveFile(@RequestBody File file){
        File one = fileService.getOne(new QueryWrapper<File>().eq("item_id", file.getItemId()).eq("file_type", file.getFileType()));
        boolean save = false;
        if(one == null){
            save = fileService.save(file);
        }else {
            file.setId(one.getId());
            save = fileService.updateById(file);
        }
        if (save){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
}

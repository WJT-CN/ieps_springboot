package com.wjt.ieps.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjt.ieps.entity.Cam;
import com.wjt.ieps.result.Result;
import com.wjt.ieps.service.CamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wjt
 * @since 2022-04-28
 */
@RestController
@RequestMapping("/ieps/cam")
@CrossOrigin
public class CamController {

    @Autowired
    private CamService camService;

    @GetMapping("/list")
    public Result getList(){
        QueryWrapper<Cam> wrapper = new QueryWrapper<Cam>().select("DISTINCT college");
        List<Cam> cams = camService.list(wrapper);
        return Result.ok().data("cams",cams);
    }

    @GetMapping("/major")
    public Result getListByCollege(@RequestParam String college){
        List<Cam> list = camService.list(new QueryWrapper<Cam>().eq("college", college));
        return Result.ok().data("majors",list);
    }
}

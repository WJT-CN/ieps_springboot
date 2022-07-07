package com.wjt.ieps.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjt.ieps.entity.Panels;
import com.wjt.ieps.entity.Teacher;
import com.wjt.ieps.entity.vo.AddPanels;
import com.wjt.ieps.entity.vo.TeacherQuery;
import com.wjt.ieps.result.Result;
import com.wjt.ieps.service.PanelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wjt
 * @since 2022-05-02
 */
@RestController
@RequestMapping("/ieps/panels")
@CrossOrigin
public class PanelsController {

    @Autowired
    PanelsService panelsService;

    @GetMapping("/list/{current}/{limit}")
    public Result getAllGroup (@PathVariable long current, @PathVariable long limit,
                               @RequestParam(required = false) String groupName){
        Page<Panels> pagePanel = new Page<>(current,limit);
        QueryWrapper<Panels> panelsQueryWrapper = new QueryWrapper<>();
        panelsQueryWrapper.select("DISTINCT `group`");
        if(!StringUtils.isEmpty(groupName)){
            panelsQueryWrapper.like("`group`",groupName);
        }
        Page<Panels> page = panelsService.page(pagePanel, panelsQueryWrapper);

        long total = page.getTotal();
        List<Panels> records = page.getRecords();
        return Result.ok().data("total",total).data("rows",records);
    }

    @PostMapping("addPanels")
    public Result addPanels(@RequestBody(required = false)AddPanels addPanels){
        if(!StringUtils.isEmpty(addPanels.getTeacherNo1())){
            Panels panels = new Panels();
            panels.setGroup(addPanels.getGroup());
            panels.setTeacherNo(addPanels.getTeacherNo1());
            panels.setName(addPanels.getTeacherName1());
            panelsService.save(panels);
        }
        if(!StringUtils.isEmpty(addPanels.getTeacherNo2())){
            Panels panels = new Panels();
            panels.setGroup(addPanels.getGroup());
            panels.setTeacherNo(addPanels.getTeacherNo2());
            panels.setName(addPanels.getTeacherName2());
            panelsService.save(panels);
        }
        if(!StringUtils.isEmpty(addPanels.getTeacherNo3())){
            Panels panels = new Panels();
            panels.setGroup(addPanels.getGroup());
            panels.setTeacherNo(addPanels.getTeacherNo3());
            panels.setName(addPanels.getTeacherName3());
            panelsService.save(panels);
        }
        if(!StringUtils.isEmpty(addPanels.getTeacherNo4())){
            Panels panels = new Panels();
            panels.setGroup(addPanels.getGroup());
            panels.setTeacherNo(addPanels.getTeacherNo4());
            panels.setName(addPanels.getTeacherName4());
            panelsService.save(panels);
        }
        if(!StringUtils.isEmpty(addPanels.getTeacherNo5())){
            Panels panels = new Panels();
            panels.setGroup(addPanels.getGroup());
            panels.setTeacherNo(addPanels.getTeacherNo5());
            panels.setName(addPanels.getTeacherName5());
            panelsService.save(panels);
        }
        if(!StringUtils.isEmpty(addPanels.getTeacherNo7())){
            Panels panels = new Panels();
            panels.setGroup(addPanels.getGroup());
            panels.setTeacherNo(addPanels.getTeacherNo7());
            panels.setName(addPanels.getTeacherName7());
            panelsService.save(panels);
        }
        if(!StringUtils.isEmpty(addPanels.getTeacherNo6())){
            Panels panels = new Panels();
            panels.setGroup(addPanels.getGroup());
            panels.setTeacherNo(addPanels.getTeacherNo6());
            panels.setName(addPanels.getTeacherName6());
            panelsService.save(panels);
        }
        return Result.ok();
    }

    @GetMapping("getPanelInfo")
    public Result getPanelInfo(String group){
        QueryWrapper<Panels> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("`group`",group);
        List<Panels> list = panelsService.list(queryWrapper);
        return Result.ok().data("panels",list);
    }

    @DeleteMapping("deletePanel")
    public Result deletePanel(String group){
        QueryWrapper<Panels> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("`group`",group);
        boolean b = panelsService.remove(queryWrapper);
        if(b){
            return Result.ok();
        }
        return Result.error().message("删除失败");
    }

    @GetMapping("/list")
    public Result list (){
        QueryWrapper<Panels> panelsQueryWrapper = new QueryWrapper<>();
        panelsQueryWrapper.select("DISTINCT `group`");
        List<Panels> list = panelsService.list(panelsQueryWrapper);
        return Result.ok().data("panels",list);
    }
}
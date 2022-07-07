package com.wjt.ieps.controller;

import com.wjt.ieps.entity.vo.AddChange;
import com.wjt.ieps.result.Result;
import com.wjt.ieps.service.ChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wjt
 * @since 2022-06-03
 */
@Controller
@RequestMapping("/ieps/change")
@CrossOrigin
public class ChangeController {
    @Autowired
    ChangeService changeService;

    @PostMapping("addChange")
    public Result addChange(@RequestBody AddChange addChange){
        changeService.add(addChange);
        return Result.ok();
    }
}

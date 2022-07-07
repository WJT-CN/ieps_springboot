package com.wjt.ieps.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjt.ieps.entity.*;
import com.wjt.ieps.entity.vo.AllItemQuery;
import com.wjt.ieps.entity.vo.ReviewQuery;
import com.wjt.ieps.result.Result;
import com.wjt.ieps.service.ItemService;
import com.wjt.ieps.service.ReviewService;
import com.wjt.ieps.service.TeacherService;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.sql.Date;
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
@RequestMapping("/ieps/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    ItemService itemService;

    @PostMapping("/list/{current}/{limit}")
    public Result getAllItem(@PathVariable long current, @PathVariable long limit,
                             @RequestBody(required = false) ReviewQuery reviewQuery){
        Subject subject = SecurityUtils.getSubject();
        String principal = (String) subject.getPrincipal();
        //创建page对象
        Page<Review> reviewPage = new Page<>(current,limit);
        //构建条件
        QueryWrapper<Review> wrapper = new QueryWrapper<>();
        if(subject.hasRole("评委")){
            Teacher teacher = teacherService.getOne(new QueryWrapper<Teacher>().eq("teacher_no", principal));
            wrapper.eq("user_num",teacher.getTeacherNo());
        }
        if(reviewQuery != null){
            String itemName = reviewQuery.getItemName();
            String reviewOption = reviewQuery.getReviewOption();
            String reviewType = reviewQuery.getReviewType();
            Date reviewTime = reviewQuery.getReviewTime();
            if(!StringUtils.isEmpty(itemName)){
                List<Item> lists = itemService.list(new QueryWrapper<Item>().like("item_name", itemName));
                if(lists.size() == 0){
                    wrapper.eq("item_Num","xxxxxxxx");
                }else{
                    ArrayList<Integer> ids = new ArrayList<>();
                    for (Item list:lists) {
                        ids.add(list.getId());
                    }
                    wrapper.in("item_Num",ids);
                }
            }
            if(!StringUtils.isEmpty(reviewOption)){
                wrapper.eq("review_option",reviewOption);
            }
            if(!StringUtils.isEmpty(reviewType)){
                wrapper.eq("review_type",reviewType);
            }
            if(!StringUtils.isEmpty(reviewTime)){
                wrapper.ge("review_time",reviewTime);
            }
        }
        wrapper.orderByDesc("review_time");
        Page<Review> page = reviewService.page(reviewPage, wrapper);

        long total = page.getTotal();
        List<Review> records = page.getRecords();
        List<Review> list = new ArrayList<>();
        for (Review review:records) {
            Item item = itemService.getById(review.getItemNum());
            review.setItem(item);
            list.add(review);
        }
        return Result.ok().data("total",total).data("rows",list);
    }

    @GetMapping("getReviewInfo")
    public Result getReviewInfo(Integer id){
        Review review = reviewService.getById(id);
        Item item = itemService.getById(review.getItemNum());
        review.setItem(item);
        return Result.ok().data("review",review);
    }

    @PostMapping("updateReview")
    public Result updateReview(@RequestBody Review review){
        boolean b = reviewService.updateById(review);
        if(b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

}

package com.wjt.ieps.service.impl;

import com.wjt.ieps.entity.Review;
import com.wjt.ieps.mapper.ReviewMapper;
import com.wjt.ieps.service.ReviewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjt
 * @since 2022-05-05
 */
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {

}

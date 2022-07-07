package com.wjt.ieps.service;

import com.wjt.ieps.entity.Change;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wjt.ieps.entity.vo.AddChange;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wjt
 * @since 2022-06-03
 */
public interface ChangeService extends IService<Change> {

    void add(AddChange addChange);
}

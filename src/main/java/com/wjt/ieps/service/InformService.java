package com.wjt.ieps.service;

import com.wjt.ieps.entity.Inform;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wjt
 * @since 2022-04-30
 */
public interface InformService extends IService<Inform> {

    boolean deleteInfo(String id);
}

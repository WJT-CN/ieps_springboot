package com.wjt.ieps.service.impl;

import com.wjt.ieps.entity.File;
import com.wjt.ieps.mapper.FileMapper;
import com.wjt.ieps.service.FileService;
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
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

}

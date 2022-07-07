package com.wjt.ieps.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.wjt.ieps.entity.Inform;
import com.wjt.ieps.mapper.InformMapper;
import com.wjt.ieps.service.InformService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjt.ieps.utils.ConstantPropertiesUtils;
import com.wjt.ieps.utils.OssUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjt
 * @since 2022-04-30
 */
@Service
public class InformServiceImpl extends ServiceImpl<InformMapper, Inform> implements InformService {
    @Value("${myfile.road}")
    String road;
    @Value("${file.location}")
    String location;

    @Override
    public boolean deleteInfo(String id) {
        Inform inform = baseMapper.selectById(id);
        boolean result = false;
        String files = inform.getFiles();
        if(!StringUtils.isEmpty(files)){
            String url = location+"/inform/"+files;
            File file = new File(url);
            // 删除文件
            boolean value = file.delete();
            if(!value){
               return false;
            }
        }
        int i = baseMapper.deleteById(id);
        if(i>0){
            return true;
        }
        return false;
    }
}

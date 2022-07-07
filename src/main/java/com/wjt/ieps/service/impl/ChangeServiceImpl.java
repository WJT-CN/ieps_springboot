package com.wjt.ieps.service.impl;

import com.wjt.ieps.entity.Change;
import com.wjt.ieps.entity.UserChange;
import com.wjt.ieps.entity.vo.AddChange;
import com.wjt.ieps.mapper.ChangeMapper;
import com.wjt.ieps.service.ChangeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjt.ieps.service.UserChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjt
 * @since 2022-06-03
 */
@Service
public class ChangeServiceImpl extends ServiceImpl<ChangeMapper, Change> implements ChangeService {

    @Autowired
    UserChangeService userChangeService;

    @Override
    public void add(AddChange addChange) {
        Change change = new Change();
        change.setItemId(addChange.getItemId());
        change.setCollege(addChange.getCollege());
        change.setType(addChange.getType());
        change.setReason(addChange.getReason());
        change.setNote(addChange.getNote());
        baseMapper.insert(change);
        if(addChange.getType().equals("成员变更")){
            UserChange userChange = new UserChange();
            userChange.setItemId(addChange.getItemId());
            userChange.setUsername(addChange.getLeader());
            userChange.setIdentity(1);
            userChangeService.save(userChange);
            userChange.setIdentity(2);
            if(!StringUtils.isEmpty(addChange.getStu1())){
                userChange.setId(null);
                userChange.setUsername(addChange.getStu1());
                userChangeService.save(userChange);
            }
            if(!StringUtils.isEmpty(addChange.getStu2())){
                userChange.setId(null);
                userChange.setUsername(addChange.getStu2());
                userChangeService.save(userChange);
            }
            if(!StringUtils.isEmpty(addChange.getStu3())){
                userChange.setId(null);
                userChange.setUsername(addChange.getStu3());
                userChangeService.save(userChange);
            }
            if(!StringUtils.isEmpty(addChange.getStu4())){
                userChange.setId(null);
                userChange.setUsername(addChange.getStu4());
                userChangeService.save(userChange);
            }
        }
        if(addChange.getType().equals("指导老师变更")){
            UserChange userChange = new UserChange();
            userChange.setItemId(addChange.getItemId());
            userChange.setIdentity(3);
            userChange.setUsername(addChange.getTea1());
            userChangeService.save(userChange);
            if(!StringUtils.isEmpty(addChange.getTea2())){
                userChange.setId(null);
                userChange.setUsername(addChange.getTea2());
                userChangeService.save(userChange);
            }
            if(!StringUtils.isEmpty(addChange.getTea3())){
                userChange.setId(null);
                userChange.setUsername(addChange.getTea3());
                userChangeService.save(userChange);
            }
        }

    }
}

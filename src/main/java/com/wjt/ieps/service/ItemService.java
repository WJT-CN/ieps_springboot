package com.wjt.ieps.service;

import com.wjt.ieps.entity.Item;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wjt.ieps.entity.vo.AddItem;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wjt
 * @since 2022-05-05
 */
public interface ItemService extends IService<Item> {

    boolean addItem(AddItem addItem);

    List<String> uploadFileAvatar(MultipartFile file,String type);

    Item getItemById(Integer id, String type);

    void deleteById(Integer id);

    void setScore(Integer id,Integer score);

    void setPanels(Integer id, String group,String type);

    void downItem(Integer id, String type, HttpServletResponse response);
}

package com.wjt.ieps.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjt.ieps.entity.*;
import com.wjt.ieps.entity.File;
import com.wjt.ieps.entity.vo.AddItem;
import com.wjt.ieps.mapper.ItemMapper;
import com.wjt.ieps.result.Result;
import com.wjt.ieps.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjt.ieps.utils.OssUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjt
 * @since 2022-05-05
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {
    @Value("${myfile.road}")
    String road;
    @Value("${file.location}")
    String location;
    @Autowired
    UserItemService userItemService;
    @Autowired
    FileService fileService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    PanelsService panelsService;

    @Override
    public boolean addItem(AddItem addItem) {
        if(addItem != null){
            Item item = new Item();
            item.setItemName(addItem.getItemName());
            item.setItemType(addItem.getItemType());
            item.setItemTerritory(addItem.getItemTerritory());
            item.setLeaderName(addItem.getLeaderName());
            item.setLeaderNum(addItem.getLeaderNum());
            item.setItemCollege(addItem.getItemCollege());
            item.setSummary(addItem.getSummary());
            item.setLeaderPhone(addItem.getLeaderPhone());
            item.setItemCategory(addItem.getItemCategory());
            String year = Calendar.getInstance().get(Calendar.YEAR)+"";
            item.setItemDate(year);
            int insert = baseMapper.insert(item);
            UserItem userItem = new UserItem();
            userItem.setItemId(item.getId());
            userItem.setUserNum(addItem.getLeaderNum());
            userItem.setIdentity(1);
            userItemService.save(userItem);
            userItem.setId(null);
            userItem.setUserNum(addItem.getTeamNum1());
            userItem.setIdentity(2);
            userItemService.save(userItem);
            if(!StringUtils.isEmpty(addItem.getTeamNum2())){
                userItem.setId(null);
                userItem.setUserNum(addItem.getTeamNum2());
                userItemService.save(userItem);
            }
            if(!StringUtils.isEmpty(addItem.getTeamNum3())){
                userItem.setId(null);
                userItem.setUserNum(addItem.getTeamNum3());
                userItemService.save(userItem);
            }
            if(!StringUtils.isEmpty(addItem.getTeamNum4())){
                userItem.setId(null);
                userItem.setUserNum(addItem.getTeamNum4());
                userItemService.save(userItem);
            }
            userItem.setId(null);
            userItem.setUserNum(addItem.getTeacherNo1());
            userItem.setIdentity(3);
            userItemService.save(userItem);
            if(!StringUtils.isEmpty(addItem.getTeacherNo2())){
                userItem.setId(null);
                userItem.setUserNum(addItem.getTeacherNo2());
                userItemService.save(userItem);
            }
            if(!StringUtils.isEmpty(addItem.getTeacherNo3())){
                userItem.setId(null);
                userItem.setUserNum(addItem.getTeacherNo3());
                userItemService.save(userItem);
            }
            File file = new File();
            file.setItemId(item.getId());
            file.setFileName(addItem.getFileName());
            file.setFileUrl(addItem.getFileUrl());
            file.setFileType("立项材料");
            fileService.save(file);
            return true;
        }
        return false;
    }

    @Override
    public List<String> uploadFileAvatar(MultipartFile file,String type) {
        if (file.isEmpty()){
            return null;
        }
        List<String> list = new ArrayList<>();
        String datePath = new DateTime().toString("yyyy");
        String fileName = file.getOriginalFilename();
        String filePath = location+"/project/"+datePath+"/"+type+"/";
        java.io.File imageFolder = new java.io.File(filePath);
        if(!imageFolder.exists()){
            imageFolder.mkdirs();
        }
        java.io.File dest = new java.io.File(filePath+System.getProperty("file.separator")+fileName);
        String storeAvatorPath = road+"/inform/"+fileName;
        try {
            file.transferTo(dest);
            list.add(imageFolder+"\\"+fileName);
            list.add(fileName);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
//        List<String> list = OssUtils.uploadFileAvatar(file,"立项材料");
//        return list;
    }

    @Override
    public Item getItemById(Integer id, String type) {
        Item item = baseMapper.selectById(id);
        File file = fileService.getOne(new QueryWrapper<File>().eq("item_id", id).eq("file_type",type));
        item.setFile(file);
        List<UserItem> userItemList = userItemService.list(new QueryWrapper<UserItem>().eq("item_id", id));
        String students = "";
        String teachers = "";
        for (UserItem userItem:userItemList) {
            if (userItem.getIdentity()==2){
                Student stu_no = studentService.getOne(new QueryWrapper<Student>().eq("stu_no", userItem.getUserNum()));
                students = students + stu_no.getName()+"  ";
            }
            if (userItem.getIdentity()==3){
                Teacher teacher_no = teacherService.getOne(new QueryWrapper<Teacher>().eq("teacher_no", userItem.getUserNum()));
                teachers = teachers + teacher_no.gettName()+" ";
            }
        }
        item.setStudent(students);
        item.setTeacher(teachers);
        return item;
    }

    @Override
    public void deleteById(Integer id) {
        List<File> files = fileService.list(new QueryWrapper<File>().eq("item_id", id));
        for(File file : files){
            String url = file.getFileUrl();
            java.io.File file1 = new java.io.File(url);
            boolean value = file1.delete();
        }
        fileService.remove(new QueryWrapper<File>().eq("item_id",id));
        userItemService.remove(new QueryWrapper<UserItem>().eq("item_id",id));
        baseMapper.deleteById(id);

    }

    @Override
    public void setScore(Integer id,Integer score) {
        Subject subject = SecurityUtils.getSubject();
        String principal = (String) subject.getPrincipal();
//        Item item = new Item();
//        baseMapper.updateById(item);
        Review one = reviewService.getOne(new QueryWrapper<Review>().eq("item_num", id).eq("review_type", "立项评审").eq("review_level", "院级评审"));
        if(one == null){
            Review review = new Review();
            review.setItemNum(id);
            review.setReviewScore(score);
            review.setReviewType("立项评审");
            review.setReviewLevel("院级评审");
            review.setUserNum(principal);
            reviewService.save(review);
        }else {
            one.setReviewScore(score);
            reviewService.updateById(one);
        }

    }

    @Override
    public void setPanels(Integer id, String group,String type) {
        List<Panels> panels = panelsService.list(new QueryWrapper<Panels>().eq("`group`", group));

        QueryWrapper<Review> wapper = new QueryWrapper<Review>().eq("item_num", id).eq("review_type", type).eq("review_level", "校级评审");
        List<Review> list = reviewService.list(wapper);
        if(list != null){
            reviewService.remove(wapper);
        }
        for (Panels panel:panels) {
            Review review = new Review();
            review.setItemNum(id);
            review.setUserNum(panel.getTeacherNo());
            review.setReviewType(type);
            review.setReviewLevel("校级评审");
            reviewService.save(review);
        }
        Item item = new Item();
        item.setId(id);
        if(type.equals("立项评审")){
            item.setItemAssign(1);
        }
        if(type.equals("中期评审")){
            item.setItemAssign(2);
        }
        if(type.equals("结题评审")){
            item.setItemAssign(3);
        }
        baseMapper.updateById(item);
    }

    @Override
    public void downItem(Integer id, String type, HttpServletResponse response) {
        File file = fileService.getOne(new QueryWrapper<File>().eq("item_id", id).eq("file_type", type));
        if(file == null){
            return;
        }
        String url = file.getFileUrl();
        String fileName = file.getFileName();
        if (url != null) {
            java.io.File file1 = null;
            //设置文件路径
            file1 = new java.io.File(url);

            try {
                fileName = new String(file.getFileName().getBytes("GBK"),"ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (file1.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file1);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally { // 做关闭操作
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


}

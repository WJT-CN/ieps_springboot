package com.wjt.ieps.service;

import com.wjt.ieps.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wjt
 * @since 2022-04-25
 */
public interface TeacherService extends IService<Teacher> {

    boolean deleteTeacherById(String id);

    void saveSubject(MultipartFile file, TeacherService teacherService, UserService userService, UserRoleService userRoleService);
}

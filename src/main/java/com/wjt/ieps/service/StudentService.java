package com.wjt.ieps.service;

import com.wjt.ieps.entity.Student;
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
public interface StudentService extends IService<Student> {

    void saveSubject(MultipartFile file, StudentService studentService, UserService userService, UserRoleService userRoleService);

    boolean deleteStudent(String id);
}

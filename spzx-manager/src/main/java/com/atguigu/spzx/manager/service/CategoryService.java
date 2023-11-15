package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    //查询分类。每次查一层数据
    List<Category> findCategoryList(Long id);

    //文件导出
    void exportData(HttpServletResponse response);
    //文件导入
    void importData(MultipartFile file);
}

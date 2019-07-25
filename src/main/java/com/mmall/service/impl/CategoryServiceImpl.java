package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    // 新增分类
    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {

        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("添加分类参数错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true); // 该分类是可用的

        int rowCount = categoryMapper.insert(category);

        if (rowCount > 0) { // 成功添加分类
            return ServerResponse.createBySuccess("添加分类成功");
        }

        return ServerResponse.createByErrorMessage("添加分类失败");

    }

    // 更新分类名字
    @Override
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {

        if (categoryId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("更新分类名字参数错误");
        }

        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);

        if (rowCount > 0) { // 更新成功
            return ServerResponse.createBySuccessMessage("更新分类名字成功");
        }

        return ServerResponse.createByErrorMessage("更新分类名字失败");

    }

    // 获取平级的子category信息
    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {

        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);

        if (CollectionUtils.isEmpty(categoryList)) { // 子category有可能为空，并不是错误
            logger.info("未找到当前分类的子分类");
        }

        return ServerResponse.createBySuccess(categoryList);

    }

    // 递归获取子category信息
    @Override
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {

        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet, categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            for (Category categoryItem : categorySet) {
                categoryIdList.add(categoryItem.getId());
            }
        }

        return ServerResponse.createBySuccess(categoryIdList);

    }

    // 递归得到子节点的helper方法
    private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId) {

        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        // 查找子category
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        // MyBatis在没有查找到内容时，也不会使categoryList为null，因此此处不用判断是否为null
        for (Category categoryItem : categoryList) {
            findChildCategory(categorySet, categoryItem.getId());
        }
        return categorySet;

    }

}

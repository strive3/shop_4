package com.mine.service.impl;
import com.mine.common.ServerResponse;
import com.mine.dao.CategoryMapper;
import com.mine.pojo.Category;
import com.mine.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author 杜晓鹏
 * @create 2019-01-07 14:51
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public ServerResponse get_category(Integer categoryId) {
        //非空判断
        if (categoryId == null || categoryId.equals(""))
            return ServerResponse.serverResponseError("类别id不能为空");

//        Category category = categoryMapper.selectByPrimaryKey(categoryId);
//        if (category == null)
//            return ServerResponse.serverResponseError("查询的类别不存在！");
        //查询子类别
        List<Category> categories = categoryMapper.findChildCategory(categoryId);

        if (categories == null || categories.size() == 0)
            return ServerResponse.serverResponseError("查询的类别没有子类别");

        return ServerResponse.serverResponseSuccess(categories);

    }

    @Override
    public ServerResponse add_category(Integer parentId, String categoryName) {
        //非空判断
        if (categoryName == null || categoryName.equals(""))
            return ServerResponse.serverResponseError("类别名字不能为空");

        Category category = new Category();
        category.setParentId(parentId);
        category.setName(categoryName);
        category.setStatus(true);

        int result = categoryMapper.insert(category);
        if (result > 0)
            return ServerResponse.serverResponseSuccess();
        return ServerResponse.serverResponseError("添加失败");
    }

    @Override
    public ServerResponse set_category_name(Integer categoryId, String categoryName) {
        //非空判断
        if (categoryId == null || categoryId.equals(""))
            return ServerResponse.serverResponseError("类别id不能为空");
        if (categoryName == null || categoryName.equals(""))
            return ServerResponse.serverResponseError("类别名字不能为空");

        //查找到当前类别
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category == null)
            return ServerResponse.serverResponseError("要修改的类别不存在");

        //修改名字
        category.setName(categoryName);
        int result = categoryMapper.updateByPrimaryKey(category);
        if (result > 0)
            return ServerResponse.serverResponseSuccess();

        return ServerResponse.serverResponseError("修改失败");
    }

    @Override
    public ServerResponse get_deep_category(Integer categoryId) {

        Set<Category> categories = new HashSet<>();
        selectAllCategory(categories,categoryId);

        //将查出来的所有类别的id加到新的集合中
        Set<Integer> integerSet = new HashSet<>();
        Iterator<Category> iterator = categories.iterator();
        while (iterator.hasNext()){
            Category category = iterator.next();
            integerSet.add(category.getId());
        }
        return ServerResponse.serverResponseSuccess(integerSet);
    }

    //递归查询出所有的类别
    private Set<Category> selectAllCategory(Set<Category> categories , Integer categoryId){
        //先将当前查找到的category添加到集合中
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null)
            categories.add(category);

        //查出当前类别的子类别集合
        List<Category> childCategory = categoryMapper.findChildCategory(categoryId);
        //如果子类别的集合为空那么返回    不为空的话执行
        if (childCategory != null && childCategory.size() > 0){
            for (Category category1:childCategory){
                //拿到当前类别的id作为parentId继续查找
                selectAllCategory(categories,category1.getId());
                categories.add(category1);
            }

        }
        return categories;
    }
}

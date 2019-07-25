package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    // 增加分类
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value="parentId", defaultValue = "0") int parentId) {

        // 用户校验
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) { // 用户未登录
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }

        // 判断登录用户是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) { // 是管理员
            // 增加分类
            return iCategoryService.addCategory(categoryName, parentId);
        } else {
            return ServerResponse.createByErrorMessage("无权限，需要管理员权限以执行改操作");
        }

    }

    // 更新分类名字
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName) {

        // 用户校验
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) { // 用户未登录
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }

        // 判断登录用户是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) { // 是管理员
            // 更新分类名字
            return iCategoryService.updateCategoryName(categoryId, categoryName);
        } else {
            return ServerResponse.createByErrorMessage("无权限，需要管理员权限以执行改操作");
        }

    }

    // 获取平级的子category信息，不递归
    // parentId为默认值0时，表明该category没有父category
    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {

        // 用户校验
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) { // 用户未登录
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }

        // 判断登录用户是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) { // 是管理员
            // 获取平级的子category信息，无递归
            return iCategoryService.getChildrenParallelCategory(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("无权限，需要管理员权限以执行改操作");
        }

    }

    // 获取递归的子category信息
    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {

        // 用户校验
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) { // 用户未登录
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }

        // 判断登录用户是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) { // 是管理员
            // 获取平级的子category信息，有递归
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("无权限，需要管理员权限以执行改操作");
        }

    }

}

package org.com.drag.web.controller;

import org.apache.poi.ss.formula.functions.T;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.model.Node;
import org.com.drag.model.Template;
import org.com.drag.model.TemplateCategory;
import org.com.drag.model.User;
import org.com.drag.service.TemplateCategoryService;
import org.com.drag.service.TemplateService;
import org.com.drag.service.oozie.bean.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guzhendong on 2017/10/19 0019.
 */
@Controller
@RequestMapping("/templateCategory")
public class TemplateCategoryController {
    @Autowired
    private TemplateCategoryService templateCategoryService;

    @Autowired
    private TemplateService templateService;

    @RequestMapping("shownode")
    @ResponseBody
    public ResponseResult showNode(String id, String name, String pId,boolean isParent, HttpSession session){
//    public ResponseResult showNode(String idStr, String name, String pidStr,boolean isParent, HttpSession session){
        User user = (User) session.getAttribute(Constants.USER_KEY);
//        String	sid = idStr.substring(1);

        TemplateCategory templateCategory = new TemplateCategory();
        if(null != id && !"".equals(id)){
            templateCategory.setTemplateCategoryPid(Integer.parseInt(id));
        }else {
            templateCategory.setTemplateCategoryPid(null);
        }
        templateCategory.setUserid(user.getId());
        List<TemplateCategory> templateCategoryList = templateCategoryService.selectAll(templateCategory);
        Template template = new Template();

        if(null != id && !"".equals(id)){
            template.setTcid(Integer.parseInt(id));
        }else {
            template.setTcid(null);
        }
        template.setUserid(user.getId());
        List<Template> templateList = templateService.selectAll(template);
                List<Node> nodes=new ArrayList<Node>();
        for (TemplateCategory templateCategory1 : templateCategoryList) {
            Node node = new Node();
            node.setId(Long.valueOf(templateCategory1.getTemplateCategoryId()));
            if(null == templateCategory1.getTemplateCategoryPid()){
                node.setpId(null);
            }else {
                node.setpId(Long.valueOf(templateCategory1.getTemplateCategoryPid()));
            }

//            node.setIdStr(templateCategory1.getTemplateCategoryId().toString());
//            node.setPidStr(templateCategory1.getTemplateCategoryPid().toString());
            node.setName(templateCategory1.getTemplateCategoryName());
            node.setIsParent(true);
            nodes.add(node);

        }
//        for (int i=0;i<templateCategoryList.size();i++){
//            long Sid=templateCategoryList.get(i).getTemplateCategoryId();
//            long pid=templateCategoryList.get(i).getTemplateCategoryPid();
//            String Sname=templateCategoryList.get(i).getTemplateCategoryName();
//                String node="{id:'"+"p"+Sid+"',pId:'"+"p"+pid+"',name:'"+Sname+"',isParent:"+true+"}";
//                nodes.add(node);
//
//        }
        for (Template template1 : templateList) {
            Node node = new Node();
            node.setId(Long.valueOf(template1.getTemplateId()));
            node.setpId(Long.valueOf(template1.getTcid()));
//            node.setIdStr(template1.getTemplateId().toString());
//            node.setPidStr(template1.getTcid().toString());
            node.setName(template1.getTemplateName());
            node.setIsParent(false);
            nodes.add(node);
        }
//        for (int i=0;i<templateList.size();i++){
//            long Sid=templateList.get(i).getTemplateId();
//            long pid=templateList.get(i).getTcid();
//            String Sname=templateList.get(i).getTemplateCategoryName();
//            String node="{id:'"+'c'+Sid+"',pId:'"+'p'+pid+"',name:'"+Sname+"',isParent:"+false+"}";
//
//            nodes.add(node);
//
//        }
        return new ResponseResult(HttpStatus.OK,"加载成功",nodes);
    }

    @RequestMapping(value = "/selectAllTemplateCategory")
    @ResponseBody
    public List<TemplateCategory> selectAllTemplateCategory(TemplateCategory tem){
        List<TemplateCategory> templateCategoryList = templateCategoryService.selectAll( tem);
        return templateCategoryList;
    }

    @ResponseBody
    @RequestMapping("addTemplateCategory")
    public ResponseResult addTemplateCategory(TemplateCategory templateCategory, HttpSession session){
        User user = (User) session.getAttribute(Constants.USER_KEY);
        if(null == user){
            return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "用户未登录");
        }
        templateCategory.setUserid(user.getId());
        if(null == templateCategory.getTemplateCategoryPid() || "".equals(templateCategory.getTemplateCategoryPid())){
            templateCategory.setTemplateCategoryPid(null);
        }
        List<TemplateCategory> list =  templateCategoryService.selectAll(templateCategory);
        for (int i = 0; i <list.size() ; i++) {
            if(null == list.get(i).getTemplateCategoryPid() && null == templateCategory.getTemplateCategoryPid()){
                if(list.get(i).getTemplateCategoryName().equals(templateCategory.getTemplateCategoryName())){
                    return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "名称重复了");
                }
            }else{
                if(list.get(i).getTemplateCategoryName().equals(templateCategory.getTemplateCategoryName()) && list.get(i).getTemplateCategoryPid().equals(templateCategory.getTemplateCategoryPid())){
                    return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "名称重复了");
                }
            }

        }
        templateCategoryService.insert(templateCategory);
        return new ResponseResult(HttpStatus.OK, "添加成功");
    }

    @RequestMapping("delTemplateCategory")
    @ResponseBody
    public ResponseResult delTemplateCategory(String templateCategoryIds, HttpSession session){
        User user = (User) session.getAttribute(Constants.USER_KEY);
        if(null == user){
            return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "用户未登录");
        }
        String[] templateCategoryId = templateCategoryIds.split(",");
        Integer integer = templateCategoryService.deleteById(templateCategoryId);
        if (0 != integer) {
            return new ResponseResult(HttpStatus.OK, "删除成功");
        } else {
            return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "删除失败");
        }
    }

    @RequestMapping("updateTemplateCategory")
    @ResponseBody
    public ResponseResult updataTemplateCategory(TemplateCategory templateCategory, HttpSession session){
        User user = (User) session.getAttribute(Constants.USER_KEY);
        if(null == user){
            return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "用户未登录");
        }
        List<TemplateCategory> list =  templateCategoryService.selectAll(templateCategory);
        for (int i = 0; i <list.size() ; i++) {
            if(null == list.get(i).getTemplateCategoryPid() && null == templateCategory.getTemplateCategoryPid()){
                if(list.get(i).getTemplateCategoryName().equals(templateCategory.getTemplateCategoryName())){
                    return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "名称重复了");
                }
            }else{
                if(list.get(i).getTemplateCategoryName().equals(templateCategory.getTemplateCategoryName()) && list.get(i).getTemplateCategoryPid().equals(templateCategory.getTemplateCategoryPid())){
                    return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "名称重复了");
                }
            }
        }
        templateCategoryService.updateByPrimaryKeySelective(templateCategory);
        return new ResponseResult(HttpStatus.OK, "修改成功");
    }
}

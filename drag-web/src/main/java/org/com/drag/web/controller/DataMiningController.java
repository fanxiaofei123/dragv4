package org.com.drag.web.controller;

import org.com.drag.common.page.PageBean;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.common.util.StringUtils;
import org.com.drag.model.*;
import org.com.drag.service.DataMiningCategoryService;
import org.com.drag.service.DataMiningService;
import org.com.drag.service.WorkFlowService;
import org.com.drag.service.WorkSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/drag/dataMining")
public class DataMiningController {

    /**
     * 每页显示数据条数
     */
    private static final Integer PAGE_NUMBER = 6;
    @Autowired
    private DataMiningService dataMiningService;

    @Autowired
    private DataMiningCategoryService dataMiningCategoryService;

    @Autowired
    private WorkSpaceService workSpaceService;

    @Autowired
    private WorkFlowService workFlowService;

    /**
     * 先查询出父节点相关集合，在遍历出子节点id集合
     * @param list
     * @param parent_id
     * @param returnList
     * @return
     */
    public static List<Integer> getChildNodes(List<DataMiningCategory> list, Integer parent_id, List returnList) {
        if(list == null && parent_id == null) return null;
        for (Iterator<DataMiningCategory> iterator = list.iterator(); iterator.hasNext();) {
            DataMiningCategory node = (DataMiningCategory) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (parent_id.equals(node.getDataMiningCategoryPid())) {
                returnList.add(node.getDataMiningCategoryId());
                //递归遍历子后子
                getChildNodes(list, node.getDataMiningCategoryId(), returnList);
            }
        }
        return returnList;
    }

    /**
     * 递归分页查询文件
     * @param dataMiningCategory
     * @param session
     * @param page
     * @param rowCount
     * @return
     */
    @RequestMapping("selectDeepDataMiningCategory")
    @ResponseBody
    public PageBean getAllDataMiningCategoryList(DataMiningCategory dataMiningCategory, HttpSession session, Integer page, Integer rowCount) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (rowCount == null) {
            rowCount = PAGE_NUMBER;
        }
        User user = (User) session.getAttribute(Constants.USER_KEY);
        dataMiningCategory.setUserid(user.getId());

        List<DataMiningCategory> menuList = dataMiningCategoryService.selectAll(new DataMiningCategory());
       List<Integer> ids = getChildNodes(menuList,dataMiningCategory.getDataMiningCategoryId(),new ArrayList());
       ids.add(dataMiningCategory.getDataMiningCategoryId());

        List<DataMining> list = new ArrayList<DataMining>();

        for(Integer id : ids ){
            List<DataMining> dataMinings = dataMiningService.getManyDataMining(id);
            for (DataMining dataMining : dataMinings){
                list.add(dataMining);
            }
        }

        List filelist = new ArrayList<>();
        int startItem = (page - 1) * rowCount;
        int endItem = page * rowCount - 1;
        int totalPage = 0;
        if (rowCount > list.size()) {
            totalPage = 1;
        } else {
            totalPage = list.size() % rowCount == 0 ? list.size() / rowCount : list.size() / rowCount + 1;
        }
        if (endItem > list.size() - 1) {
            endItem = list.size() - 1;
        }
        PageBean pageBean = new PageBean();

        pageBean.setTotal(list.size());
        pageBean.setTotalPage(totalPage);
        pageBean.setCurPage(page);
        if(page <= 0 & page == null){
            pageBean.setPrevious(1);
        }else{
            pageBean.setPrevious(page-1);
        }
        if (page >= totalPage){
            pageBean.setNext(totalPage);
        }else {
            pageBean.setNext(page+1);
        }
        for (int i = startItem; i <= endItem; i++) {
            filelist.add(list.get(i));
        }
        pageBean.setRows(filelist);
        return pageBean;
    }

    /**
     * 根据数据名称 模糊搜索
     * @param categoryName
     * @return
     */
    @RequestMapping(value = "getManyOrOne",method = RequestMethod.GET)
    @ResponseBody
    public PageBean getManyOrOneDataMining(HttpSession session,String categoryName, Integer page, Integer rowCount){
        User user = (User) session.getAttribute(Constants.USER_KEY);
        if (page == null || page <= 0) {
            page = 1;
        }
        if (rowCount == null) {
            rowCount = PAGE_NUMBER;
        }

        List<DataMining> list = dataMiningService.getManyOrOneDataMining(categoryName,user.getId());

        List filelist = new ArrayList<>();
        int startItem = (page - 1) * rowCount;
        int endItem = page * rowCount - 1;
        int totalPage = 0;
        if (rowCount > list.size()) {
            totalPage = 1;
        } else {
            totalPage = list.size() % rowCount == 0 ? list.size() / rowCount : list.size() / rowCount + 1;
        }
        if (endItem > list.size() - 1) {
            endItem = list.size() - 1;
        }
        PageBean pageBean = new PageBean();

        pageBean.setTotal(list.size());
        pageBean.setTotalPage(totalPage);
        pageBean.setCurPage(page);
        if(page <= 0 & page == null){
            pageBean.setPrevious(1);
        }else{
            pageBean.setPrevious(page-1);
        }
        if (page >= totalPage){
            pageBean.setNext(totalPage);
        }else {
            pageBean.setNext(page+1);
        }
        for (int i = startItem; i <= endItem; i++) {
            filelist.add(list.get(i));
        }
        pageBean.setRows(filelist);
        return pageBean;
    }

    /**
     * 通过id 查询回显的数据
     * @param dataMiningId
     * @return
     */
    @RequestMapping(value = "selectById",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult selectDataMiningById(Integer dataMiningId){
        DataMining dataMining = dataMiningService.selectOneDataMiningById(dataMiningId);
        return new ResponseResult(HttpStatus.OK,"通过id查询数据成功",dataMining);
    }

    /**
     * 编辑DataMining
     * @param dataMining
     * @return
     */
    @RequestMapping(value = "updateOne",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult updateOneDataMining(DataMining dataMining){
        if (StringUtils.isNotBlank(dataMining.getDataMiningName())){
            int updateCount = dataMiningService.updateOneDataMining(dataMining);
            if (updateCount > 0 ){
                return new ResponseResult(HttpStatus.OK,"编辑成功",updateCount);
            }
            return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"编辑失败");
        }
        return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"编辑失败");
    }

    /**
     * 删除单个DataMining
     * @param dataMiningId
     * @return
     */
    @RequestMapping(value = "deleteOne",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult deleteOneDataMining(Integer dataMiningId){
        int deleteCount = dataMiningService.deleteOneDataMining(dataMiningId);
        if (deleteCount > 0){
            return new ResponseResult(HttpStatus.OK,"删除成功",deleteCount);
        }
        return new ResponseResult(HttpStatus.FAILED_DEPENDENCY,"删除失败");
    }

    /**
     * 批量删除DataMining
     * @param dataMiningIds
     * @return
     */
    @RequestMapping(value = "deleteMany",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult deleteManyDataMining(String dataMiningIds){
        String[] strArray = null;
        strArray = dataMiningIds.split("-");
        int deleteCount = dataMiningService.deleteManyDataMining(strArray);
        if (deleteCount > 0 ){
            return new ResponseResult(HttpStatus.OK,"批量删除成功",deleteCount);
        }
        return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"批量删除失败");
    }

    /**
     * 文件重命名
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "updateDataMiningName",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult updateDataMiningName(Integer id, String name){
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(id+"")){
            DataMining dataMining = dataMiningService.selectDataMiningReName(id);
            if (dataMining != null){
                int sum = dataMiningService.selectSum(dataMining.getDataMiningCategoryId(),name);
                if(0 != sum ){
                    return new ResponseResult(  HttpStatus.EXPECTATION_FAILED,"文件名已存在,重命名失败");
                }else {
                    int rowCount = dataMiningService.upateDataMiningName(id,name);
                    if (rowCount > 0 ){
                        return new ResponseResult(  HttpStatus.OK,"重命名成功",rowCount);
                    }
                    return new ResponseResult(  HttpStatus.EXPECTATION_FAILED,"重命名失败");
                }
            }
            return new ResponseResult(  HttpStatus.EXPECTATION_FAILED,"重命名失败");
        }
        return new ResponseResult(  HttpStatus.EXPECTATION_FAILED,"参数有误，重命名失败");
    }
}

package org.com.drag.web.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.com.drag.common.page.PageBean;
import org.com.drag.common.page.PageParam;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.model.*;
import org.com.drag.service.SchedulerJobService;
import org.com.drag.service.WorkFlowService;
import org.com.drag.service.WorkSpaceService;
import org.com.drag.service.oozie.api.IHdfsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: WorkSpaceController

 * @Description: 工作空间Controller

 * @author: jiaonanyue

 * @date: 2016年11月3日 上午11:08:06
 */
@Controller
@RequestMapping("/drag/work")
public class WorkSpaceController {
	
	@Autowired
	private WorkSpaceService workSpaceService;
	@Autowired
	private WorkFlowService workFlowService;
    @Autowired
    private IHdfsApi iHdfsApi;
    @Autowired
	private SchedulerJobService schedulerJobService;
	
	/**
	 * 创建工作空间
	 * @param session
	 * @param workSpace
	 * workContextJsons=[
	 * {"ConnectionId":"con_22","PageSourceId":"state_start1","PageTargetId":"state_flow2",
	 * "SourceText":"开始","TargetText":"流程","SourceAnchor":"RightMiddle","TargetAnchor":"LeftMiddle"}
	 * ]
	 * &
	 * [{"BlockId":"state_start1","BlockContent":"开始","BlockX":169,"BlockY":95,"Name":"node1"},
	 * {"BlockId":"state_flow2","BlockContent":"流程","BlockX":540,"BlockY":73,"Name":"node2"}]
	 * @return
	 * String cols="[{'id':'3'},{'id':'3'}]";
	 *  ObjectMapper mapper = new ObjectMapper(); 
	 *  JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, Bean.class); 
	 *  //Map类型  
	 *   mapper.getTypeFactory().constructParametricType(HashMap.class,String.class, Bean.class); 
	 *   List<Bean> lst =  (List<Bean>)mapper.readValue(cols, javaType); 
	 */
	//@RequestMapping(value = "create", method = RequestMethod.POST)
	/*public String createWorkSpace(HttpSession session, String workContextJson ,String blocks){
		 
		
		WorkSpace workSpace = new WorkSpace();
		if(session != null ){
			User user = (User) session.getAttribute("user");
			workSpace.setUserid(user.getId());
		}
		workSpace.setName("lisi");
		int a = workSpaceService.insertWorkSpaceByLogic(workSpace,workContextJson,blocks);
		if(a == 1){
			
			return "工作空间保存成功";
		}else{
			return "工作空间保存失败";
		}
		
	}*/
	
	
	/**
	 * 创建工作空间  并创建对应的目录
	 * @param session
	 * @param workSpace
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult createWorkSpace(HttpServletRequest request,HttpSession session, WorkSpace workSpace,String PName){
		
		if(workSpace != null){
			User user = (User) session.getAttribute(Constants.USER_KEY);
			workSpace.setUserid(user.getId());
			workSpace.setCreateTime(new Date());
			
			//判断用户是否创建了同名工作空间(这个判断需要改)
			WorkSpace WorkSpaceName = new WorkSpace();
			WorkSpaceName.setUserid(user.getId());
			WorkSpaceName.setName(workSpace.getName());
			List<WorkSpace> workSpaceList = workSpaceService.selectWorkSpace(WorkSpaceName);
			if( workSpaceList != null && workSpaceList.size() >0 ){
					for (int i = 0; i <workSpaceList.size() ; i++) {
						if(workSpace.getPid().equals(workSpaceList.get(i).getPid()) && workSpace.getName().equals(workSpaceList.get(i).getName())){
							return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"工作空间名相同");
						}
				}
			}

//			else{
				Boolean succeed = false;
				String PURL ="";
				if(workSpace.getPid() == 0){
					succeed = iHdfsApi.createUserWorkspace(user.getHdfsUrl(user)+"/"+workSpace.getName(),user.getLoginname(),true);

				}else{
					String workspaceURL = workSpaceService.folderAllPath(workSpace.getPid());
					String[] newWorkspace = workspaceURL.split("workspaces/");
					if(newWorkspace.length>=2){
						for (int i = 1; i < newWorkspace.length ; i++) {
							PURL += newWorkspace[i].toString()+"workspaces/";
						}
						PURL = PURL.substring(0,PURL.lastIndexOf("workspaces/"));
					}else {
						PURL = newWorkspace[0].toString();
					}
					//创建对应hdfs目录
					succeed = iHdfsApi.createUserWorkspace(user.getHdfsUrl(user)+"/"+PURL+"/"+workSpace.getName(),user.getLoginname(),true);
				}
				//需要做一个查询将hdfs目录在workspace下的文件夹路径显示
				if(succeed){
					workSpace.setHdfsUrl(user.getHdfsUrl()+"/"+PURL+"/"+workSpace.getName());
					int a = workSpaceService.insertSelective(workSpace);
					if( a == 1){
						return new ResponseResult(HttpStatus.OK,"工作空间创建成功");
					}else{
						return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"工作空间创建失败");
					}
				}else{
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"工作空间创建失败");
				}

//			}
			
		}else{
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"参数不对");
		}
		
		
		
	}
	
	/**
	 * 查询用户的工作空间
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "getworklist", method = RequestMethod.GET)
	public String getWorkSpaceList(HttpSession session,Model model,int page){
		
		if(page == 0){
			page = 1;
		}
		
		WorkSpace WorkSpaceUseId = new WorkSpace();
		User user = (User) session.getAttribute(Constants.USER_KEY);
		
		WorkSpaceUseId.setUserid(user.getId());
		PageHelper.startPage(page,10); // 核心分页代码  测试
		
		List<WorkSpace> workSpaceList = workSpaceService.selectWorkSpace(WorkSpaceUseId);
		//设置返回的总记录数  
        PageInfo<WorkSpace> pageInfos=new PageInfo<WorkSpace>(workSpaceList);
        if(page == 1){
        	 model.addAttribute("Previous",page);
        }else{
        	 model.addAttribute("Previous",page-1);
        }
       
        if(page < pageInfos.getPages()){
        	 model.addAttribute("next",page+1);
        }else{
        	 model.addAttribute("next",page);
        }
        model.addAttribute("page",page);
        model.addAttribute("Total",pageInfos.getPages());
		
		if(workSpaceList != null && workSpaceList.size()>0){
			for(WorkSpace ws :workSpaceList){
				ws.setUserEmail(user.getemail());
				 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				 ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
			}
		}
		model.addAttribute("workSpaceList", workSpaceList);
		return "/workSpace/workSpaceList";
	}
	
	
	@RequestMapping(value = "getworklists", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getWorkSpaceLists(HttpSession session,Model model,PageParam page){
		/*PageBean pageBean = new PageBean();
		if (page == null || page == 0) {
			page = 1;
		}
		WorkSpace WorkSpaceUseId = new WorkSpace();
		User user = (User) session.getAttribute(Constants.USER_KEY);
		
		WorkSpaceUseId.setUserid(user.getId());
		PageHelper.startPage(page,10); // 核心分页代码  测试
		List<WorkSpace> workSpaceList = workSpaceService.selectWorkSpace(WorkSpaceUseId);
		if(workSpaceList != null && workSpaceList.size()>0){
			for(WorkSpace ws :workSpaceList){
				ws.setUserEmail(user.getemail());
				 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				 ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
			}
		}
		// 设置返回的总记录数
		PageInfo<WorkSpace> pageInfos = new PageInfo<WorkSpace>(workSpaceList);
		if (page == 1) {
			pageBean.setPrevious(page);
		} else {
			pageBean.setPrevious(page - 1);
		}

		if (page < pageInfos.getPages()) {
			pageBean.setNext(page + 1);
		} else {
			pageBean.setNext(page);
		}
		pageBean.setTotal(pageInfos.getPages());
		pageBean.setCurPage(page);
		pageBean.setRows(workSpaceList);
*/
		WorkSpace WorkSpaceUser = new WorkSpace();
		User user = (User) session.getAttribute(Constants.USER_KEY);
		
		WorkSpaceUser.setUserid(user.getId());
		if(page != null){
			page.setNumPerPage(10);
		}
		//切换数据库  
		//DataSourceContextHolder. setDbType(MailAuthorSetting.DATABASE_SWITCHING);  
		PageBean pageBean = workSpaceService.selectByPage(page, WorkSpaceUser);
		List<WorkSpace> workSpaceList = (List<WorkSpace>) pageBean.getRows();
		if(workSpaceList != null && workSpaceList.size()>0){
			for(WorkSpace ws :workSpaceList){
				ws.setUserEmail(user.getemail());
				 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				 ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
			}
		}
		
		return pageBean;

	}
	
	
	@RequestMapping(value = "getworklistname", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getWorkSpaceListName(HttpSession session,Integer page,WorkSpace workspace){
		PageBean pageBean = new PageBean();
		if (page == null || page == 0) {
			page = 1;
		}
		User user = (User) session.getAttribute(Constants.USER_KEY);
		
		workspace.setUserid(user.getId());
		PageHelper.startPage(page,10); // 核心分页代码  测试
		List<WorkSpace> workSpaceList = workSpaceService.selectWorkSpaceName(workspace);
		if(workSpaceList != null && workSpaceList.size()>0){
			for(WorkSpace ws :workSpaceList){
				ws.setUserEmail(user.getemail());
				 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				 ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
			}
		}
		// 设置返回的总记录数
		PageInfo<WorkSpace> pageInfos = new PageInfo<WorkSpace>(workSpaceList);
		if (page == 1) {
			pageBean.setPrevious(page);
		} else {
			pageBean.setPrevious(page - 1);
		}

		if (page < pageInfos.getPages()) {
			pageBean.setNext(page + 1);
		} else {
			pageBean.setNext(page);
		}
		pageBean.setTotal(pageInfos.getPages());
		pageBean.setCurPage(page);
		pageBean.setRows(workSpaceList);

		return pageBean;

	}
	
	
	
	/**
	 * 通过id查询用户工作空间
	 * @return
	 */
	@RequestMapping(value = "getwork", method = RequestMethod.POST)
	@ResponseBody
	public WorkSpace getWorkSpace(Model model,int id){
		
		WorkSpace workSpace = workSpaceService.selectByPrimaryKey(id);
//		model.addAttribute("workSpace", workSpace);
		return workSpace;
	}

	
	/**
	 * 更新用户工作空间
	 * @param workSpace
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult updateWorkSpace(WorkSpace workSpace,HttpSession session){
		
		User user = (User) session.getAttribute(Constants.USER_KEY);

		return workSpaceService.updateWorkSpace(workSpace, user);

	}
	
	/**
	 * 删除工作空间
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult deleteWorkSpace(WorkSpace workSpace,HttpSession session){
		User user = (User) session.getAttribute(Constants.USER_KEY);
		
		return workSpaceService.DeleteWorkSpace(workSpace, user);
	}

	/**
	 * 查询工作空间下是否有调度存在
	 * @param workSpace
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "findByWorkSpaceId",method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult findByWorkSpaceId(WorkSpace workSpace,HttpSession session){
		User user = (User) session.getAttribute(Constants.USER_KEY);
		List<SchedulerJob> list =  schedulerJobService.findByWorkSpaceId(workSpace.getId());
        String name = null;
        if(null != list && list.size()>0){
            for (int i = 0; i <list.size() ; i++) {
                name =   list.get(i).getSchJobName();
                break;
            }
            return new ResponseResult(HttpStatus.EXPECTATION_FAILED,name+"：工作流存在调度,你是否要删除！");
        }
        else{
        return new ResponseResult(HttpStatus.OK,"不存在工作流存在调度,你可以删除！");
        }
	}
	/**
	 * 批量删除工作空间
	 * @param workSpace
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "batchdel",method = RequestMethod.POST)
	@ResponseBody
	public  ResponseResult   batchDelete(@RequestBody WorkSpace workSpace, HttpSession session) {
		User user = (User)session.getAttribute(Constants.USER_KEY);
		
	   return workSpaceService.BatchDeleteWorkSpace(workSpace, user);
	}

	/**
	 * 查询Ztree展示数据
	 * @return
	 */
	@RequestMapping("/selectZtree")
	@ResponseBody
	public ResponseResult selectZtree(HttpSession session,Integer id,boolean isParent,String name,Integer pId){
//	public List<Node> selectZtree(HttpSession session,Integer id,boolean isParent,String name,Integer pId){
		User user = (User) session.getAttribute(Constants.USER_KEY);
		if(null == user){
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "用户未登录");
		}
		Integer	isp = 0;
		if(isParent){
			isp = 1;
		}else{
			isp = 0;
		}
		List<WorkSpace> treeList= workSpaceService.selectZtree(user.getId(),id,isp);
		WorkFlow workFlow = new WorkFlow();
//		workFlow.setName(name);
		workFlow.setWorkspid(id);
		List<WorkFlow> list = workFlowService.selectWorkFlow(workFlow);
		List<Node> nodes=new ArrayList<Node>();
		for (int i=0;i<treeList.size();i++){
			long nid=treeList.get(i).getId();
			long pid=treeList.get(i).getPid();
			String tname=treeList.get(i).getName();
			String hdfsUrl=treeList.get(i).getHdfsUrl(treeList.get(i));
			Node node= new Node();
			if(1 == treeList.get(i).getIsParent()){
				node.setIsParent(true);
			}else{
				node.setIsParent(false);
			}
			node.setId(nid);
			node.setpId(pid);
			node.setName(tname);
			node.setCurDir(hdfsUrl);
			nodes.add(node);
		}
		for (int i = 0; i <list.size() ; i++) {
			String tname=list.get(i).getName();
			long pid=list.get(i).getWorkspid();
			long nid=list.get(i).getId();
			String hdfsUrl=list.get(i).getHdfsUrl(list.get(i));
//			String workSpaceName=list.get(i).getWorkspaceName();
			Node node= new Node();
			node.setIsParent(false);
			node.setId(nid);
			node.setpId(pid);
			node.setName(tname);
			node.setCurDir(hdfsUrl);
//			node.setWorkSpaceName(workSpaceName);
			nodes.add(node);
		}
//		return nodes;
		return new ResponseResult(HttpStatus.OK,"查询成功",nodes);
	}

	/**
	 * 弹框展现工作流文件夹
	 * @param session
	 * @param id
	 * @param isParent
	 * @param name
	 * @param pId
	 * @return
	 */
	@RequestMapping("/selectZtreeWindow")
	@ResponseBody
	public ResponseResult selectZtreeWindow(HttpSession session,Integer id,boolean isParent,String name,Integer pId){
		User user = (User) session.getAttribute(Constants.USER_KEY);
		if(null == user){
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "用户未登录");
		}
		Integer	isp = 0;
		if(isParent){
			isp = 1;
		}else{
			isp = 0;
		}
		List<WorkSpace> treeList= workSpaceService.selectZtree(user.getId(),id,isp);
		List<Node> nodes=new ArrayList<Node>();
		for (int i=0;i<treeList.size();i++){
			long nid=treeList.get(i).getId();
			long pid=treeList.get(i).getPid();
			String tname=treeList.get(i).getName();
			String hdfsurl=treeList.get(i).getHdfsUrl(treeList.get(i));
			Node node= new Node();
			if(1 == treeList.get(i).getIsParent()){
				node.setIsParent(true);
			}else{
				node.setIsParent(false);
			}
			node.setId(nid);
			node.setpId(pid);
			node.setName(tname);
			node.setCurDir(hdfsurl);
			nodes.add(node);
		}
		return new ResponseResult(HttpStatus.OK,"查询成功",nodes);
	}

	/**
	 * 查询出所有数据
	 * @param contentCid
	 * @return
	 */
	@RequestMapping("selectAllWorkspaces")
	@ResponseBody
	public String getContentList(Integer contentCid) {
//		WorkSpace w = new WorkSpace();
//		w.setId(contentCid);
		List<WorkSpace> menuList = workSpaceService.selectAll(new WorkSpace());
		Set<Integer> ids =  getChildNodes(menuList,contentCid,new HashSet());
		for(Integer id : ids ){
			System.out.println("======== id ====" + id);
//			workSpaceService.deleteByPrimaryKey(id);

		}
		return null;
	}

	/**
	 * 先查询出父节点相关集合，在遍历出子节点id集合
	 * @param list 父节点相关集合
	 * @param parent_id
	 * @param returnList
	 * @return
	 */
	public static Set<Integer> getChildNodes(List<WorkSpace> list, Integer parent_id,Set returnList) {
		if(list == null && parent_id == null) return null;
		for (Iterator<WorkSpace> iterator = list.iterator(); iterator.hasNext();) {
			WorkSpace node = (WorkSpace) iterator.next();
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
//			System.out.println("pid:"+node.getPid());
//			System.out.println("####################");
			if (parent_id.equals(node.getPid())) {
				returnList.add(node.getId());
				//递归遍历子后子
				getChildNodes(list, node.getId(), returnList);
			}
		}
		return returnList;
	}
}

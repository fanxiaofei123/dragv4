package org.com.drag.service.impl;

import org.com.drag.common.result.ResponseResult;
import org.com.drag.dao.SchedulerJobDao;
import org.com.drag.dao.WorkFlowMapper;
import org.com.drag.dao.WorkSpaceMapper;
import org.com.drag.model.User;
import org.com.drag.model.WorkFlow;
import org.com.drag.model.WorkSpace;
import org.com.drag.service.WorkSpaceService;
import org.com.drag.service.oozie.api.IHdfsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


@Service
public class WorkSpaceServiceImpl extends BaseServiceImpl<WorkSpace> implements WorkSpaceService {

	private static final int WORK_NAME_SIZE = 20;
	@Autowired
	private WorkSpaceMapper  workSpaceDao;
	@Autowired
	private WorkFlowMapper workFlowDao;
	@Autowired
	private IHdfsApi iHdfsApi;
	@Autowired
	private SchedulerJobDao schedulerJobDao;
	/**
	 * 通过条件查询工作空间
	 */
	@Override
	public List<WorkSpace> selectWorkSpace(WorkSpace workSpace) {
		
		return workSpaceDao.selectWorkSpace(workSpace);
	}


	@Override
	@Transactional
	public ResponseResult DeleteWorkSpace(WorkSpace workSpace,User user) {
		
		if(workSpace != null && workSpace.getId() != null){
			    WorkSpace workSpaces = workSpaceDao.selectByPrimaryKey(workSpace.getId());
				//删除工作空间hdfs目录
				//HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
//				String hdfs = MailAuthorSetting.HADOOP_HDFS_USER_DIR+user.getLoginname()+"/workspaces/"+workSpaces.getName();
				Boolean b = iHdfsApi.deleteFileOrDir(workSpaces.getHdfsUrl(workSpaces), user.getLoginname(), true);
				if(b){

					int a = workSpaceDao.deleteByPrimaryKey(workSpace.getId());
					//删除数据库子文件夹记录
					this.getContentList(workSpace.getId());
					if(a == 1){
						WorkFlow workFlow = new WorkFlow();
						workFlow.setWorkspid(workSpace.getId());
						List<WorkFlow> workFlowList = workFlowDao.selectWorkFlow(workFlow);
						if(workFlowList != null && workFlowList.size()>0){
							for(WorkFlow wf: workFlowList){
								workFlowDao.deleteByPrimaryKey(wf.getId());
							}
						}
					
					return new ResponseResult(HttpStatus.OK,"删除工作空间成功");
				}else{
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"删除工作空间失败");
				}
				
			}else{
				return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"删除工作空间失败");
			}
			
			
		}else{
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"参数不对");
		}
	}


	@Override
	@Transactional
	public ResponseResult updateWorkSpace(WorkSpace workSpace, User user) {
		if(workSpace.getName().length() >= WORK_NAME_SIZE){
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"名称长度最多20个字符");
		}
		WorkSpace workSpacen = workSpaceDao.selectByName(workSpace.getName());
		if(workSpacen != null){
			if(workSpacen.getPid() == workSpace.getPid()){
				if(workSpace.getName().equals(workSpacen.getName())){
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "名称重复了!");
				}
			}
		}

		if(workSpace != null && workSpace.getName() != null){
			WorkSpace workSpaces = workSpaceDao.selectByPrimaryKey(workSpace.getId());
			WorkSpace workSpaceName = workSpaceDao.selectByName(workSpace.getName());
			if(workSpaceName != null){
				workSpaceDao.updateByPrimaryKeySelective(workSpace);
				return new ResponseResult(HttpStatus.OK,"修改工作空间成功");
			}
			//需要做一个查询将hdfs目录在workspace下的文件夹路径显示
			String PURL ="";
			if(workSpace.getPid() > 1){
				String workspaceURL = workSpaceDao.folderAllPath(workSpace.getPid());
				String[] newWorkspace = workspaceURL.split("workspaces/");

				if(newWorkspace.length>=2){
					for (int i = 1; i < newWorkspace.length ; i++) {
						PURL += newWorkspace[i].toString()+"workspaces/";
					}
					PURL = PURL.substring(0,PURL.lastIndexOf("workspaces/"));
				}else {
					PURL = newWorkspace[0].toString();
				}
			}
			 //修改目录方法
			String hdfs = workSpaces.getHdfsUrl(workSpaces);
			Boolean a = iHdfsApi.renameDirectoryOrFile(hdfs,workSpace.getName(), user.getLoginname());
			if(a){
				if(PURL.equals("")){
					workSpace.setHdfsUrl(user.getHdfsUrl()+PURL+"/"+workSpace.getName());
				}else {
					workSpace.setHdfsUrl(user.getHdfsUrl()+"/"+PURL+"/"+workSpace.getName());
				}
//					//修改工作流目录
					WorkFlow workflow = new WorkFlow();
					workflow.setWorkspid(workSpace.getId());
					List<WorkFlow> workFlowList = workFlowDao.selectWorkFlow(workflow);
					if(workFlowList != null){
						for(WorkFlow wf:workFlowList){
							wf.setHdfsUrl(user.getHdfsUrl()+"/"+PURL+"/"+workSpace.getName()+"/"+wf.getName());
							workFlowDao.updateByPrimaryKeySelective(wf);
						}
					}
					workSpaceDao.updateByPrimaryKeySelective(workSpace);
					//修改子文件hdfs路径
//					this.getupdateList(workSpace.getId());
				List<WorkSpace> menuList = workSpaceDao.selectAll(new WorkSpace());
				List<Integer> ids =  getChildNodes(menuList,workSpace.getId(),new LinkedList());
				WorkSpace pws;
				for(Integer id : ids ){
					WorkSpace ws = 	workSpaceDao.selectByPrimaryKey(id);
					if(workSpace.getId().equals(ws.getPid())){
						pws = workSpaceDao.selectByPrimaryKey(workSpace.getId());
					}else{
						pws = workSpaceDao.selectByPrimaryKey(ws.getPid());
					}
					String phdfsurl = pws.getHdfsUrl();
					String chdfsurl = ws.getHdfsUrl();
					int newphd = chdfsurl.lastIndexOf("/");
					String b =  chdfsurl.substring(newphd);
					String hdfsUrl =  phdfsurl+b;
					WorkSpace n = new WorkSpace();
					n.setHdfsUrl(hdfsUrl);
					n.setId(id);
					workSpaceDao.updateByPrimaryKeySelective(n);

					//修改工作空间子目录工作流hdfs路径
					WorkSpace workSpace1 = workSpaceDao.selectByPrimaryKey(id);
					WorkFlow workFlow = new WorkFlow();
					workFlow.setWorkspid(workSpace1.getId());
					List<WorkFlow> list= workFlowDao.selectWorkFlow(workFlow);

					if(workFlowList != null){
						for(WorkFlow wf:list){
							workFlow.setHdfsUrl(workSpace1.getHdfsUrl()+"/"+wf.getName());
							workFlowDao.updateByWorkspid(workFlow);
						}
					}
				}

					return new ResponseResult(HttpStatus.OK,"修改工作空间成功");
			}else{
				return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"修改工作空间失败");
			}
			
		}else{
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"参数不对");
		}
		
	}


	@Override
	@Transactional
	public ResponseResult BatchDeleteWorkSpace(WorkSpace workSpace, User user) {


		if(workSpace != null && workSpace.getIds() != null && workSpace.getIds().size() > 0){
			
			//HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
			List<Integer> idList = workSpace.getIds();
			for(Integer ids : idList){
				WorkSpace workSpaces = workSpaceDao.selectByPrimaryKey(ids);
				if(workSpaces == null){
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"工作空间删除失败");
			}
				Boolean a = iHdfsApi.deleteFileOrDir(workSpaces.getHdfsUrl(workSpaces),user.getLoginname(),true);
				if(a){
					int b = workSpaceDao.deleteByPrimaryKey(ids);
					if(b != 1){
						return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"工作空间删除失败");
					}else{
						WorkFlow workSpaceId = new WorkFlow();
						workSpaceId.setWorkspid(ids);
						workFlowDao.deleteWorkFlow(workSpaceId);
					}
				}else{
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"工作空间删除失败");
				}
			}
			return new ResponseResult(HttpStatus.OK,"工作空间删除成功");
		}else{
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"工作空间删除失败");
		}
		
	}


	@Override
	public List<WorkSpace> UserCountWorkSpace(int userId) {

		return workSpaceDao.UserCountWorkSpace(userId);
	}


	@Override
	public int countAll() {

		return workSpaceDao.countAll();
	}

	@Override
	public List<WorkSpace> selectZtree(Integer userid,Integer pId,Integer isParent) {
		return workSpaceDao.selectZtree(userid,pId ,isParent);
	}

	@Override
	public String folderAllPath(Integer pid) {
		return workSpaceDao.folderAllPath(pid);
	}

	@Override
	public List<WorkSpace> selectWorkSpaceName(WorkSpace workSpace) {

		return workSpaceDao.selectWorkSpaceName(workSpace);
	}

	/**
	 * 删除工作空间，包括子节点的
	 * @param contentCid
	 * @return
	 */
	public Integer getContentList(Integer contentCid) {
		List<WorkSpace> menuList = workSpaceDao.selectAll(new WorkSpace());
		List<Integer> ids =  getChildNodes(menuList,contentCid,new LinkedList());
		for(Integer id : ids ){
			int a = workSpaceDao.deleteByPrimaryKey(id);
			if(a != 1){
				return 2;
			}
		}
		return 1;
	}
	/**
	 * 更新文价夹名称
	 * @param contentCid
	 * @return
	 */
	public Integer getupdateList(Integer contentCid) {
		List<WorkSpace> menuList = workSpaceDao.selectAll(new WorkSpace());
		List<Integer> ids =  getChildNodes(menuList,contentCid,new LinkedList());
		WorkSpace pws;
		for(Integer id : ids ){
			WorkSpace ws = 	workSpaceDao.selectByPrimaryKey(id);
			if(contentCid.equals(ws.getPid())){
				pws = workSpaceDao.selectByPrimaryKey(contentCid);
			}else{
				pws = workSpaceDao.selectByPrimaryKey(ws.getPid());
			}
			String phdfsurl = pws.getHdfsUrl();
			String chdfsurl = ws.getHdfsUrl();
			int newphd = chdfsurl.lastIndexOf("/");
			String b =  chdfsurl.substring(newphd);
			String hdfsUrl =  phdfsurl+b;
			WorkSpace n = new WorkSpace();
			n.setHdfsUrl(hdfsUrl);
			n.setId(id);
			workSpaceDao.updateByPrimaryKeySelective(n);
		}
		return 1;
	}

	/**
	 * 先查询出父节点相关集合，在遍历出子节点id集合
	 * @param list 父节点相关集合
	 * @param parentId
	 * @param returnList
	 * @return
	 */
	public static List<Integer> getChildNodes(List<WorkSpace> list, Integer parentId,List returnList) {
		if(list == null && parentId == null) return null;
		for (Iterator<WorkSpace> iterator = list.iterator(); iterator.hasNext();) {
			WorkSpace node = (WorkSpace) iterator.next();
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (parentId.equals(node.getPid())) {
				returnList.add(node.getId());
				//递归遍历子后子
				getChildNodes(list, node.getId(), returnList);
			}
		}
		return returnList;
	}
}

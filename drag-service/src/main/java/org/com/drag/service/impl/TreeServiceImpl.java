package org.com.drag.service.impl;

import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.com.drag.dao.FacadeModelMapper;
import org.com.drag.dao.FacadeTreeMapper;
import org.com.drag.enums.MenuType;
import org.com.drag.model.FacadeTree;
import org.com.drag.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by sky on 2017/4/10.
 */
@Service
public class TreeServiceImpl extends BaseServiceImpl<FacadeTree> implements TreeService{
    @Autowired
    private FacadeTreeMapper treeDao;
    @Autowired
	private FacadeModelMapper facadeModelMapper;
    @Transactional
    public List<FacadeTree> selectAll() {
        return treeDao.selectAllTree();
    }
    
    @Transactional
    public List<FacadeTree> selectPNode(Integer pid) {
        return treeDao.selectPNode(pid);
    }
    
    /**
     * 删除一个节点，同时删除其子节点（包括自己）在father_leaf表中的所有记录
     * 设被删除的节点是A，其父节点是B
     * 1.A在father_leaf表中的所有记录需要被删除
     * 2.B如果在A删除后是叶子节点，在father_leaf表中添加记录
     * */
    @Transactional
    public void deleteById(List<Long> fids,Long id) {
    	FacadeTree node = treeDao.selectById(id);
    	treeDao.deleteById(fids);
    	treeDao.deleteFatherLeafByFids(fids);
    	long pid = node.getPid();
    	List<Long> leafIds = getLeafId(selectAll(),pid);
    	if(leafIds.isEmpty()){
    		node = new FacadeTree();
    		node.setId(pid);
    		node.setIsleaf(1);
    		treeDao.updateByPrimaryKeySelective(node);
    		treeDao.insertFatherLeafBatch(getFatherId(selectAll(), pid), pid);
    	}
    }
    
    /**
     * 添加一个节点
     * 设在节点A上添加节点B，则
     * 1.删除A在father_leaf中的所有记录（leaf为A）
     * 2.在father_leaf中添加B的相关记录
     * */
    @Transactional
    public int addNode(FacadeTree node) {
    	int row = treeDao.insertNode(node);
    	deleteFatherLeafByLeaf(node.getPid());
    	List<FacadeTree> tree = treeDao.selectAllTree();
    	List<Long> ids = getFatherId(tree, node.getId());
    	treeDao.insertFatherLeafBatch(ids,node.getId());
        return row;
    }

    @Override
    @Transactional
    public FacadeTree selectById(Long id) {
        return treeDao.selectById(id);
    }

    @Override
    @Transactional
    public FacadeTree selectByName(String pName) {
        return treeDao.selectByName(pName);
    }

    @Override
    @Transactional
    public int updateNode(String cName, Long id, Long pid) {
        Map<String ,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("cName",cName);
        paramMap.put("id",id);
        paramMap.put("pid",pid);
        return treeDao.updateNode(paramMap);
    }
    
    @Override
    @Transactional
	public List<FacadeTree> getByPid(Long pid) {
		return treeDao.selectByPid(pid);
	}
    
    @Transactional
    public EnumMap<MenuType, LinkedList<FacadeTree>> getTree(){
    	List<FacadeTree> origin = selectAll();
    	EnumMap<MenuType, LinkedList<FacadeTree>> map = new EnumMap<MenuType, LinkedList<FacadeTree>>(MenuType.class);
    	Iterator<FacadeTree> it = origin.iterator();
    	while(it.hasNext()){
    		FacadeTree item = it.next();
    		MenuType menuType = MenuType.valueOf(item.getType());
    		LinkedList<FacadeTree> list = map.get(menuType);
    		if(list==null){
    			list = new LinkedList<FacadeTree>();
    			map.put(menuType, list);
    		}
    		list.add(item);
    	}
    	return map;
    }
    
    @Transactional
    public List<FacadeTree> getLeaves(Long pid){
    	return treeDao.selectLeafByFid(pid);
    }

    @Override
    public FacadeTree findByCTime(Date date) {
        return treeDao.findByCTime(date);
    }

    @Override
    public void deleteByPId(Long id) {


        treeDao.deleteByPId(id);
    }

    @Override
    public void updateType0(Long pid) {
        treeDao.updateType0(pid);
    }
    @Override
    public void updateType1(Long pid) {
        treeDao.updateType1(pid);
    }

    /**
     * 当节点发生移动时，删除和修改father_leaf中的记录
     * 设被移动的节点为A，A原先的父级节点为B，移动后，父节点为C
     * 1.删除A在father_leaf中的所有记录
     * 2.如果C在A移动之前是叶子节点，删除father_leaf中leaf为C的所有记录
     * 
     * 在这里，执行指针调整
     * 
     * 3.插入father_leaf新记录，表示A的新的父子关系
     * 4.如果B在A移动之后是叶子节点，插入father_leaf新记录，表示B新的父子关系
     * */
    @Override
    public void moveNodeById(Map<String, Object> idMap) {
    	List<FacadeTree> leaves = getLeaves(Long.valueOf(idMap.get("moveId").toString()));
    	updateFatherLeafBeforeMove(leaves, Long.valueOf(idMap.get("targetId").toString()));
    	long pid = treeDao.selectById(Long.valueOf(idMap.get("moveId").toString())).getPid();
        treeDao.moveNodeById(idMap);
        updateFatherLeafAfterMove(leaves, pid);
    }
    
    /**
     * 在father_leaf表中，删除一个叶子节点的所有记录
     * */
    private void deleteFatherLeafByLeaf(long leaf){
    	treeDao.deleteFatherLeafByLeaf(leaf);
    }
    
    private void updateFatherLeafBeforeMove(List<FacadeTree> leaves, long targetId){
    	List<Long> ids = new LinkedList<>();
    	leaves.forEach(new Consumer<FacadeTree>(){
			public void accept(FacadeTree t) {
				ids.add(t.getId());
			}
		});
    	treeDao.deleteFatherLeafBatch(ids);
    	treeDao.deleteFatherLeafByLeaf(targetId);
    }
    
    private void updateFatherLeafAfterMove(List<FacadeTree> leaves, long movedId){
    	leaves.forEach(new Consumer<FacadeTree>(){
			public void accept(FacadeTree t) {
				List<Long> fids = getFatherId(selectAll(), t.getId());
				treeDao.insertFatherLeafBatch(fids, t.getId());
			}
		});
    	if(getLeafId(selectAll(),movedId).isEmpty()){
    		FacadeTree node = treeDao.selectById(movedId);
    		node.setIsleaf(1);
    		treeDao.updateByPrimaryKeySelective(node);
    		List<Long> fids = getFatherId(selectAll(), movedId);
    		treeDao.insertFatherLeafBatch(fids, movedId);
    		//treeDao.insertFatherLeafBatch(getFatherId(selectAll(), node.getPid()), node.getPid());
    	}
    }
    
    /**
     * 获取一个节点的所有父级节点，不包括自己
     * */
    private List<Long> getFatherId(List<FacadeTree> origin,long id){
    	List<Long> fids = new LinkedList<>();
    	while(id!=0){
    		Iterator<FacadeTree> it = origin.iterator();
    		while(it.hasNext()){
    			FacadeTree n = it.next();
    			if(n.getId().equals(id)){
    				fids.add(n.getId());
    				id = n.getPid();
    			}
    		}
    	}
    	fids.add(0L);
    	return fids;
    }
    
    public List<Long> getLeafId(List<FacadeTree> origin, long pid){//14
    	LinkedList<Long> fids = new LinkedList<Long>();//待删除节点id
		LinkedList<FacadeTree> stack = new LinkedList<FacadeTree>();//保存待处理节点的栈
        fids.push(pid);
		Iterator<FacadeTree> it = origin.iterator();
		while(it.hasNext()){
			FacadeTree item = it.next();
			if(item.getPid().longValue()==pid){
				stack.push(item);
				fids.add(item.getId());
				//it.remove();
			}
		}
		buildTree(origin, stack, fids);
		origin = null;
		stack = null;
		return fids;
	}
    
    /**
	 * 遍历原始节点列表，构建树形结构
	 * @param origin 待处理的节点列表
	 * @param stack 节点栈
	 * */
	private void buildTree(List<FacadeTree> origin, LinkedList<FacadeTree> stack, LinkedList<Long> fids){
		while(!stack.isEmpty()){//栈中无元素时，构建完毕
			Iterator<FacadeTree> it = origin.iterator();
			FacadeTree father = stack.pop();//拿到待处理节点
			while(it.hasNext()){
				FacadeTree item = it.next();
				if(item.getPid().longValue()==father.getId().longValue()){
				    stack.push(item);
					fids.add(item.getId());
				}
			}
		}
	}
	
	public List<FacadeTree> getByOptions(String type, String frontusername){
		List<FacadeTree> tree = null;
		if("myModels".equals(type)){
			tree = treeDao.selectByMyModels(frontusername);
		}else if("myApplies".equals(type)){
			tree = treeDao.selectByMyApplies(frontusername);
		}else if("foreList".equals(type)){
			tree = treeDao.selectByForeList(frontusername);
		}
		return tree;
	}

	@Override
	public void updateNodeType(Long id) {
		treeDao.updateNodeType(id);
	}

	/**
	 * 查询facade_Model表中的treeid字段
	 * @return
	 */
	@Override
	public List<Long> selectReleaseId() {
		return facadeModelMapper.selectTreeIds();
	}


}

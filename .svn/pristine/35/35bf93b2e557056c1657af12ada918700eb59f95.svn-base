package org.com.drag.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.com.drag.model.FacadeTree;
/**
 * 
 * Copyright © 2016 All rights reserved.

 * @ClassName: FacadeTreeMapper

 * @Description: 树形结构

 * @author: jiaonanyue

 * @date: 2016年11月12日 上午11:45:02
 */
public interface FacadeTreeMapper extends BaseDao<FacadeTree>{
	
	
	    List<FacadeTree> selectAllTree();

	    List<FacadeTree> selectPNode(Integer pid);

	    void deleteById(List<Long> fids);

	    int insertNode(FacadeTree node);
	    
	    List<FacadeTree> selectByPid(@Param("pid")Long pid);

       FacadeTree selectByName(String pName);

       int updateNode(Map<String, Object> paramMap);

	   FacadeTree selectById(Long id);
	   
	   List<FacadeTree> selectLeafByFid(@Param("fid")Long fid);

    FacadeTree findByCTime(Date date);

	void deleteByPId(Long id);

	void updateType0(Long pid);
	void updateType1(Long pid);

	void moveNodeById(Map<String, Object> idMap);
	
	int insertFatherLeafBatch(@Param("fids")List<Long> fids, @Param("leaf")Long leaf);
	
	int deleteFatherLeafBatch(@Param("leaves")List<Long> leaves);
	int deleteFatherLeafByFids(@Param("fids")List<Long> fids);
	int deleteFatherLeafByLeaf(@Param("treeId")long treeId);
	
	List<FacadeTree> selectByMyModels(@Param("frontusername")String frontusername);
	List<FacadeTree> selectByMyApplies(@Param("frontusername")String frontusername);
	List<FacadeTree> selectByForeList(@Param("frontusername")String frontusername);

    void updateNodeType(Long id);
}

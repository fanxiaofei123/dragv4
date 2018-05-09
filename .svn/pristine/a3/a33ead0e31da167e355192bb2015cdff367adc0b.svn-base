package org.com.drag.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.com.drag.model.FacadeModel;
import org.com.drag.model.ModelDetail;
import org.com.drag.model.ModelFrontUser;

/**
 * 
 * Copyright © 2016 All rights reserved.

 * @ClassName: FacadeModelMapper

 * @Description: 模型dao

 * @author: jiaonanyue

 * @date: 2016年11月4日 下午3:46:23
 */
public interface FacadeModelMapper extends BaseDao<FacadeModel>{
	
    /**
     * 条件查询数据
     * @param fm
     * @return
     */
	public List<FacadeModel> selectFacadeModel(FacadeModel fm);
	
	/**
	 * 通过状态查询
	 * @param fm
	 * @return
	 */
	public List<FacadeModel>  selectFacadeModelByType(FacadeModel fm);
	
	public List<FacadeModel>  selectByTreeIds(@Param("treeIds")List<Long> treeIds);
	
	public List<FacadeModel>  selectByFid(@Param("fid")long fid, @Param("type")String type);
	
	public List<ModelFrontUser>  selectMyModels(@Param("fid")long fid,@Param("frontName")String frontName, @Param("type")String type);

	public List<ModelFrontUser>  selectMyApply(@Param("fid")long fid,@Param("frontName")String frontName,@Param("type")String type);

	public List<ModelFrontUser>  selectByFidWithModelRecord(@Param("fid")long fid,@Param("frontName")String frontName, @Param("type")String type);
	
	public ModelDetail  selectWithDetail(@Param("id")int id);

	List<FacadeModel> selectFacadeModelByTypeName(FacadeModel facadeModel);

    List<Long> selectTreeIds();
}
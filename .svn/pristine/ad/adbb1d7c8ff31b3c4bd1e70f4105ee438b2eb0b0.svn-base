package org.com.drag.dao;

import java.util.List;

public interface BaseDao<T> {
	

	/**
	 * 通过id删除信息
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Integer id);

    /**
     * 添加信息
     * @param record
     * @return
     */
    int insert(T record);

    /**
     * 有选择添加信息
     * @param record
     * @return
     */
    int insertSelective(T record);

    /**
     * 通过id查询信息
     * @param id
     * @return
     */
    T selectByPrimaryKey(Integer id);

    /**
     * 有选择更新信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 更新信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);
    
    /**
     * 通过条件查询信息
     * @param record
     * @return
     */
    List<T> selectAll(T record);
    
    /**
     * 批量删除数据
     * @param ids
     * @return
     */
    long deleteByBatch(T record);
    
    
    

}

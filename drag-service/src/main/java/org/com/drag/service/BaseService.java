package org.com.drag.service;

import java.util.List;

import org.com.drag.common.page.PageBean;
import org.com.drag.common.page.PageParam;

public interface BaseService<T> {


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
     * 通过条件分页查询
     * @param record
     * @return
     */
    PageBean selectByPage(PageParam page , T record);
    
    /**
     * 批量删除数据
     * @param ids
     * @return
     */
    long deleteByBatch(T record);
    
    /**
     * 批量更新数据
     * @param recordList
     * @return
     */
    long updateByBatch(List<T> recordList); 
}

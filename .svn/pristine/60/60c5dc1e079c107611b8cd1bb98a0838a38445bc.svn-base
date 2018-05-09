package org.com.drag.service.impl;

import java.util.List;

import org.com.drag.common.page.PageBean;
import org.com.drag.common.page.PageParam;
import org.com.drag.dao.BaseDao;
import org.com.drag.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class BaseServiceImpl<T> implements BaseService<T> {

	@Autowired
	private BaseDao<T> baseDao;

	@Override
	public int deleteByPrimaryKey(Integer id) {

		return baseDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(T record) {

		return baseDao.insert(record);
	}

	@Override
	public int insertSelective(T record) {

		return baseDao.insertSelective(record);
	}

	@Override
	public T selectByPrimaryKey(Integer id) {

		return baseDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(T record) {

		return baseDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(T record) {

		return baseDao.updateByPrimaryKey(record);
	}

	@Override
	public List<T> selectAll(T record) {

		return baseDao.selectAll(record);
	}

	/**
	 * PageParam 
	 * pageNum; // 当前页数
	 * numPerPage; // 每页记录数
	 * 
	 */
	@Override
	public PageBean selectByPage(PageParam page, T record) {

		PageBean pageBean = new PageBean();
		if (page != null && page.getPageNum() == 0) {
			page.setPageNum(1);
		}
		PageHelper.startPage(page.getPageNum(), page.getNumPerPage()); // 核心分页代码 测试
		List<T> list = baseDao.selectAll(record);
		// 设置返回的总记录数
		PageInfo<T> pageInfos = new PageInfo<T>(list);
		if (page.getPageNum() == 1) {
			pageBean.setPrevious(page.getPageNum());
		} else {
			pageBean.setPrevious(page.getPageNum() - 1);
		}

		if (page.getPageNum() < pageInfos.getPages()) {
			pageBean.setNext(page.getPageNum() + 1);
		} else {
			pageBean.setNext(page.getPageNum());
		}
		pageBean.setTotal(pageInfos.getPages());
		pageBean.setCurPage(page.getPageNum());
		pageBean.setRows(list);

		return pageBean;
	}

	@Override
	public long deleteByBatch(T record) {

		return baseDao.deleteByBatch(record);
	}

	@Override
	public long updateByBatch(List<T> recordList) {

		return 0;
	}

}

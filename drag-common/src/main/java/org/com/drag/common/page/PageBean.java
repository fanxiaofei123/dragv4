package org.com.drag.common.page;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Copyright © 2017优易数据. All rights reserved.

 * @ClassName: PageBean

 * @Description: 返回分页实体

 * @author: jiaonanyue

 * @date: 2017年1月22日 下午2:28:54
 */
public class PageBean<T> implements Serializable {
	
	private static final long serialVersionUID = -9146856807357417356L;
	
	
	private Integer total; // 总记录 
	private List<T> rows; //显示的记录  
	private Integer curPage;//当前页
    private Integer totalPage; //总页数
    private Integer previous; //上一页
    private Integer next; //下一页
	private List<String> fileNamelist; //hdfs文件名

	public List<String> getFileNamelist() {

		return fileNamelist;
	}

	public void setFileNamelist(List<String> fileNamelist) {
		this.fileNamelist = fileNamelist;
	}

	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getPrevious() {
		return previous;
	}
	public void setPrevious(Integer previous) {
		this.previous = previous;
	}
	public Integer getNext() {
		return next;
	}
	public void setNext(Integer next) {
		this.next = next;
	}
	@Override
	public String toString() {
		return "PageBean [total=" + total + ", rows=" + rows + ", curPage=" + curPage + ", totalPage=" + totalPage
				+ ", previous=" + previous + ", next=" + next + "]";
	}
     
	 
}

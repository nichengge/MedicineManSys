package com.entity;

/**
 * 分页Model类
 * 
 *
 */
public class PageBean {
	//“page” //第几页的数据
	//“rows” //每页多少条数据
	// easyui datagrid 自身会自动通过 post 的形式传递 rows and page。
	private int page; // 第几页的数据
	private int pageSize; // 每页记录数
	private int start;  // 起始页
	
	
	public PageBean(int page, int pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		return (page-1)*pageSize;
	}
	
	
}

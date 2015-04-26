package com.utils.download.dao;

import java.util.List;

import com.utils.download.bo.ThreadInfo;

public interface ThreadDao {
	/**
	 * 插入一条线程信息
	 * @param threadInfo
	 */
	public void insertThread(ThreadInfo threadInfo);
	
	/**
	 * 删除一条线程信息
	 * @param thread_id
	 * @param url
	 */
	public void deleteThread(int thread_id,String url);
	
	/**
	 * 修改一条线程信息的下载进度
	 * @param thread_id
	 * @param url
	 * @param finished
	 */
	public void updateThread(int thread_id,String url,Integer finished);
	
	/**
	 * 查找线程信息
	 * @param url
	 * @return
	 */
	public List<ThreadInfo> findThread(String url);
	
	/**
	 * 判断线程信息是否已经存在了
	 * @param thread_id
	 * @param url
	 * @return
	 */
	public boolean isExists(int thread_id,String url);
}

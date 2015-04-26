package com.utils.download.dao;

import java.util.List;

import com.utils.download.bo.ThreadInfo;

public interface ThreadDao {
	/**
	 * ����һ���߳���Ϣ
	 * @param threadInfo
	 */
	public void insertThread(ThreadInfo threadInfo);
	
	/**
	 * ɾ��һ���߳���Ϣ
	 * @param thread_id
	 * @param url
	 */
	public void deleteThread(int thread_id,String url);
	
	/**
	 * �޸�һ���߳���Ϣ�����ؽ���
	 * @param thread_id
	 * @param url
	 * @param finished
	 */
	public void updateThread(int thread_id,String url,Integer finished);
	
	/**
	 * �����߳���Ϣ
	 * @param url
	 * @return
	 */
	public List<ThreadInfo> findThread(String url);
	
	/**
	 * �ж��߳���Ϣ�Ƿ��Ѿ�������
	 * @param thread_id
	 * @param url
	 * @return
	 */
	public boolean isExists(int thread_id,String url);
}

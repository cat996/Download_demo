package com.download.db;

import java.util.List;

import com.download.entities.ThreadInfo;

import android.R.integer;

/**
 * �̵߳����ݿ���ʽӿ�
 * @author hp
 *
 */
public interface ThreadDAO {

	/**
	 * �����߳���Ϣ
	 * @param threadInfo
	 */
	public void  insertThread(ThreadInfo threadInfo) ;
	/**
	 * ɾ���߳�
	 * @param url
	 * @param thread_id
	 */
	public void  deleteThread(String url,int  thread_id);
	
	/**
	 * �����߳����ؽ���
	 * @param threadInfo
	 */
	public void  updateThread(String url,int  thread_id,int finished);
	/**
	 * ��ѯ�ļ����߳���Ϣ�����ؼ���
	 * @param url
	 * @return
	 */
	public List<ThreadInfo> geThreadInfos(String url);
	/**
	 * �ж��߳�id �Ƿ������ݿ����
	 * @param url
	 * @param thread_id
	 * @return
	 */
	public boolean isExists(String url,int  thread_id);
}

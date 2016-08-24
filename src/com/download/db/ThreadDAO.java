package com.download.db;

import java.util.List;

import com.download.entities.ThreadInfo;

import android.R.integer;

/**
 * 线程的数据库访问接口
 * @author hp
 *
 */
public interface ThreadDAO {

	/**
	 * 插入线程信息
	 * @param threadInfo
	 */
	public void  insertThread(ThreadInfo threadInfo) ;
	/**
	 * 删除线程
	 * @param url
	 * @param thread_id
	 */
	public void  deleteThread(String url,int  thread_id);
	
	/**
	 * 更新线程下载进度
	 * @param threadInfo
	 */
	public void  updateThread(String url,int  thread_id,int finished);
	/**
	 * 查询文件的线程信息，返回集合
	 * @param url
	 * @return
	 */
	public List<ThreadInfo> geThreadInfos(String url);
	/**
	 * 判断线程id 是否在数据库存在
	 * @param url
	 * @param thread_id
	 * @return
	 */
	public boolean isExists(String url,int  thread_id);
}

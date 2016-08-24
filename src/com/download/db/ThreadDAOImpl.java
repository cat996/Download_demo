package com.download.db;

import java.util.ArrayList;
import java.util.List;

import com.download.entities.ThreadInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * 数据访问接口实现，要借助DBHelper 
 * @author hp
 *
 */
public class ThreadDAOImpl implements ThreadDAO{

	private DBHelper mHelper=null;
	public ThreadDAOImpl(Context context){
		mHelper=new DBHelper(context);
	}
	@Override
	public void insertThread(ThreadInfo threadInfo) {
		SQLiteDatabase database=mHelper.getReadableDatabase();
		database.execSQL(
				"insert into thread_info(thread_id,url,start,end,finished) values(?,?,?,?,?) " ,new Object[]{
						threadInfo.getId(),threadInfo.getUrl(),threadInfo.getStart(),threadInfo.getEnd(),threadInfo.getFinished()
				});
		database.close();
		
	}

	public void deleteThread(String url, int thread_id) {
		SQLiteDatabase database=mHelper.getReadableDatabase();
		database.execSQL(
				"delete from thread_info where url=? and thread_id=?" ,new Object[]{
						url,thread_id
				});
		database.close();
		
	}

	public void updateThread(String url, int thread_id,int finished) {
		SQLiteDatabase database=mHelper.getReadableDatabase();
		database.execSQL(
				"update thread_info set finished=? where url=? and thread_id=?" ,
				new Object[]{finished,url,thread_id});
		database.close();
		
	}

	@Override
	public List<ThreadInfo> geThreadInfos(String url) {
		List<ThreadInfo> list=new ArrayList<ThreadInfo>();
		SQLiteDatabase database=mHelper.getReadableDatabase();
		Cursor cursor=database.rawQuery(
				"select * from thread_info where url=? " ,
				new String[]{url});
		while(cursor.moveToNext()){
			ThreadInfo threadInfo =new ThreadInfo();
			threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
			threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
			threadInfo.setStart(cursor.getInt(cursor.getColumnIndex("thread_id")));
			threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("thread_id")));
			threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("thread_id")));
			list.add(threadInfo);
			
		}
		
		cursor.close();
		database.close();
		return list;	
	}

	@Override
	public boolean isExists(String url, int thread_id) {
		
		SQLiteDatabase database=mHelper.getReadableDatabase();
		Cursor cursor=database.rawQuery(
				"select * from thread_info where url=? and thread_id=?" ,
				new String[]{url,thread_id+""});
		 boolean exists=cursor.moveToNext();
		database.close();
		cursor.close();
		return exists;
		
		}
		
		
		
	}



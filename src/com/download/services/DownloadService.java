package com.download.services;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.RandomAccess;

import org.apache.http.HttpStatus;

import com.download.entities.FileInfo;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
//mainactivity跳转到 service，在service里开启线程获取文件长度，在本地新建了一个相同大小的文件
public class DownloadService extends Service {

	public static final String DOWNLOAD_PATH=
			Environment.getExternalStorageDirectory().getAbsolutePath()+
			"/download";
			
	public static final String ACTION_START="ACTION_START";
	public static final String ACTION_STOP="ACTION_STOP";
	public static final int MSG_INIT=0;
	
	//接受activity传来的 路径
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	  //获得activity传来的参数
		if (ACTION_START.equals(intent.getAction())) {
			FileInfo fileInfo=(FileInfo) intent.getSerializableExtra("fileinfo");
			Log.i("test", "Start:"+fileInfo.toString());		
			//启动初始化线程
			new initThread(fileInfo).start();
		}
		else if (ACTION_STOP.equals(intent.getAction())) {
			FileInfo fileInfo=(FileInfo) intent.getSerializableExtra("fileinfo");
			Log.i("test", "Stop:"+fileInfo.toString());
		}
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_INIT:
				FileInfo fileInfo=(FileInfo) msg.obj;
				Log.i("GetTheInfo", "Init:"+fileInfo);
				
				break;

			default:
				break;
			}
			
		};
		
	};
	
	/**
	 * 初始化子线程
	 */
	class initThread extends Thread{
		private FileInfo mFileInfo=null;

		public initThread(FileInfo mFileInfo) {
			super();
			this.mFileInfo = mFileInfo;
		}
		
		public void run() {
			HttpURLConnection conn=null;
			RandomAccessFile raf=null;
			try {
				//连接网络文件，
				URL url=new URL(mFileInfo.getUrl());
			    conn=(HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(3000);
				conn.setRequestMethod("GET");
				int  length=-1;
				if (conn.getResponseCode()==HttpStatus.SC_OK) {
					//获得长度
					length=conn.getContentLength();				
				}
				if (length<=0) {
					return;
				}
			    File  dir=new File(DOWNLOAD_PATH);
			    if (!dir.exists()) {
					dir.mkdir();//创建路径
				}			    
				//在本地创建文件
			    File file=new File(dir,mFileInfo.getFilename());//路径，文件名
			    raf=new RandomAccessFile(file, "rwd");
				//设置文件长度
			    raf.setLength(length);
			    mFileInfo.setLength(length);
			    //组装一个message ； 发送给handler
			    handler.obtainMessage(MSG_INIT, mFileInfo).sendToTarget();
			    
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			finally {
				
				try {
					conn.disconnect();
					raf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}//run()
		
	}

}

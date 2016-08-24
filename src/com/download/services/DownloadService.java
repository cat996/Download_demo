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
//mainactivity��ת�� service����service�￪���̻߳�ȡ�ļ����ȣ��ڱ����½���һ����ͬ��С���ļ�
public class DownloadService extends Service {

	public static final String DOWNLOAD_PATH=
			Environment.getExternalStorageDirectory().getAbsolutePath()+
			"/download";
			
	public static final String ACTION_START="ACTION_START";
	public static final String ACTION_STOP="ACTION_STOP";
	public static final int MSG_INIT=0;
	
	//����activity������ ·��
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	  //���activity�����Ĳ���
		if (ACTION_START.equals(intent.getAction())) {
			FileInfo fileInfo=(FileInfo) intent.getSerializableExtra("fileinfo");
			Log.i("test", "Start:"+fileInfo.toString());		
			//������ʼ���߳�
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
	 * ��ʼ�����߳�
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
				//���������ļ���
				URL url=new URL(mFileInfo.getUrl());
			    conn=(HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(3000);
				conn.setRequestMethod("GET");
				int  length=-1;
				if (conn.getResponseCode()==HttpStatus.SC_OK) {
					//��ó���
					length=conn.getContentLength();				
				}
				if (length<=0) {
					return;
				}
			    File  dir=new File(DOWNLOAD_PATH);
			    if (!dir.exists()) {
					dir.mkdir();//����·��
				}			    
				//�ڱ��ش����ļ�
			    File file=new File(dir,mFileInfo.getFilename());//·�����ļ���
			    raf=new RandomAccessFile(file, "rwd");
				//�����ļ�����
			    raf.setLength(length);
			    mFileInfo.setLength(length);
			    //��װһ��message �� ���͸�handler
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

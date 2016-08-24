package com.download.app;

import com.download.entities.FileInfo;
import com.download.services.DownloadService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView mTvFileName=null;
	private ProgressBar mPbProgress=null;
	private Button mBtStop=null;
	private Button mBtStart=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTvFileName=(TextView) findViewById(R.id.tvFileName);
		mPbProgress=(ProgressBar) findViewById(R.id.pbProgress);
		mBtStop=(Button) findViewById(R.id.btStop);
		mBtStart=(Button) findViewById(R.id.btStart);
		//创建一个文件实体类对象
		final FileInfo fileInfo=new FileInfo(0,
				//"http://sw.bos.baidu.com/sw-search-sp/software/dcef3f16cf0c4/kugou_8.0.73.19052_setup.exe",
				"http://dlsw.baidu.com/sw-search-sp/soft/3f/12289/Weibo.4.5.3.37575common_wbupdate.1423811415.exe",
				"Weibo.4.5.3.37575common_wbupdate.1423811415.exe",0,0);//id，路径，名字，长度，进度
		mBtStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//通过intent传递参数给 service 
				Intent intent=new Intent(MainActivity.this,DownloadService.class		);
				intent.setAction(DownloadService.ACTION_START);
				intent.putExtra("fileinfo", fileInfo);
				startService(intent);
				
				
			}
		});
mBtStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//通过intent传递参数给 service 
				Intent intent=new Intent(MainActivity.this,DownloadService.class		);
				intent.setAction(DownloadService.ACTION_STOP);
				intent.putExtra("fileinfo", fileInfo);
				startService(intent);
				
				
			}
		});
		
		
		
	}
	
}

package com.utils.download;

import org.w3c.dom.Text;

import com.utils.download.bo.FileInfo;
import com.utils.download.services.DownLoadService;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private TextView fileName;
	private ProgressBar bar;
	private TextView barText;
	private Button download, stop;
	private FileInfo fileInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	/** ³õÊ¼»¯¿Ø¼þ **/
	private void initView() {
		fileName = (TextView) findViewById(R.id.filename);
		bar = (ProgressBar) findViewById(R.id.fileprogress);
		bar.setMax(100);
		barText = (TextView) findViewById(R.id.progresstext);
		download = (Button) findViewById(R.id.download);
		stop = (Button) findViewById(R.id.stop);
		download.setOnClickListener(this);
		stop.setOnClickListener(this);
		
		IntentFilter filter=new IntentFilter();
		filter.addAction(DownLoadService.ACTION_UPDATE);
		registerReceiver(mReceiver, filter);
	}

	private void initData() {
		//http://sqlserver.dlservice.microsoft.com/dl/download/B/8/0/B808AF59-7619-4A71-A447-F597DE74AC44/SQLFULL_CHS.iso
		fileInfo = new FileInfo(
				0,
				"http://dlsw.baidu.com/sw-search-sp/2015_04_25_22/bind1/14506/rj_fh4422.exe",
				"rj_fh4422.exe", 0, 0);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.download:
			intent = new Intent(this, DownLoadService.class);
			intent.setAction(DownLoadService.ACTION_START);
			intent.putExtra("fileinfo", fileInfo);
			startService(intent);
			break;
		case R.id.stop:
			intent = new Intent(this, DownLoadService.class);
			intent.setAction(DownLoadService.ACTION_STOP);
			intent.putExtra("fileinfo", fileInfo);
			startService(intent);
			break;

		default:
			break;
		}
	}
	
	protected void onDestroy() {
		unregisterReceiver(mReceiver);
	};
	
	BroadcastReceiver mReceiver=new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			if(DownLoadService.ACTION_UPDATE.equals(intent.getAction())){
				int finished=intent.getIntExtra("finished", 0);
				bar.setProgress(finished);
				barText.setText(finished+"%");
			}
		}
		
	};
}

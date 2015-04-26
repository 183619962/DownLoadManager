package com.utils.download.services;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpStatus;

import com.utils.download.bo.FileInfo;
import com.utils.download.util.DownLoadTask;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

/**
 * 用于下载到service service的优先级比activity的高，不容易被系统销毁
 * 
 * @author lpf
 * 
 */
public class DownLoadService extends Service {
	public static final String TAG = "DownLoadService";

	private FileInfo fileInfo;
	public static final String ACTION_START = "ACTION_START";
	public static final String ACTION_STOP = "ACTION_STOP";
	public static final String ACTION_UPDATE="ACTION_UPDATE";

	public static final String DOWNLOAD_PATH_KEY = "DOWNLOAD_PATH_KEY";
	public static final int SERVICE_INIT = 0;
	private DownLoadTask mTask=null;

	public static String downLoad_path = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/myDownLoad/";

	/***
	 * 用于接受传过来的参数
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if (ACTION_START.equals(intent.getAction())) {
			fileInfo = (FileInfo) intent.getSerializableExtra("fileinfo");
			Log.i(TAG, "START:" + fileInfo.toString());
			// 得到文件基本信息之后，需要通过一个子线程去操作获取文件长度及本地文件的创建
			new initThread(fileInfo).start();
		} else if (ACTION_STOP.equals(intent.getAction())) {
			fileInfo = (FileInfo) intent.getSerializableExtra("fileinfo");
			Log.i(TAG, "STOP:" + fileInfo.toString());
			if(null!=mTask){
				mTask.isPause=true;
			}
		}
		String path = intent.getStringExtra("DOWNLOAD_PATH_KEY");
		if (null != path && path.length() > 0) {
			this.downLoad_path = path;
		}
		Log.i(TAG, "path:" + downLoad_path);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SERVICE_INIT:
				fileInfo=(FileInfo) msg.obj;
				Log.i(TAG, "INIT:" + fileInfo.toString());
				mTask=new DownLoadTask(DownLoadService.this, fileInfo);
				mTask.downLoad();
				break;

			default:
				break;
			}
		};
	};

	/**
	 * 定义一个用于初始化的线程
	 */
	public class initThread extends Thread {
		private FileInfo mFileInfo;

		public initThread(FileInfo mFileInfo) {
			this.mFileInfo = mFileInfo;
		}

		@Override
		public void run() {
			HttpURLConnection conn = null;
			RandomAccessFile raf = null;
			try {
				// 连接网络文件
				URL url = new URL(mFileInfo.getUrl());
				conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(3 * 1000);
				conn.setRequestMethod("GET");
				Integer length = -1;
				// 判断文件是否已经连接上了
				if (conn.getResponseCode() == HttpStatus.SC_OK) {
					// 得到内容的长度
					length = conn.getContentLength();
				}
				if (length == -1) {
					Log.i(TAG, "获取文件失败");
					return;
				}
				// 获取文件的大小
				// 本地创建一个文件
				File dir = new File(downLoad_path);
				// 判断文件夹是否存在
				if (!dir.exists()) {
					// 不存在则创建
					dir.mkdir();
				}
				File file = new File(dir, mFileInfo.getFileName());
				if(file.exists()){
					Log.i(TAG, "create file success");
				}
				// 随机的访问。特殊的输出流，可以随机的在一个地方写入
				// rwd 文件操作模式。r=read w=write d=delete
				raf = new RandomAccessFile(file, "rwd");
				// 设置本地文件的长度
				raf.setLength(length);
				mFileInfo.setLength(length);
				// Message message=Message.obtain();
				handler.obtainMessage(SERVICE_INIT, mFileInfo).sendToTarget();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				try {
					raf.close();
					conn.disconnect();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}

		}
	}

}

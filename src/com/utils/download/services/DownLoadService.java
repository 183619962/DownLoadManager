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
 * �������ص�service service�����ȼ���activity�ĸߣ������ױ�ϵͳ����
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
	 * ���ڽ��ܴ������Ĳ���
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if (ACTION_START.equals(intent.getAction())) {
			fileInfo = (FileInfo) intent.getSerializableExtra("fileinfo");
			Log.i(TAG, "START:" + fileInfo.toString());
			// �õ��ļ�������Ϣ֮����Ҫͨ��һ�����߳�ȥ������ȡ�ļ����ȼ������ļ��Ĵ���
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
	 * ����һ�����ڳ�ʼ�����߳�
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
				// ���������ļ�
				URL url = new URL(mFileInfo.getUrl());
				conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(3 * 1000);
				conn.setRequestMethod("GET");
				Integer length = -1;
				// �ж��ļ��Ƿ��Ѿ���������
				if (conn.getResponseCode() == HttpStatus.SC_OK) {
					// �õ����ݵĳ���
					length = conn.getContentLength();
				}
				if (length == -1) {
					Log.i(TAG, "��ȡ�ļ�ʧ��");
					return;
				}
				// ��ȡ�ļ��Ĵ�С
				// ���ش���һ���ļ�
				File dir = new File(downLoad_path);
				// �ж��ļ����Ƿ����
				if (!dir.exists()) {
					// �������򴴽�
					dir.mkdir();
				}
				File file = new File(dir, mFileInfo.getFileName());
				if(file.exists()){
					Log.i(TAG, "create file success");
				}
				// ����ķ��ʡ������������������������һ���ط�д��
				// rwd �ļ�����ģʽ��r=read w=write d=delete
				raf = new RandomAccessFile(file, "rwd");
				// ���ñ����ļ��ĳ���
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

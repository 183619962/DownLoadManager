package com.utils.download.util;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpStatus;

import com.utils.download.bo.FileInfo;
import com.utils.download.bo.ThreadInfo;
import com.utils.download.dao.ThreadDao;
import com.utils.download.dao.impl.ThreadDaoImpl;
import com.utils.download.services.DownLoadService;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DownLoadTask {
	public static final String TAG="DownLoadTask";
	private Context context = null;
	private FileInfo fileInfo = null;
	private ThreadDao dao = null;
	private Integer mFinished = 0;
	public boolean isPause = false;

	public DownLoadTask(Context context, FileInfo fileInfo) {
		super();
		this.context = context;
		this.fileInfo = fileInfo;
		this.dao = new ThreadDaoImpl(context);
	}

	public void downLoad() {
		// ��ȡ���ݿ���߳���Ϣ
		List<ThreadInfo> threadInfos = dao.findThread(fileInfo.getUrl());
		ThreadInfo threadInfo=null;
		if (null != threadInfos && threadInfos.size() > 0) {
			threadInfo=threadInfos.get(0);
		} else {
			threadInfo = new ThreadInfo(fileInfo.getId(),
					fileInfo.getUrl(), 0, fileInfo.getLength(), 0);
			
		}
		
		//�������߳̽�������
		new DownLoadThread(threadInfo).start();
	}

	class DownLoadThread extends Thread {
		private ThreadInfo mThreadInfo;

		public DownLoadThread(ThreadInfo mThreadInfo) {
			super();
			this.mThreadInfo = mThreadInfo;
		}

		@Override
		public void run() {
			// �����ݿ���������߳���Ϣ
			if (!dao.isExists(mThreadInfo.getId(), mThreadInfo.getUrl())) {
				dao.insertThread(mThreadInfo);
			}
			HttpURLConnection conn = null;
			InputStream in = null;
			RandomAccessFile raf = null;

			try {
				URL url = new URL(mThreadInfo.getUrl());
				conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(3 * 1000);
				conn.setRequestMethod("GET");
				// �������ص�λ��
				Integer start = mThreadInfo.getStart() + mThreadInfo.getFinished();
				// �������صķ�Χ
				conn.setRequestProperty("Range", "bytes=" + start + "-"
						+ mThreadInfo.getEnd());

				File file = new File(DownLoadService.downLoad_path,
						fileInfo.getFileName());
				raf = new RandomAccessFile(file, "rwd");
				// �����ļ���д��λ��
				raf.seek(start);

				mFinished += mThreadInfo.getFinished();
				Intent intent = new Intent(DownLoadService.ACTION_UPDATE);
				// ��ʼ����
				if (conn.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT) {
					// ��ȡ����
					in = conn.getInputStream();
					byte[] buffer = new byte[1024 * 4];
					int len = -1;
					long time = System.currentTimeMillis();
					while ((len = in.read(buffer)) != -1) {
						raf.write(buffer, 0, len);
						mFinished += len;
						if (System.currentTimeMillis() - time > 500) {
							Log.i(TAG, "mFinished:"+mFinished+"//mFinished:"+(double)mFinished
									/ (double)fileInfo.getLength()+"//fileInfo.getLength():"+fileInfo.getLength()+"//**//:"+(int)(((double)mFinished
											/ (double)fileInfo.getLength())* 100));
							intent.putExtra("finished", (int)(((double)mFinished
									/ (double)fileInfo.getLength())* 100));
							context.sendBroadcast(intent);
							time = System.currentTimeMillis();
						}
						if (isPause) {
							dao.updateThread(mThreadInfo.getId(),
									mThreadInfo.getUrl(), mFinished);
							return;
						}
					}
					
					//�����˻ش�һ��100%   ����500�����ڵĻ�ȡ�����ǲ���㲥��activity
					intent.putExtra("finished", 100);
					context.sendBroadcast(intent);

					// ɾ���߳���Ϣ
					dao.deleteThread(mThreadInfo.getId(), mThreadInfo.getUrl());
				}

			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				try {
					in.close();
					raf.close();
					conn.disconnect();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}

}

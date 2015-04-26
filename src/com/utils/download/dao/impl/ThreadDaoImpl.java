package com.utils.download.dao.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.utils.download.bo.ThreadInfo;
import com.utils.download.dao.ThreadDao;
import com.utils.download.db.DBHelper;

public class ThreadDaoImpl implements ThreadDao {
	private DBHelper helper = null;

	public ThreadDaoImpl(Context context) {
		helper = new DBHelper(context);
	}

	@Override
	public void insertThread(ThreadInfo threadInfo) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(
				"insert into thread_info(thread_id,url,start,end,finished) values(?,?,?,?,?)",
				new Object[] { threadInfo.getId(), threadInfo.getUrl(),
						threadInfo.getStart(), threadInfo.getEnd(),
						threadInfo.getFinished() });
		db.close();
	}

	@Override
	public void deleteThread(int thread_id, String url) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("delete from thread_info where thread_id=? and url = ?",
				new Object[] { thread_id, url });
		db.close();
	}

	@Override
	public void updateThread(int thread_id, String url, Integer finished) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(
				"update thread_info set finished=? where thread_id=? and url=?",
				new Object[] { finished, thread_id, url });
		db.close();
	}

	@Override
	public List<ThreadInfo> findThread(String url) {
		List<ThreadInfo> infos = new ArrayList<ThreadInfo>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from thread_info where url=?",
				new String[] { url });
		while (cursor.moveToNext()) {
			ThreadInfo info = new ThreadInfo();
			info.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
			info.setUrl(cursor.getString(cursor.getColumnIndex("url")));
			info.setStart(cursor.getInt(cursor.getColumnIndex("start")));
			info.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
			info.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
			infos.add(info);
		}
		cursor.close();
		db.close();
		return infos;
	}

	@Override
	public boolean isExists(int thread_id, String url) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from thread_info where thread_id=? and url=?",
				new String[] { thread_id + "", url });
		boolean isExists = false;
		if (cursor.moveToNext()) {
			isExists = true;
		}
		cursor.close();
		db.close();
		return isExists;
	}

}

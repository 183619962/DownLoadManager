package com.utils.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ���ݿ������   ���ڴ������ݿ�
 * @author asus
 *
 */
public class DBHelper extends SQLiteOpenHelper{
	//���ݿ�����
	private static final String DBNAME="download.db";
	//���ݿ�汾
	private static final int VERSION=1;
	
	//�����洢�̵߳ı�
	private static final String CREATE_THREADINFO_TABLE="create table thread_info(_id integer primary key"
			+",thread_id integer,url text,start integer,end integer,finished integer)";
	private static final String DROP_THREADINFO_TABLE="drop table if exists thread_info";

	public DBHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_THREADINFO_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL(DROP_THREADINFO_TABLE);
		db.execSQL(CREATE_THREADINFO_TABLE);
	}

}

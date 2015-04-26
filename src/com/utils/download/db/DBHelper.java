package com.utils.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类   用于创建数据库
 * @author asus
 *
 */
public class DBHelper extends SQLiteOpenHelper{
	//数据库名字
	private static final String DBNAME="download.db";
	//数据库版本
	private static final int VERSION=1;
	
	//创建存储线程的表
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

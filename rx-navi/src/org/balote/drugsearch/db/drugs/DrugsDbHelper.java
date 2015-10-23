package org.balote.drugsearch.db.drugs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DrugsDbHelper extends SQLiteOpenHelper {

	public static DrugsDbHelper _instance = null;
	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "drugs_info.db";

	public static DrugsDbHelper getInstance(Context context) {
		if (_instance == null) {
			_instance = new DrugsDbHelper(context);
		}
		return _instance;
	}

	private DrugsDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DrugsDbConstants.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DrugsDbConstants.DROP_TABLE);
		onCreate(db);
	}

}

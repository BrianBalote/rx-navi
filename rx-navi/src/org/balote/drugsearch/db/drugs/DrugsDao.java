package org.balote.drugsearch.db.drugs;

import java.util.ArrayList;

import org.balote.drugsearch.db.api.IDao;
import org.balote.drugsearch.models.basic.ConceptModel;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DrugsDao implements IDao {

	private static final String TAG = "DrugsDao";

	private static SQLiteStatement insertDrugStmt = null;
	private static SQLiteDatabase db = null;
	private static DrugsDbHelper dbHelper = null;
	private static DrugsDao _instance = null;

	private DrugsDao(Context context) {
		try {
			dbHelper = DrugsDbHelper.getInstance(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DrugsDao getInstance(Context context) {
		if (_instance == null) {
			_instance = new DrugsDao(context);
		}
		return _instance;
	}

	@Override
	public void close() {

		Log.i(TAG, "close()");

		try {
			db.close();
		} catch (SQLiteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void open() {

		Log.i(TAG, "open()");

		try {
			db = dbHelper.getWritableDatabase();

			if (insertDrugStmt == null) {
				insertDrugStmt = db
						.compileStatement(DrugsDbConstants.INSERT_DRUG);
			}

		} catch (SQLiteException e) {

			Log.i(TAG, "open() exception: " + e.getMessage());

			e.printStackTrace();
		}
	}

	@Override
	public void read() {
		try {
			db = dbHelper.getReadableDatabase();
		} catch (SQLiteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clearTable() {
		try {
			db.delete(DrugsDbConstants.TABLE_NAME, "1", null);
		} catch (SQLiteException e) {
			e.printStackTrace();
		}
	}

	public void insertDrug(String name, String nui) {

		try {

			insertDrugStmt.clearBindings();
			insertDrugStmt.bindString(1, name);
			insertDrugStmt.bindString(2, nui);
			insertDrugStmt.executeInsert();

		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "@ insertDrug() error: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "@ insertDrug() error: " + e.getMessage());
		}

	}

	public ArrayList<ConceptModel> findDrug(String nameKey) {

		long startTime = System.currentTimeMillis();

		ArrayList<ConceptModel> list = null;

		try {

			Cursor c = db.rawQuery(DrugsDbConstants.FIND_DRUG_QUERY,
					new String[] { nameKey + "%" });
			list = new ArrayList<ConceptModel>(c.getCount());

			if (c.moveToFirst()) {

				do {

					ConceptModel cm = new ConceptModel();

					String name = c.getString(c
							.getColumnIndex(DrugsDbConstants.COL_NAME));

					String nui = c.getString(c
							.getColumnIndex(DrugsDbConstants.COL_NUI));

					cm.setConceptName(name);
					cm.setConceptNui(nui);
					cm.setConceptKind("DRUG_KIND");

					list.add(cm);

				} while (c.moveToNext());

			}

			c.close();

		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "@ findDrug() error: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "@ findDrug() error: " + e.getMessage());
		}

		long executionTime = System.currentTimeMillis() - startTime;
		Log.i(TAG, "@ findDrug() execution time: " + executionTime + " millis");

		return list;
	}

}

package org.balote.drugsearch.managers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public final class NdfrtVersionManager {

	private static final String TAG = "NdfrtVersionManager";
	private static final String NDFRT_VERSION_PERSISTENCE_KEY = "org.balote.drugsearch";
	private static final String VERSION_KEY = "VERSION_KEY";
	private static NdfrtVersionManager _instance = null;

	private NdfrtVersionManager() {
	}

	public static NdfrtVersionManager getInstance() {
		if (_instance == null) {
			_instance = new NdfrtVersionManager();
		}
		return _instance;
	}

	public boolean saveVersion(Context context, String version) {

		Editor e = context.getSharedPreferences(NDFRT_VERSION_PERSISTENCE_KEY,
				Context.MODE_PRIVATE).edit();

		e.putString(VERSION_KEY, version);
		return e.commit();
	}

	public String restoreVersion(Context context) {

		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				NDFRT_VERSION_PERSISTENCE_KEY, Context.MODE_PRIVATE);

		String version = mySharedPreferences.getString(VERSION_KEY, "");

		return version;
	}

	public boolean updateVersion(Context context, String newVersion)
			throws ParseException {

		boolean hasAnUpdate = false;

		String oldVersion = restoreVersion(context);

		if (oldVersion != null && !oldVersion.trim().isEmpty()) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.US);

			Date oldVersionDate = sdf.parse(oldVersion);
			Date newVersionDate = sdf.parse(newVersion);

			Log.d(TAG, "checkIfLatestVersion() old version: " + oldVersion);
			Log.d(TAG, "checkIfLatestVersion() new version: " + newVersion);

			if (oldVersionDate.compareTo(newVersionDate) < 0) {

				saveVersion(context, newVersion);

				Log.d(TAG, "checkIfLatestVersion() ndfrt has a new version!!!");

				hasAnUpdate = true;
			}

		} else {

			saveVersion(context, newVersion);

			Log.d(TAG, "checkIfLatestVersion() ndfrt has a new version!!!");

			hasAnUpdate = true;
		}

		return hasAnUpdate;

	}
}

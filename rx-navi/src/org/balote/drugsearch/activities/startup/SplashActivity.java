package org.balote.drugsearch.activities.startup;

import java.io.StringReader;

import org.balote.drugsearch.activities.drug.main.DrugInformationSearchActivity;
import org.balote.drugsearch.constants.XmlTagsConstants;
import org.balote.drugsearch.db.drugs.DrugsDao;
import org.balote.drugsearch.models.basic.ConceptModel;
import org.balote.drugsearch.runnables.api.IGetAllDrugsObserver;
import org.balote.drugsearch.runnables.api.IVersionCheckObserver;
import org.balote.drugsearch.runnables.impl.GetAllDrugsRunnable;
import org.balote.drugsearch.runnables.impl.VersionCheckRunnable;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ustg.drugsearch.R;

public class SplashActivity extends Activity implements IVersionCheckObserver,
		IGetAllDrugsObserver {

	private ProgressBar myProgressBar = null;
	private TextView myProgressText = null;
	private VersionCheckRunnable myVersionCheckRunnable = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		myProgressBar = (ProgressBar) findViewById(R.id.my_progress_bar);
		myProgressBar.setVisibility(View.INVISIBLE);

		myProgressText = (TextView) findViewById(R.id.my_progress_text);

		myVersionCheckRunnable = new VersionCheckRunnable(SplashActivity.this,
				SplashActivity.this);
		new Thread(myVersionCheckRunnable).start();
		myProgressBar.setVisibility(View.VISIBLE);
		myProgressText.setText("contacting server for updates...");
	}

	@Override
	public void onNotifyVersionUpdate(boolean hasAnUpdate) {

		if (!hasAnUpdate) {

			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					startDrugInfoActivity();
				}
			});

		} else {

			GetAllDrugsRunnable myGetAllDrugsRunnable = new GetAllDrugsRunnable(
					SplashActivity.this);
			new Thread(myGetAllDrugsRunnable).start();

			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					myProgressText
							.setText("downloading drug data, please wait...");
				}
			});
		}

		myVersionCheckRunnable = null;
	}

	@Override
	public void onNotifyDownloadDone(final String xmlString) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {

				ParseXmlAsyncTask parseXmlAsyncTask = new ParseXmlAsyncTask();
				parseXmlAsyncTask.execute(xmlString);
			}
		});

	}

	private void startDrugInfoActivity() {
		Intent i = new Intent(SplashActivity.this,
				DrugInformationSearchActivity.class);
		SplashActivity.this.startActivity(i);
		SplashActivity.this.finish();

		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	class ParseXmlAsyncTask extends AsyncTask<String, Void, Void> {

		private String TAG = "ParseXmlAsyncTask";

		@Override
		protected void onPreExecute() {

			myProgressText.setText("parsing drug data, please wait...");
		}

		@Override
		protected Void doInBackground(String... params) {

			String xmlString = params[0];

			try {

				DrugsDao dao = DrugsDao.getInstance(SplashActivity.this);
				dao.open();

				XmlPullParserFactory factory = XmlPullParserFactory
						.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp = factory.newPullParser();

				xpp.setInput(new StringReader(xmlString));

				int event = xpp.getEventType();
				String tag = "";
				String name = "";
				String nui = "";

				while (event != XmlPullParser.END_DOCUMENT) {

					switch (event) {

					case XmlPullParser.START_DOCUMENT:
						break;

					case XmlPullParser.START_TAG:
						tag = xpp.getName();

						if (tag.equalsIgnoreCase(ConceptModel.CONCEPT_NAME_TAG)) {

							name = xpp.nextText();
						}
						if (tag.equalsIgnoreCase(ConceptModel.CONCEPT_NUI_TAG)) {

							nui = xpp.nextText();
						}
						break;

					case XmlPullParser.END_TAG:
						tag = xpp.getName();

						if (tag.equalsIgnoreCase(XmlTagsConstants.CONCEPT_TAG)) {

							dao.insertDrug(name, nui);
						}
						break;
					}

					event = xpp.next();
				}

				dao.close();

			} catch (Exception e) {

				Log.e(TAG, "error: " + e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void v) {

			startDrugInfoActivity();
		}

	}
}

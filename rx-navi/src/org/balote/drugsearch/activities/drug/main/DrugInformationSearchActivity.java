package org.balote.drugsearch.activities.drug.main;

import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.balote.drugsearch.constants.NdfrtConstants;
import org.balote.drugsearch.db.drugs.DrugsDao;
import org.balote.drugsearch.managers.DrugInformationManager;
import org.balote.drugsearch.models.advance.InformationModel;
import org.balote.drugsearch.models.basic.ConceptModel;
import org.balote.drugsearch.parser.InformationXmlParser;
import org.balote.drugsearch.utils.InputStreamConverterUtil;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ustg.drugsearch.R;

public class DrugInformationSearchActivity extends ListActivity {

	private static final String TAG = "DrugInformationSearchActivity";

	private EditText drugTextField = null;
	private ProgressBar myProgressBar = null;
	private DrugsDao dao = null;
	private Vibrator vibrator = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drug_information_search);

		dao = DrugsDao.getInstance(DrugInformationSearchActivity.this);

		myProgressBar = (ProgressBar) findViewById(R.id.my_progress_bar);
		myProgressBar.setVisibility(View.INVISIBLE);

		drugTextField = (EditText) findViewById(R.id.drug_text_field);
		drugTextField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (s.toString().length() > 0) {

					dao.open();
					ArrayList<ConceptModel> list = dao.findDrug(s.toString());
					dao.close();

					DrugListAdapter adapter = new DrugListAdapter(
							R.layout.list_item, list);
					setListAdapter(adapter);

				} else {

					setListAdapter(null);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
	}

	private class GetAllInfoAsyncTask extends
			AsyncTask<String, Void, InformationModel> {

		@Override
		protected InformationModel doInBackground(String... params) {

			HttpClient myHttpClient = new DefaultHttpClient();
			HttpResponse myHttpResponse;
			int responseCode = 0;
			String xmlString = "";

			try {

				HttpGet myHttpGet = new HttpGet(
						NdfrtConstants.composeGetAllInfoUrl(params[0]));

				myHttpResponse = myHttpClient.execute(myHttpGet);
				responseCode = myHttpResponse.getStatusLine().getStatusCode();

				if (responseCode == 200) {

					xmlString = InputStreamConverterUtil.convert(myHttpResponse
							.getEntity().getContent());

					return InformationXmlParser
							.parseInformationFromXml(xmlString);

				} else {

					Log.w(TAG, "response code: " + responseCode);

				}

			} catch (Exception e) {

				e.printStackTrace();

			} finally {

				myHttpClient.getConnectionManager().shutdown();

			}

			return null;
		}

		@Override
		protected void onPostExecute(InformationModel drugInfoModel) {

			if (drugInfoModel != null) {

				DrugInformationManager.getInstance().setMyDrugInfoModel(
						drugInfoModel);

				Intent i = new Intent(DrugInformationSearchActivity.this,
						DrugInformationListActivity.class);
				DrugInformationSearchActivity.this.startActivity(i);

				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);

			} else {

				// TODO
			}

			myProgressBar.setVisibility(View.GONE);
		}

		@Override
		protected void onPreExecute() {
			myProgressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}

	}

	protected class DrugListAdapter extends BaseAdapter {

		private int viewResourceId;
		private ArrayList<ConceptModel> list = null;

		public DrugListAdapter(int viewResourceId, ArrayList<ConceptModel> list) {

			this.viewResourceId = viewResourceId;
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return list.indexOf(getItem(position));
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View row = convertView;
			MyPlaceHolder holder = null;

			if (row == null) {

				LayoutInflater inflater = (DrugInformationSearchActivity.this)
						.getLayoutInflater();
				row = inflater.inflate(viewResourceId, parent, false);

				holder = new MyPlaceHolder();
				holder.drugNameText = (TextView) row
						.findViewById(R.id.list_item_name_text);

				row.setTag(holder);

			} else {

				holder = (MyPlaceHolder) row.getTag();
			}

			final ConceptModel cm = list.get(position);

			holder.drugNameText.setText(cm.getConceptName());

			if (position % 2 == 0) {

				row.setBackgroundColor((DrugInformationSearchActivity.this)
						.getResources().getColor(R.color.light_list_item_bg));

			} else {

				row.setBackgroundColor((DrugInformationSearchActivity.this)
						.getResources().getColor(R.color.dark_list_item_bg));
			}

			row.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					if (myProgressBar.getVisibility() != View.VISIBLE) {

						GetAllInfoAsyncTask mygAllInfoAsyncTask = new GetAllInfoAsyncTask();
						mygAllInfoAsyncTask.execute(cm.getConceptNui());

						vibrator.vibrate(50);
					}
				}
			});

			return row;
		}

		class MyPlaceHolder {
			TextView drugNameText;
		}
	}

	@SuppressWarnings("unused")
	private class GetNuiAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			HttpClient myHttpClient = new DefaultHttpClient();
			HttpResponse myHttpResponse;
			int responseCode = 0;
			String xmlString = "";
			String nui = "";

			try {

				StringBuffer sb = new StringBuffer();
				sb.append(NdfrtConstants.ROOT_URL);
				sb.append("search?conceptName=");
				sb.append(URLEncoder.encode(params[0], "UTF-8"));
				sb.append("&kindName=DRUG_KIND");

				Log.i(TAG, "url : " + sb.toString());

				HttpGet myHttpGet = new HttpGet(sb.toString());

				myHttpResponse = myHttpClient.execute(myHttpGet);
				responseCode = myHttpResponse.getStatusLine().getStatusCode();

				if (responseCode == 200) {

					xmlString = InputStreamConverterUtil.convert(myHttpResponse
							.getEntity().getContent());

					nui = parseNui(xmlString);

				} else {

					Log.w(TAG, "response code: " + responseCode);

				}

			} catch (Exception e) {

				e.printStackTrace();

			} finally {

				myHttpClient.getConnectionManager().shutdown();

			}

			return nui;
		}

		@Override
		protected void onPostExecute(String nui) {

			Log.d(TAG, "nui: " + nui);

			if (nui != null && !nui.trim().isEmpty()) {

				GetAllInfoAsyncTask mygAllInfoAsyncTask = new GetAllInfoAsyncTask();
				mygAllInfoAsyncTask.execute(nui);
			}
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}

	}

	private static String parseNui(String xmlString) {

		String CONCEPT_NUI_TAG = "conceptNui";

		try {

			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();

			xpp.setInput(new StringReader(xmlString));

			int eventType = xpp.getEventType();

			String startTag = "";

			while (eventType != XmlPullParser.END_DOCUMENT) {

				if (eventType == XmlPullParser.START_TAG) {

					if (xpp.getName().equalsIgnoreCase(CONCEPT_NUI_TAG)) {
						startTag = CONCEPT_NUI_TAG;
					}

				} else if (eventType == XmlPullParser.TEXT) {

					if (startTag.equalsIgnoreCase(CONCEPT_NUI_TAG)) {
						return xpp.getText();
					}
				}

				eventType = xpp.next();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}

package org.balote.drugsearch.activities.extendables;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.balote.drugsearch.activities.disease.DiseaseInformationActivity;
import org.balote.drugsearch.constants.NdfrtConstants;
import org.balote.drugsearch.managers.DiseaseInformationManager;
import org.balote.drugsearch.models.advance.InformationModel;
import org.balote.drugsearch.models.basic.RoleModel;
import org.balote.drugsearch.parser.InformationXmlParser;
import org.balote.drugsearch.utils.InputStreamConverterUtil;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ustg.drugsearch.R;

public class ExtendableDiseaseListActivity extends ListActivity {

	private static final String TAG = "ExtendableDiseaseListActivity";
	protected ProgressBar myProgressBar = null;
	

	protected class ExtendableDiseaseListAdapter extends BaseAdapter {

		private int viewResourceId;
		private ArrayList<RoleModel> list;
		private Vibrator vibrator = null;

		public ExtendableDiseaseListAdapter(int viewResourceId,
				ArrayList<RoleModel> list) {

			this.viewResourceId = viewResourceId;
			this.list = list;
			this.vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
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

				LayoutInflater inflater = (ExtendableDiseaseListActivity.this)
						.getLayoutInflater();
				row = inflater.inflate(viewResourceId, parent, false);

				holder = new MyPlaceHolder();
				holder.listItemNameText = (TextView) row
						.findViewById(R.id.list_item_name_text);

				row.setTag(holder);

			} else {

				holder = (MyPlaceHolder) row.getTag();
			}

			final RoleModel rm = list.get(position);

			holder.listItemNameText.setText(rm.getRoleConceptModel()
					.getConceptName());

			if (position % 2 == 0) {

				row.setBackgroundColor((ExtendableDiseaseListActivity.this)
						.getResources().getColor(R.color.light_list_item_bg));

			} else {

				row.setBackgroundColor((ExtendableDiseaseListActivity.this)
						.getResources().getColor(R.color.dark_list_item_bg));
			}

			row.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					myProgressBar.setVisibility(View.VISIBLE);

					GetDiseaseInfoAsyncTask myGetDiseaseInfoAsyncTask = new GetDiseaseInfoAsyncTask();
					myGetDiseaseInfoAsyncTask.execute(rm.getRoleConceptModel()
							.getConceptNui());
					
					vibrator.vibrate(50);
				}
			});

			return row;
		}

		class MyPlaceHolder {
			TextView listItemNameText;
		}

	}

	private class GetDiseaseInfoAsyncTask extends
			AsyncTask<String, Void, InformationModel> {

		@Override
		protected void onPreExecute() {
			myProgressBar.setVisibility(View.VISIBLE);
		}

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
		protected void onPostExecute(InformationModel diseaseInformationModel) {

			if (diseaseInformationModel != null) {

				DiseaseInformationManager.getInstance().setDiseaseInfoModel(
						diseaseInformationModel);

				Intent i = new Intent(ExtendableDiseaseListActivity.this,
						DiseaseInformationActivity.class);
				ExtendableDiseaseListActivity.this.startActivity(i);

				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);

			} else {

				// TODO
			}

			myProgressBar.setVisibility(View.GONE);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}
}

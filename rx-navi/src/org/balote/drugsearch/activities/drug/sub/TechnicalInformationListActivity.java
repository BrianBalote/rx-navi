package org.balote.drugsearch.activities.drug.sub;

import java.util.ArrayList;

import org.balote.drugsearch.managers.DrugInformationManager;
import org.balote.drugsearch.models.basic.PropertyModel;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ustg.drugsearch.R;

public class TechnicalInformationListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity_technical_information);

		ArrayList<PropertyModel> list = new ArrayList<PropertyModel>();

		for (PropertyModel pm : DrugInformationManager.getInstance()
				.getMyDrugInfoModel().getGroupProperties()) {

			if (!pm.getPropertyName().equalsIgnoreCase("VANDF_Record")) {

				list.add(pm);
			}
		}

		TechnicalInformationAdapter adapter = new TechnicalInformationAdapter(
				R.layout.list_item_drug_info, list);

		setListAdapter(adapter);

	}

	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	private class TechnicalInformationAdapter extends BaseAdapter {

		private int viewResourceId;
		private ArrayList<PropertyModel> list;

		public TechnicalInformationAdapter(int viewResourceId,
				ArrayList<PropertyModel> list) {

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

				LayoutInflater inflater = (TechnicalInformationListActivity.this)
						.getLayoutInflater();
				row = inflater.inflate(viewResourceId, parent, false);

				holder = new MyPlaceHolder();
				holder.listItemDrugInfoLabel = (TextView) row
						.findViewById(R.id.list_item_drug_info_label);
				holder.listItemDrugInfoText = (TextView) row
						.findViewById(R.id.list_item_drug_info_text);

				row.setTag(holder);

			} else {

				holder = (MyPlaceHolder) row.getTag();
			}

			PropertyModel pm = list.get(position);

			StringBuffer sb = new StringBuffer();

			sb.append(pm.getPropertyName().replace('_', ' '));
			sb.append(": ");

			holder.listItemDrugInfoLabel.setText(sb.toString());
			holder.listItemDrugInfoText.setText(pm.getPropertyValue());

			if (position % 2 == 0) {

				row.setBackgroundColor((TechnicalInformationListActivity.this)
						.getResources().getColor(R.color.light_list_item_bg));

			} else {

				row.setBackgroundColor((TechnicalInformationListActivity.this)
						.getResources().getColor(R.color.dark_list_item_bg));
			}

			return row;
		}

		class MyPlaceHolder {
			TextView listItemDrugInfoLabel;
			TextView listItemDrugInfoText;
		}

	}
}

package org.balote.drugsearch.activities.drug.sub;

import org.balote.drugsearch.managers.DrugInformationManager;
import org.balote.drugsearch.models.basic.ConceptModel;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ustg.drugsearch.R;

public class AvailableFormsListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity_available_forms);

		AvailableFormsAdapter adapter = new AvailableFormsAdapter(
				R.layout.list_item);

		setListAdapter(adapter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	private class AvailableFormsAdapter extends BaseAdapter {

		private int viewResourceId;

		public AvailableFormsAdapter(int viewResourceId) {

			this.viewResourceId = viewResourceId;
		}

		@Override
		public int getCount() {

			return DrugInformationManager.getInstance().getMyDrugInfoModel()
					.getChildConcepts().size();
		}

		@Override
		public Object getItem(int position) {

			return DrugInformationManager.getInstance().getMyDrugInfoModel()
					.getChildConcepts().get(position);
		}

		@Override
		public long getItemId(int position) {

			return DrugInformationManager.getInstance().getMyDrugInfoModel()
					.getChildConcepts().indexOf(getItem(position));
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View row = convertView;
			MyPlaceHolder holder = null;

			if (row == null) {

				LayoutInflater inflater = (AvailableFormsListActivity.this)
						.getLayoutInflater();
				row = inflater.inflate(viewResourceId, parent, false);

				holder = new MyPlaceHolder();
				holder.listItemNameText = (TextView) row
						.findViewById(R.id.list_item_name_text);

				holder.signText = (TextView) row.findViewById(R.id.sign_text);
				
				row.setTag(holder);

			} else {

				holder = (MyPlaceHolder) row.getTag();
			}

			ConceptModel cm = DrugInformationManager.getInstance()
					.getMyDrugInfoModel().getChildConcepts().get(position);

			holder.listItemNameText.setText(cm.getConceptName());
			holder.signText.setVisibility(View.GONE);
			
			if (position % 2 == 0) {

				row.setBackgroundColor((AvailableFormsListActivity.this)
						.getResources().getColor(R.color.light_list_item_bg));

			} else {

				row.setBackgroundColor((AvailableFormsListActivity.this)
						.getResources().getColor(R.color.dark_list_item_bg));
			}

			return row;
		}

		class MyPlaceHolder {
			TextView listItemNameText;
			TextView signText;
		}

	}

}

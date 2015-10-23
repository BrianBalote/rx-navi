package org.balote.drugsearch.activities.drug.sub;

import java.util.ArrayList;

import org.balote.drugsearch.managers.DrugInformationManager;
import org.balote.drugsearch.models.basic.RoleModel;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ustg.drugsearch.R;

public class PhysiologicalEffectListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity_physiological_effect);

		ArrayList<RoleModel> list = new ArrayList<RoleModel>();

		for (RoleModel rm : DrugInformationManager.getInstance()
				.getMyDrugInfoModel().getGroupRoles()) {

			if (rm.getRoleName().equalsIgnoreCase("has_PE")) {

				list.add(rm);
			}
		}

		PhysiologicalEffectAdapter adapter = new PhysiologicalEffectAdapter(
				R.layout.list_item, list);

		setListAdapter(adapter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	private class PhysiologicalEffectAdapter extends BaseAdapter {

		private int viewResourceId;
		private ArrayList<RoleModel> list;

		public PhysiologicalEffectAdapter(int viewResourceId,
				ArrayList<RoleModel> list) {

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

				LayoutInflater inflater = (PhysiologicalEffectListActivity.this)
						.getLayoutInflater();
				row = inflater.inflate(viewResourceId, parent, false);

				holder = new MyPlaceHolder();
				holder.listItemNameText = (TextView) row
						.findViewById(R.id.list_item_name_text);

				row.setTag(holder);

			} else {

				holder = (MyPlaceHolder) row.getTag();
			}

			RoleModel rm = list.get(position);

			holder.listItemNameText.setText(rm.getRoleConceptModel()
					.getConceptName());

			if (position % 2 == 0) {

				row.setBackgroundColor((PhysiologicalEffectListActivity.this)
						.getResources().getColor(R.color.light_list_item_bg));

			} else {

				row.setBackgroundColor((PhysiologicalEffectListActivity.this)
						.getResources().getColor(R.color.dark_list_item_bg));
			}

			return row;
		}

		class MyPlaceHolder {
			TextView listItemNameText;
		}

	}
}

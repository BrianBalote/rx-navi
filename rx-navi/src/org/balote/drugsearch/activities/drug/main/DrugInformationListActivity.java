package org.balote.drugsearch.activities.drug.main;

import java.util.ArrayList;

import org.balote.drugsearch.activities.drug.sub.AvailableFormsListActivity;
import org.balote.drugsearch.activities.drug.sub.ConfigurationInteractionListActivity;
import org.balote.drugsearch.activities.drug.sub.MechanismOfActionListActivity;
import org.balote.drugsearch.activities.drug.sub.PhysiologicalEffectListActivity;
import org.balote.drugsearch.activities.drug.sub.PreventableDiseasesListActivity;
import org.balote.drugsearch.activities.drug.sub.TechnicalInformationListActivity;
import org.balote.drugsearch.activities.drug.sub.TreatableDiseasesListActivity;
import org.balote.drugsearch.managers.DrugInformationManager;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ustg.drugsearch.R;

public class DrugInformationListActivity extends ListActivity {

	private TextView drugNameText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity_drug_information);

		drugNameText = (TextView) findViewById(R.id.drug_name_text);
		drugNameText.setText(DrugInformationManager.getInstance()
				.getMyDrugInfoModel().getMainConcept().getConceptName());

		ArrayList<String> list = new ArrayList<String>();

		list.add(getResources().getString(R.string.title_technical_information));
		list.add(getResources().getString(R.string.title_available_forms));

		if (DrugInformationManager.getInstance().getMyDrugInfoModel()
				.isHasTreatableDiseases()) {

			list.add(getResources()
					.getString(R.string.title_treatable_diseases));
		}

		if (DrugInformationManager.getInstance().getMyDrugInfoModel()
				.isHasPreventableDiseases()) {

			list.add(getResources().getString(
					R.string.title_preventable_diseases));
		}

		if (DrugInformationManager.getInstance().getMyDrugInfoModel()
				.isHasConfigurationInteraction()) {

			list.add(getResources().getString(
					R.string.title_configuration_interation));
		}

		if (DrugInformationManager.getInstance().getMyDrugInfoModel()
				.isHasMechanismOfAction()) {

			list.add(getResources().getString(
					R.string.title_mechanism_of_action));
		}

		if (DrugInformationManager.getInstance().getMyDrugInfoModel()
				.isHasPhysiologicalEffect()) {

			list.add(getResources().getString(
					R.string.title_physiological_effect));
		}

		DrugInformationAdapter adapter = new DrugInformationAdapter(
				R.layout.list_item, DrugInformationListActivity.this, list);

		setListAdapter(adapter);
	}

	private class DrugInformationAdapter extends BaseAdapter {

		private int viewResourceId;
		private Context context;
		private ArrayList<String> list;

		public DrugInformationAdapter(int viewResourceId, Context context,
				ArrayList<String> list) {

			this.viewResourceId = viewResourceId;
			this.context = context;
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			View row = convertView;
			MyPlaceHolder holder = null;

			if (row == null) {

				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				row = inflater.inflate(viewResourceId, parent, false);

				holder = new MyPlaceHolder();
				holder.listItemNameText = (TextView) row
						.findViewById(R.id.list_item_name_text);

				row.setTag(holder);

			} else {

				holder = (MyPlaceHolder) row.getTag();
			}

			holder.listItemNameText.setText(list.get(position));

			if (position % 2 == 0) {

				row.setBackgroundColor(context.getResources().getColor(
						R.color.light_list_item_bg));

			} else {

				row.setBackgroundColor(context.getResources().getColor(
						R.color.dark_list_item_bg));
			}

			row.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					String listItem = list.get(position);
					Intent i = null;

					if (listItem.equalsIgnoreCase(getResources().getString(
							R.string.title_technical_information))) {

						i = new Intent(DrugInformationListActivity.this,
								TechnicalInformationListActivity.class);
						DrugInformationListActivity.this.startActivity(i);

					} else if (listItem.equalsIgnoreCase(getResources()
							.getString(R.string.title_available_forms))) {

						i = new Intent(DrugInformationListActivity.this,
								AvailableFormsListActivity.class);
						DrugInformationListActivity.this.startActivity(i);

					} else if (listItem.equalsIgnoreCase(getResources()
							.getString(R.string.title_treatable_diseases))) {

						i = new Intent(DrugInformationListActivity.this,
								TreatableDiseasesListActivity.class);
						DrugInformationListActivity.this.startActivity(i);

					} else if (listItem.equalsIgnoreCase(getResources()
							.getString(R.string.title_preventable_diseases))) {

						i = new Intent(DrugInformationListActivity.this,
								PreventableDiseasesListActivity.class);
						DrugInformationListActivity.this.startActivity(i);

					} else if (listItem
							.equalsIgnoreCase(getResources().getString(
									R.string.title_configuration_interation))) {

						i = new Intent(DrugInformationListActivity.this,
								ConfigurationInteractionListActivity.class);
						DrugInformationListActivity.this.startActivity(i);

					} else if (listItem.equalsIgnoreCase(getResources()
							.getString(R.string.title_mechanism_of_action))) {

						i = new Intent(DrugInformationListActivity.this,
								MechanismOfActionListActivity.class);
						DrugInformationListActivity.this.startActivity(i);

					} else if (listItem.equalsIgnoreCase(getResources()
							.getString(R.string.title_physiological_effect))) {

						i = new Intent(DrugInformationListActivity.this,
								PhysiologicalEffectListActivity.class);
						DrugInformationListActivity.this.startActivity(i);

					}

					overridePendingTransition(android.R.anim.fade_in,
							android.R.anim.fade_out);

				}
			});

			return row;
		}

		class MyPlaceHolder {
			TextView listItemNameText;
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}
}

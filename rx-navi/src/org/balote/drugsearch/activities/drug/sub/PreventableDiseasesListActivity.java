package org.balote.drugsearch.activities.drug.sub;

import java.util.ArrayList;

import org.balote.drugsearch.activities.extendables.ExtendableDiseaseListActivity;
import org.balote.drugsearch.managers.DrugInformationManager;
import org.balote.drugsearch.models.basic.RoleModel;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.ustg.drugsearch.R;

public class PreventableDiseasesListActivity extends
		ExtendableDiseaseListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity_preventable_diseases);

		myProgressBar = (ProgressBar) findViewById(R.id.my_progress_bar);
		myProgressBar.setVisibility(View.INVISIBLE);

		ArrayList<RoleModel> list = new ArrayList<RoleModel>();

		for (RoleModel rm : DrugInformationManager.getInstance()
				.getMyDrugInfoModel().getGroupRoles()) {

			if (rm.getRoleName().equalsIgnoreCase("may_prevent")
					|| rm.getRoleName()
							.equalsIgnoreCase("may_treat_or_prevent")) {

				list.add(rm);
			}
		}

		ExtendableDiseaseListAdapter adapter = new ExtendableDiseaseListAdapter(
				R.layout.list_item, list);

		setListAdapter(adapter);
	}

}

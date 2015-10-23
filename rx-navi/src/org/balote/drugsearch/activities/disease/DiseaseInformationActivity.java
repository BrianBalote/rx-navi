package org.balote.drugsearch.activities.disease;

import org.balote.drugsearch.managers.DiseaseInformationManager;
import org.balote.drugsearch.models.basic.PropertyModel;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.ustg.drugsearch.R;

public class DiseaseInformationActivity extends Activity {

	private TextView diseaseNameText;
	private TextView diseaseDefinitionText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disease_information);

		diseaseNameText = (TextView) findViewById(R.id.disease_name_text);
		diseaseNameText.setText(DiseaseInformationManager.getInstance()
				.getDiseaseInfoModel().getMainConcept().getConceptName());

		String definition = "";

		for (PropertyModel pm : DiseaseInformationManager.getInstance()
				.getDiseaseInfoModel().getGroupProperties()) {

			if (pm.getPropertyName().equalsIgnoreCase("MeSH_Definition")) {

				definition = pm.getPropertyValue();
			}
		}

		diseaseDefinitionText = (TextView) findViewById(R.id.disease_definition_text);
		diseaseDefinitionText.setText(definition);
	}

	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

}

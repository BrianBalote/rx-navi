package org.balote.drugsearch.activities.startup;

import org.balote.drugsearch.activities.drug.main.DrugInformationSearchActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.ustg.drugsearch.R;

public class LoginActivity extends Activity {

	private TextView clickHere = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		clickHere = (TextView) findViewById(R.id.click_here_text);
		clickHere.setText(Html.fromHtml(getResources().getString(
				R.string.click_here_string)));
		clickHere.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(LoginActivity.this,
						DrugInformationSearchActivity.class);
				LoginActivity.this.startActivity(i);
				LoginActivity.this.finish();

				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
			}
		});
	}

}

package org.balote.drugsearch.managers;

import org.balote.drugsearch.models.advance.InformationModel;

public class DrugInformationManager {

	private static DrugInformationManager _instance = null;
	private InformationModel myDrugInfoModel = null;

	private DrugInformationManager() {
	}

	public static DrugInformationManager getInstance() {
		if (_instance == null) {
			_instance = new DrugInformationManager();
		}
		return _instance;
	}

	public final InformationModel getMyDrugInfoModel() {
		return myDrugInfoModel;
	}

	public final void setMyDrugInfoModel(InformationModel myDrugInfoModel) {
		this.myDrugInfoModel = myDrugInfoModel;
	}

}

package org.balote.drugsearch.managers;

import org.balote.drugsearch.models.advance.InformationModel;

public class DiseaseInformationManager {

	private static DiseaseInformationManager _instance = null;
	private InformationModel diseaseInfoModel = null;

	private DiseaseInformationManager() {

	}

	public static DiseaseInformationManager getInstance() {
		if (_instance == null) {
			_instance = new DiseaseInformationManager();
		}

		return _instance;
	}

	public final InformationModel getDiseaseInfoModel() {
		return diseaseInfoModel;
	}

	public final void setDiseaseInfoModel(InformationModel diseaseInfoModel) {
		this.diseaseInfoModel = diseaseInfoModel;
	}

}

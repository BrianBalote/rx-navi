package org.balote.drugsearch.models.basic;

public class ConceptModel {

	public static final String CONCEPT_TAG = "concept";
	public static final String CONCEPT_NAME_TAG = "conceptName";
	public static final String CONCEPT_NUI_TAG = "conceptNui";
	public static final String CONCEPT_KIND_TAG = "conceptKind";

	private String conceptName = "";
	private String conceptNui = "";
	private String conceptKind = "";

	public ConceptModel() {
	}

	public ConceptModel(String conceptName, String conceptNui,
			String conceptKind) {

		this.conceptName = conceptName;
		this.conceptNui = conceptNui;
		this.conceptKind = conceptKind;
	}

	public String getConceptName() {
		return conceptName;
	}

	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}

	public String getConceptNui() {
		return conceptNui;
	}

	public void setConceptNui(String conceptNui) {
		this.conceptNui = conceptNui;
	}

	public String getConceptKind() {
		return conceptKind;
	}

	public void setConceptKind(String conceptKind) {
		this.conceptKind = conceptKind;
	}

}

package org.balote.drugsearch.parser;

import java.io.StringReader;

import org.balote.drugsearch.constants.XmlTagsConstants;
import org.balote.drugsearch.models.advance.InformationModel;
import org.balote.drugsearch.models.basic.ConceptModel;
import org.balote.drugsearch.models.basic.PropertyModel;
import org.balote.drugsearch.models.basic.RoleModel;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class InformationXmlParser {

	private static final String TAG = "InformationXmlParser";

	private static boolean atFullConcept = false;
	private static boolean atParentConcepts = false;
	private static boolean atChildConcepts = false;
	private static boolean atGroupProperties = false;
	private static boolean atGroupRoles = false;

	public static InformationModel parseInformationFromXml(String xmlString) {

		long startTime = System.currentTimeMillis();

		InformationModel myDrugInfoModel = new InformationModel();

		try {

			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();

			xpp.setInput(new StringReader(xmlString));

			int event = xpp.getEventType();
			String tag = "";
			ConceptModel cm = null;
			PropertyModel pm = null;
			RoleModel rm = null;

			while (event != XmlPullParser.END_DOCUMENT) {

				switch (event) {

				case XmlPullParser.START_DOCUMENT:
					break;

				case XmlPullParser.START_TAG:
					tag = xpp.getName();

					if (tag.equalsIgnoreCase(XmlTagsConstants.FULL_CONCEPT_TAG)
							&& !atFullConcept) {
						atFullConcept = true;
					} else if (atFullConcept) {
						if (tag.equalsIgnoreCase(ConceptModel.CONCEPT_NAME_TAG)) {
							myDrugInfoModel.getMainConcept().setConceptName(
									xpp.nextText());
						}
						if (tag.equalsIgnoreCase(ConceptModel.CONCEPT_NUI_TAG)) {
							myDrugInfoModel.getMainConcept().setConceptNui(
									xpp.nextText());
						}
						if (tag.equalsIgnoreCase(ConceptModel.CONCEPT_KIND_TAG)) {
							myDrugInfoModel.getMainConcept().setConceptKind(
									xpp.nextText());
						}
					}

					if (tag.equalsIgnoreCase(XmlTagsConstants.PARENT_CONCEPTS_TAG)
							&& !atParentConcepts) {
						atFullConcept = false;
						atParentConcepts = true;
					} else if (atParentConcepts) {

						if (tag.equalsIgnoreCase(XmlTagsConstants.CONCEPT_TAG)) {
							cm = new ConceptModel();
						}
						if (tag.equalsIgnoreCase(ConceptModel.CONCEPT_NAME_TAG)) {
							cm.setConceptName(xpp.nextText());
						}
						if (tag.equalsIgnoreCase(ConceptModel.CONCEPT_NUI_TAG)) {
							cm.setConceptNui(xpp.nextText());
						}
						if (tag.equalsIgnoreCase(ConceptModel.CONCEPT_KIND_TAG)) {
							cm.setConceptKind(xpp.nextText());
						}
					}

					if (tag.equalsIgnoreCase(XmlTagsConstants.CHILD_CONCEPTS_TAG)
							&& !atChildConcepts) {
						atParentConcepts = false;
						atChildConcepts = true;
					} else if (atChildConcepts) {

						if (tag.equalsIgnoreCase(XmlTagsConstants.CONCEPT_TAG)) {
							cm = new ConceptModel();
						}
						if (tag.equalsIgnoreCase(ConceptModel.CONCEPT_NAME_TAG)) {
							cm.setConceptName(xpp.nextText());
						}
						if (tag.equalsIgnoreCase(ConceptModel.CONCEPT_NUI_TAG)) {
							cm.setConceptNui(xpp.nextText());
						}
						if (tag.equalsIgnoreCase(ConceptModel.CONCEPT_KIND_TAG)) {
							cm.setConceptKind(xpp.nextText());
						}
					}

					if (tag.equalsIgnoreCase(XmlTagsConstants.GROUP_PROPERTIES_TAG)
							&& !atGroupProperties) {

						atChildConcepts = false;
						atGroupProperties = true;
					} else if (atGroupProperties) {

						if (tag.equalsIgnoreCase(XmlTagsConstants.PROPERTY_TAG)) {
							pm = new PropertyModel();
						}
						if (tag.equalsIgnoreCase(PropertyModel.PROPERTY_NAME_TAG)) {
							pm.setPropertyName(xpp.nextText());
						}
						if (tag.equalsIgnoreCase(PropertyModel.PROPERTY_VALUE_TAG)) {
							pm.setPropertyValue(xpp.nextText());
						}
					}

					if (tag.equalsIgnoreCase(XmlTagsConstants.GROUP_ROLES_TAG)
							&& !atGroupRoles) {
						atGroupProperties = false;
						atGroupRoles = true;
					} else if (atGroupRoles) {

						if (tag.equals(XmlTagsConstants.ROLE_TAG)) {
							rm = new RoleModel();
						}
						if (tag.equalsIgnoreCase(RoleModel.ROLE_NAME_TAG)) {
							rm.setRoleName(xpp.nextText());

							if (rm.getRoleName().equalsIgnoreCase(
									XmlTagsConstants.MAY_PREVENT_TAG)) {
								myDrugInfoModel.setHasPreventableDiseases(true);
							}
							if (rm.getRoleName().equalsIgnoreCase(
									XmlTagsConstants.MAY_TREAT_TAG)) {
								myDrugInfoModel.setHasTreatableDiseases(true);
							}
							if (rm.getRoleName().equalsIgnoreCase(
									XmlTagsConstants.MAY_TREAT_OR_PREVENT_TAG)) {
								myDrugInfoModel.setHasTreatableDiseases(true);
								myDrugInfoModel.setHasPreventableDiseases(true);
							}
							if (rm.getRoleName().equalsIgnoreCase(
									XmlTagsConstants.CI_WITH_TAG)) {
								myDrugInfoModel
										.setHasConfigurationInteraction(true);
							}
							if (rm.getRoleName().equalsIgnoreCase(
									XmlTagsConstants.HAS_MOA_TAG)) {
								myDrugInfoModel.setHasMechanismOfAction(true);
							}
							if (rm.getRoleName().equalsIgnoreCase(
									XmlTagsConstants.HAS_PE_TAG)) {

								myDrugInfoModel.setHasPhysiologicalEffect(true);
							}
						}
						if (tag.equalsIgnoreCase(XmlTagsConstants.CONCEPT_TAG)) {
							cm = new ConceptModel();
						}
						if (tag.equalsIgnoreCase(ConceptModel.CONCEPT_NAME_TAG)) {
							cm.setConceptName(xpp.nextText());
						}
						if (tag.equalsIgnoreCase(ConceptModel.CONCEPT_NUI_TAG)) {
							cm.setConceptNui(xpp.nextText());
						}
						if (tag.equalsIgnoreCase(ConceptModel.CONCEPT_KIND_TAG)) {
							cm.setConceptKind(xpp.nextText());
						}
					}

					break;

				case XmlPullParser.END_TAG:
					tag = xpp.getName();

					if (atParentConcepts) {

						if (tag.equalsIgnoreCase(XmlTagsConstants.CONCEPT_TAG)
								&& cm != null) {
							myDrugInfoModel.getParentConcepts().add(cm);
						}
					}

					if (atChildConcepts) {

						if (tag.equalsIgnoreCase(XmlTagsConstants.CONCEPT_TAG)
								&& cm != null) {
							myDrugInfoModel.getChildConcepts().add(cm);
						}
					}

					if (atGroupProperties) {

						if (tag.equalsIgnoreCase(XmlTagsConstants.PROPERTY_TAG)
								&& pm != null) {
							myDrugInfoModel.getGroupProperties().add(pm);
						}
					}

					if (atGroupRoles) {

						if (tag.equalsIgnoreCase(XmlTagsConstants.CONCEPT_TAG)
								&& rm != null && cm != null) {
							rm.setRoleConceptModel(cm);
						}

						if (tag.equalsIgnoreCase(XmlTagsConstants.ROLE_TAG)
								&& rm != null) {
							myDrugInfoModel.getGroupRoles().add(rm);
						}
					}

					if (tag.equalsIgnoreCase(XmlTagsConstants.GROUP_ROLES_TAG)) {
						atGroupRoles = false;
					}

					break;
				}

				event = xpp.next();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		long executionTime = System.currentTimeMillis() - startTime;
		Log.i(TAG, "@ parseDrugInfo() execution time: " + executionTime
				+ " millis");

		test(myDrugInfoModel);

		return myDrugInfoModel;
	}

	public static void test(InformationModel myDrugInfoModel) {

		if (myDrugInfoModel == null) {
			return;
		}

		Log.i(TAG, "concept name: "
				+ myDrugInfoModel.getMainConcept().getConceptName());
		Log.i(TAG, "concept nui: "
				+ myDrugInfoModel.getMainConcept().getConceptNui());
		Log.i(TAG, "concept kind: "
				+ myDrugInfoModel.getMainConcept().getConceptKind());

		for (ConceptModel cm : myDrugInfoModel.getParentConcepts()) {

			Log.w(TAG, "parent concept name: " + cm.getConceptName());
			Log.w(TAG, "parent concept nui: " + cm.getConceptNui());
			Log.w(TAG, "parent concept kind: " + cm.getConceptKind());
		}

		for (ConceptModel cm : myDrugInfoModel.getChildConcepts()) {

			Log.d(TAG, "child concept name: " + cm.getConceptName());
			Log.d(TAG, "child concept nui: " + cm.getConceptNui());
			Log.d(TAG, "child concept kind: " + cm.getConceptKind());
		}

		for (PropertyModel pm : myDrugInfoModel.getGroupProperties()) {

			Log.i(TAG, "group property name: " + pm.getPropertyName());
			Log.i(TAG, "group property value: " + pm.getPropertyValue());
		}

		for (RoleModel rm : myDrugInfoModel.getGroupRoles()) {

			Log.w(TAG, "role name: " + rm.getRoleName());
			Log.d(TAG, "role concept name: "
					+ rm.getRoleConceptModel().getConceptName());
			Log.d(TAG, "role concept nui: "
					+ rm.getRoleConceptModel().getConceptNui());
			Log.d(TAG, "role concept kind: "
					+ rm.getRoleConceptModel().getConceptKind());
		}
	}

}

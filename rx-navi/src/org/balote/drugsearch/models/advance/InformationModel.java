package org.balote.drugsearch.models.advance;

import java.util.ArrayList;

import org.balote.drugsearch.models.basic.ConceptModel;
import org.balote.drugsearch.models.basic.PropertyModel;
import org.balote.drugsearch.models.basic.RoleModel;

public class InformationModel {

	private ConceptModel mainConcept = null;
	private ArrayList<ConceptModel> parentConcepts = null;
	private ArrayList<ConceptModel> childConcepts = null;
	private ArrayList<PropertyModel> groupProperties = null;
	private ArrayList<RoleModel> groupRoles = null;
	private boolean hasTreatableDiseases = false;
	private boolean hasPreventableDiseases = false;
	private boolean hasConfigurationInteraction = false;
	private boolean hasPhysiologicalEffect = false;
	private boolean hasMechanismOfAction = false;

	public InformationModel() {

		mainConcept = new ConceptModel();
		parentConcepts = new ArrayList<ConceptModel>();
		childConcepts = new ArrayList<ConceptModel>();
		groupProperties = new ArrayList<PropertyModel>();
		groupRoles = new ArrayList<RoleModel>();
	}

	public final ConceptModel getMainConcept() {
		return mainConcept;
	}

	public final void setMainConcept(ConceptModel mainConcept) {
		this.mainConcept = mainConcept;
	}

	public final ArrayList<ConceptModel> getParentConcepts() {
		return parentConcepts;
	}

	public final void setParentConcepts(ArrayList<ConceptModel> parentConcepts) {
		this.parentConcepts = parentConcepts;
	}

	public final ArrayList<ConceptModel> getChildConcepts() {
		return childConcepts;
	}

	public final void setChildConcepts(ArrayList<ConceptModel> childConcepts) {
		this.childConcepts = childConcepts;
	}

	public final ArrayList<PropertyModel> getGroupProperties() {
		return groupProperties;
	}

	public final void setGroupProperties(
			ArrayList<PropertyModel> groupProperties) {
		this.groupProperties = groupProperties;
	}

	public final ArrayList<RoleModel> getGroupRoles() {
		return groupRoles;
	}

	public final void setGroupRoles(ArrayList<RoleModel> groupRoles) {
		this.groupRoles = groupRoles;
	}

	public final boolean isHasTreatableDiseases() {
		return hasTreatableDiseases;
	}

	public final void setHasTreatableDiseases(boolean hasTreatableDiseases) {
		this.hasTreatableDiseases = hasTreatableDiseases;
	}

	public final boolean isHasPreventableDiseases() {
		return hasPreventableDiseases;
	}

	public final void setHasPreventableDiseases(boolean hasPreventableDiseases) {
		this.hasPreventableDiseases = hasPreventableDiseases;
	}

	public final boolean isHasConfigurationInteraction() {
		return hasConfigurationInteraction;
	}

	public final void setHasConfigurationInteraction(
			boolean hasConfigurationInteraction) {
		this.hasConfigurationInteraction = hasConfigurationInteraction;
	}

	public final boolean isHasPhysiologicalEffect() {
		return hasPhysiologicalEffect;
	}

	public final void setHasPhysiologicalEffect(boolean hasPhysiologicalEffect) {
		this.hasPhysiologicalEffect = hasPhysiologicalEffect;
	}

	public final boolean isHasMechanismOfAction() {
		return hasMechanismOfAction;
	}

	public final void setHasMechanismOfAction(boolean hasModeOfActivation) {
		this.hasMechanismOfAction = hasModeOfActivation;
	}

}

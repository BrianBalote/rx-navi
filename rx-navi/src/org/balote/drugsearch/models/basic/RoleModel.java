package org.balote.drugsearch.models.basic;

public class RoleModel {

	public static final String ROLE_NAME_TAG = "roleName";

	private String roleName = "";
	private ConceptModel roleConceptModel = null;

	public RoleModel() {
		roleConceptModel = new ConceptModel();
	}

	public RoleModel(String roleName, ConceptModel roleConceptModel) {
		this.roleName = roleName;
		this.roleConceptModel = roleConceptModel;
	}

	public final String getRoleName() {
		return roleName;
	}

	public final void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public final ConceptModel getRoleConceptModel() {
		return roleConceptModel;
	}

	public final void setRoleConceptModel(ConceptModel roleConceptModel) {
		this.roleConceptModel = roleConceptModel;
	}

}

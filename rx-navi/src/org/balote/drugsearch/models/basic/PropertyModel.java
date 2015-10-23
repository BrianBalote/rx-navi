package org.balote.drugsearch.models.basic;

public class PropertyModel {

	public static final String PROPERTY_NAME_TAG = "propertyName";
	public static final String PROPERTY_VALUE_TAG = "propertyValue";

	private String propertyName = "";
	private String propertyValue = "";

	public PropertyModel() {

	}

	public PropertyModel(String propertyName, String propertyValue) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

}

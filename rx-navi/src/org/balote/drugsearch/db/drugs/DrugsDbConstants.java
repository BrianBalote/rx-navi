package org.balote.drugsearch.db.drugs;

public final class DrugsDbConstants {

	public static final String TABLE_NAME = "drugs_table";
	public static final String COL_NAME = "name";
	public static final String COL_NUI = "nui";

	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ " ( " + " id INTEGER PRIMARY KEY, " + COL_NAME + " TEXT, "
			+ COL_NUI + " TEXT " + " );";

	public static final String DROP_TABLE = "DROP TABLE IF EXISTS "
			+ TABLE_NAME;

	public static final String INSERT_DRUG = "INSERT INTO " + TABLE_NAME
			+ " ( " + COL_NAME + " , " + COL_NUI + " ) VALUES(?,?)";
	
	public static final String FIND_DRUG_QUERY = "SELECT * FROM " + DrugsDbConstants.TABLE_NAME
			+ " WHERE " + DrugsDbConstants.COL_NAME + " LIKE ?";
}

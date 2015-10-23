package org.balote.drugsearch.db.api;


public interface IDao {

	public void close();

	public void open();

	public void read();

	public void clearTable();
}

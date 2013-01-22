package org.genomesmanager.repositories.configuration;

public interface ConfigurationRepo {

	public abstract String getValue(String category, String key);
	void setValue(String category, String key, String value);

}
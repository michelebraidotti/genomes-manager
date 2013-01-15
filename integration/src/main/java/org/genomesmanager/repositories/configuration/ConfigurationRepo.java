package org.genomesmanager.repositories.configuration;

public interface ConfigurationRepo {

	public abstract String getValue(String category, String key);

}
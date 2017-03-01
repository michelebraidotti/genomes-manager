package org.genomesmanager.repositories.configuration;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ConfigurationRepository {

	public abstract String getValue(String category, String key);
	void setValue(String category, String key, String value);

}
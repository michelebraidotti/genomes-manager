package org.genomesmanager.repositories.configuration;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ConfigurationRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	@Test
	public void getSetValue() {
		String category = "c";
		String key = "k";
		String value = "v";
		configurationRepository.setValue(category, key, value);
		assertEquals(value, configurationRepository.getValue(category, key));
	}

}

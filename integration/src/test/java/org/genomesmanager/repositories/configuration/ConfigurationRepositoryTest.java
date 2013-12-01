package org.genomesmanager.repositories.configuration;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.repositories.configuration.ConfigurationRepository;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ConfigurationRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	private ConfigurationRepository configurationRepo;
	
	@Test
	public void getSetValue() {
		String category = "c";
		String key = "k";
		String value = "v";
		configurationRepo.setValue(category, key, value);
		assertEquals(value, configurationRepo.getValue(category, key));
	}

}

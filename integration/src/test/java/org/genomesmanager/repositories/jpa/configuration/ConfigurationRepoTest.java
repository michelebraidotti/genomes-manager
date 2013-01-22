package org.genomesmanager.repositories.jpa.configuration;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.repositories.configuration.ConfigurationRepo;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ConfigurationRepoTest extends AbstractIntegrationTest {
	@Autowired
	private ConfigurationRepo configurationRepo;
	
	@Test
	public void getSetValue() {
		String category = "c";
		String key = "k";
		String value = "v";
		configurationRepo.setValue(category, key, value);
		assertEquals(value, configurationRepo.getValue(category, key));
	}

}

package org.genomesmanager.repositories.jpa.configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.repositories.configuration.ConfigurationRepo;
import org.springframework.stereotype.Repository;

@Repository("ConfigurationRepo")
public class ConfigurationRepoJpa implements ConfigurationRepo  {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	
	public ConfigurationRepoJpa() {
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.configuration.Configuration#getValue(java.lang.String, java.lang.String)
	 */
	@Override
	public String getValue(String category, String key) {
		// TODO throw exception ???
		String qry ="SELECT value FROM _sys_config " +
			"WHERE category = '" + category + "' AND keyword = '" + key + "'";
		q = em.createNativeQuery(qry);
		return (String) q.getSingleResult();
	}
	
	@Override
	public void setValue(String category, String key, String value) {
		String qry = "INSERT INTO _sys_config VALUES ('" + category + "', '" + key + "', '" + value + "')";
		q = em.createNativeQuery(qry);
		q.executeUpdate();
	}
}

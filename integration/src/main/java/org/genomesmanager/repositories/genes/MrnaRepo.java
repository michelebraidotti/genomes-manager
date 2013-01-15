package org.genomesmanager.repositories.genes;

import org.genomesmanager.domain.entities.Mrna;

public interface MrnaRepo {

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.ExonRepo#get(int)
	 */
	public abstract Mrna get(int id);

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.ExonRepo#get(java.lang.String)
	 */
	public abstract Mrna get(String name);

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.ExonRepo#insert(org.genomesmanager.domain.entities.Exon)
	 */
	public abstract void insert(Mrna mrna);

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.ExonRepo#update(org.genomesmanager.domain.entities.Exon)
	 */
	public abstract void update(Mrna mrna);

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.ExonRepo#delete(int)
	 */
	public abstract void delete(int id);

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.ExonRepo#delete(org.genomesmanager.domain.entities.Exon)
	 */
	public abstract void delete(Mrna mrna);

}
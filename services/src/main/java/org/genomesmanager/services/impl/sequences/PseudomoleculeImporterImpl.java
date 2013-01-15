package org.genomesmanager.services.impl.sequences;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.repositories.sequences.ChromosomeRepo;
import org.genomesmanager.repositories.sequences.ChromosomeRepoException;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.services.sequences.PseudomoleculeImporter;
import org.genomesmanager.services.sequences.PseudomoleculeImporterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("PseudomoleculeImporter")
public class PseudomoleculeImporterImpl implements PseudomoleculeImporter {
	@Autowired
	private ChromosomeRepo chrRepo;
	@Autowired
	private SequenceRepo seqRepo;
	
    public PseudomoleculeImporterImpl() {
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.PseudomoleculeImporter#importPseudomolecule(int, java.lang.StringBuilder, java.lang.String, java.lang.String)
	 */
    @Override
	public void importPseudomolecule(int chrId, StringBuilder sequenceBulilder, String name, 
    		String version) throws  PseudomoleculeImporterException {
    	Pseudomolecule pm = new Pseudomolecule();
    	Chromosome c;
		try {
			c = chrRepo.get(chrId);
		} 
		catch (ChromosomeRepoException e) {
			throw new PseudomoleculeImporterException("ChromosomeEAException: " + 
					e.getMessage());
		}
		String sequence = sequenceBulilder.toString();
    	pm.setSequence(sequence);
    	pm.setLength(sequence.length());
    	pm.setName(name);
    	pm.setVersion(version);
    	pm.setChromosome(c);
    	seqRepo.insert(pm);
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.PseudomoleculeImporter#updatePseudomolecule(int, java.lang.StringBuilder)
	 */
    @Override
	public void updatePseudomolecule(int seqId, 
    		StringBuilder sequenceBulilder) throws  PseudomoleculeImporterException {
    	throw new PseudomoleculeImporterException("Not yet implemented");
    }
}

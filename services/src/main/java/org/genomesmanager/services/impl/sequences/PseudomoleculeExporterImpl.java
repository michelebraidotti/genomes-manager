package org.genomesmanager.services.impl.sequences;

import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.domain.entities.SequenceSliceException;
import org.genomesmanager.repositories.sequences.PseudomoleculeRepo;
import org.genomesmanager.repositories.sequences.SequenceRepoException;
import org.genomesmanager.services.sequences.PseudomoleculeExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("PseudomoleculeExporter")
public class PseudomoleculeExporterImpl implements PseudomoleculeExporter {
	@Autowired
	private PseudomoleculeRepo pseudomoleculeRepo;
	
    public PseudomoleculeExporterImpl() {
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.PseudomoleculeExporter#get(int, boolean)
	 */
    @Override
	public Pseudomolecule get(int id, boolean masked) throws SequenceSliceException, SequenceRepoException {
    	Pseudomolecule p = pseudomoleculeRepo.get(id);
    	if ( p.getIsScaffoldDerived() ) {
    		StringBuilder seq = new StringBuilder();
    		if ( p.getIsUnplaced() ) {
    			seq = pseudomoleculeRepo.getFromChromosomeUnplaced(p.getChromosome().getId(), masked);
    		}
    		else {
    			seq = pseudomoleculeRepo.getFromChromosome(p.getChromosome().getId(), masked);
    		}
    		p.setSequenceText(seq.toString());
    		p.setLength(seq.length());
    	}
    	return p;
    }		

}

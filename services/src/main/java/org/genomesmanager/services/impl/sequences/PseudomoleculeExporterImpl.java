package org.genomesmanager.services.impl.sequences;

import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.domain.entities.SequenceSliceException;
import org.genomesmanager.repositories.sequences.PseudomoleculeRepository;
import org.genomesmanager.repositories.sequences.SequenceRepoException;
import org.genomesmanager.services.sequences.PseudomoleculeExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("PseudomoleculeExporter")
public class PseudomoleculeExporterImpl implements PseudomoleculeExporter {
	@Autowired
	private PseudomoleculeRepository pseudomoleculeRepository;
	
    public PseudomoleculeExporterImpl() {
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.PseudomoleculeExporter#get(int, boolean)
	 */
    @Override
	public Pseudomolecule get(int id, boolean masked) throws SequenceSliceException, SequenceRepoException {
    	Pseudomolecule p = pseudomoleculeRepository.findOne(id);
    	if ( p.getIsScaffoldDerived() ) {
    		StringBuilder seq = new StringBuilder();
    		if ( p.getIsUnplaced() ) {
    			seq = pseudomoleculeRepository.getFromChromosomeUnplaced(p.getChromosome().getId(), masked);
    		}
    		else {
    			seq = pseudomoleculeRepository.getFromChromosome(p.getChromosome().getId(), masked);
    		}
    		p.setSequenceText(seq.toString());
    		p.setLength(seq.length());
    	}
    	return p;
    }		

}

package org.genomesmanager.services.impl.sequences;

import org.genomesmanager.bioprograms.formats.SimpleFasta;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.services.sequences.PseudomoleculeImporter;
import org.genomesmanager.services.sequences.PseudomoleculeImporterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("PseudomoleculeImporter")
@Transactional
public class PseudomoleculeImporterImpl implements PseudomoleculeImporter {
	@Autowired
	private ChromosomeRepository chromosomeRepository;
	@Autowired
	private SequenceRepository sequenceRepository;
	
    public PseudomoleculeImporterImpl() {
    }
    
    @Override
	public void importPseudomolecule(int chrId, List<SimpleFasta> fastas,
			String version) throws PseudomoleculeImporterException {
    	for (SimpleFasta fasta:fastas) {
    			importPseudomolecule(chrId, new StringBuilder(fasta.getSequence()), fasta.getId(), version);
		}
	}



	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.PseudomoleculeImporter#importPseudomolecule(int, java.lang.StringBuilder, java.lang.String, java.lang.String)
	 */
    @Override
	public void importPseudomolecule(int chrId, StringBuilder sequenceBulilder, String name, 
    		String version) throws  PseudomoleculeImporterException {
    	Pseudomolecule pm = new Pseudomolecule();
    	Chromosome c = chromosomeRepository.findOne(chrId);
		if ( c == null) throw  new PseudomoleculeImporterException("Chromosome with Id '"  + chrId + "' not found.");
		String sequence = sequenceBulilder.toString();
    	pm.setSequenceText(sequence);
    	pm.setLength(sequence.length());
    	pm.setName(name);
    	pm.setVersion(version);
    	pm.setChromosome(c);
    	sequenceRepository.save(pm);
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

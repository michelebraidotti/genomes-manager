package org.genomesmanager.services.sequences;

import org.genomesmanager.formats.SimpleFasta;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("PseudomoleculeImporter")
@Transactional
public class PseudomoleculeImporter {
	@Autowired
	private ChromosomeRepository chromosomeRepository;
	@Autowired
	private SequenceRepository sequenceRepository;
	
    public PseudomoleculeImporter() {
    }
    
	public void importPseudomolecule(int chrId, List<SimpleFasta> fastas,
			String version) throws PseudomoleculeImporterException {
    	for (SimpleFasta fasta:fastas) {
    			importPseudomolecule(chrId, new StringBuilder(fasta.getSequence()), fasta.getId(), version);
		}
	}

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
    
	public void updatePseudomolecule(int seqId,
    		StringBuilder sequenceBulilder) throws  PseudomoleculeImporterException {
    	throw new PseudomoleculeImporterException("Not yet implemented");
    }
}

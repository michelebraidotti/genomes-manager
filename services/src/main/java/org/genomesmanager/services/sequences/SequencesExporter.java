package org.genomesmanager.services.sequences;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.SequenceSliceException;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("SequencesExporter")
public class SequencesExporter {
	@Autowired
	private SequenceRepository sequenceRepository;
	
    public SequencesExporter() {
    }
    
	public List<String> getFastaContent(Chromosome chr) {
		List<String> out = new ArrayList<String>();
		for (Sequence seq: sequenceRepository.findByChromosome(chr)) {
			out.add(seq.getFastaHeader());
			out.add(seq.getSequenceText());
		}
		return out;
    }

	public List<String> getMaskedFastaContent(Chromosome chr) throws SequenceSliceException {
		List<String> out = new ArrayList<String>();
		for (Sequence seq: sequenceRepository.findByChromosome(chr)) {
			out.add(seq.getFastaHeader());
			out.add(seq.getMaskedSequence());
		}
		return out;
    }
	
}

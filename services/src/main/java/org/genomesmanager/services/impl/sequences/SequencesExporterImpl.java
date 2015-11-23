package org.genomesmanager.services.impl.sequences;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.SequenceSliceException;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.services.sequences.SequencesExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("SequencesExporter")
public class SequencesExporterImpl implements SequencesExporter {
	@Autowired
	private SequenceRepository sequenceRepository;
	
    public SequencesExporterImpl() {
    }
    
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.SequencesExporter#getFastaContentByChr(int)
	 */
	@Override
    public List<String> getFastaContent(Chromosome chr) {
		List<String> out = new ArrayList<String>();
		for (Sequence seq: sequenceRepository.findByChromosome(chr)) {
			out.add(seq.getFastaHeader());
			out.add(seq.getSequenceText());
		}
		return out;
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.SequencesExporter#getMaskedFastaContentByChr(int)
	 */
	@Override
    public List<String> getMaskedFastaContent(Chromosome chr) throws SequenceSliceException {
		List<String> out = new ArrayList<String>();
		for (Sequence seq: sequenceRepository.findByChromosome(chr)) {
			out.add(seq.getFastaHeader());
			out.add(seq.getMaskedSequence());
		}
		return out;
    }
	
}

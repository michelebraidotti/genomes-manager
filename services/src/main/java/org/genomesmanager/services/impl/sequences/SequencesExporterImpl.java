package org.genomesmanager.services.impl.sequences;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.SequenceSliceException;
import org.genomesmanager.repositories.sequences.ChromosomeRepo;
import org.genomesmanager.repositories.sequences.SequencesList;
import org.genomesmanager.services.sequences.SequencesExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SequencesExporter")
public class SequencesExporterImpl implements SequencesExporter {
	@Autowired
	private SequencesList sequencesList;
	
    public SequencesExporterImpl() {
    }
    
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.SequencesExporter#getFastaContentByChr(int)
	 */
	@Override
    public List<String> getFastaContent(Chromosome chr) {
		List<String> out = new ArrayList<String>();
		for (Sequence seq:sequencesList.getAllByChromosome(chr.getId())) {
			out.add(seq.getFastaHeader());
			out.add(seq.getSequence());
		}
		return out;
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.SequencesExporter#getMaskedFastaContentByChr(int)
	 */
	@Override
    public List<String> getMaskedFastaContent(Chromosome chr) throws SequenceSliceException {
		List<String> out = new ArrayList<String>();
		for (Sequence seq:sequencesList.getAllByChromosome(chr.getId())) {
			out.add(seq.getFastaHeader());
			out.add(seq.getMaskedSequence());
		}
		return out;
    }
	
}

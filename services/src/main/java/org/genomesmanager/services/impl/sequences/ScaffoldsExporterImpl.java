package org.genomesmanager.services.impl.sequences;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.common.formats.SimpleFasta;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.SequenceSliceException;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.sequences.ScaffoldsList;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.genomesmanager.services.sequences.ScaffoldsExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ScaffoldsExporter")
public class ScaffoldsExporterImpl implements ScaffoldsExporter {
	@Autowired
	private SpeciesRepo speciesRepo;
	@Autowired
	private ScaffoldsList scaffoldsList;
	
    public ScaffoldsExporterImpl() {
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.ScaffoldsExporter#allSequencesBySpecies(java.lang.String, java.lang.Boolean)
	 */
    @Override
	public List<SimpleFasta> getAllSequencesBySpecies(String speciesDefinition, 
    		Boolean maskSequence) throws SpeciesRepoException, SequenceSliceException {
		Species s = speciesRepo.get(speciesDefinition);
    	return convertScaffoldsToFasta(scaffoldsList.getAllBySpecies(s.getId()), maskSequence);
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.ScaffoldsExporter#alSequenceslByChromosome(int, java.lang.Boolean)
	 */
    @Override
	public List<SimpleFasta> getAllSequencesByChromosome(int chrId, 
    		Boolean maskSequence) throws SpeciesRepoException, SequenceSliceException {
    	return convertScaffoldsToFasta(scaffoldsList.getAllByChromosome(chrId), maskSequence);
    }
    
	private List <SimpleFasta> convertScaffoldsToFasta(List<Scaffold> scaffs, 
			Boolean maskSequences) throws SequenceSliceException {
		List <SimpleFasta> fastas = new ArrayList<SimpleFasta>(scaffs.size());
		for (Scaffold s:scaffs) {
			SimpleFasta f = null;
			if (maskSequences) {
				f = new SimpleFasta(s.getName() + "_masked.fa", s.getFastaHeader(), s.getMaskedSequence());
			}
			else {
				f = new SimpleFasta(s.getName() + ".fa", s.getFastaHeader(), s.getSequenceText());
			}
			fastas.add(f);
		}
    	return fastas;
    }
}

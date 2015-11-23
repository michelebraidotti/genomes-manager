package org.genomesmanager.services.impl.sequences;

import org.genomesmanager.common.formats.SimpleFasta;
import org.genomesmanager.domain.dtos.CannotParseSpeciesDefinitionException;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.SequenceSliceException;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.ScaffoldRepository;
import org.genomesmanager.services.sequences.ScaffoldsExporter;
import org.genomesmanager.services.species.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ScaffoldsExporter")
public class ScaffoldsExporterImpl implements ScaffoldsExporter {
	@Autowired
	private SpeciesService speciesService;
	@Autowired
	private ScaffoldRepository scaffoldRepository;
	@Autowired
	private ChromosomeRepository chromosomeRepository;
	
    public ScaffoldsExporterImpl() {
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.ScaffoldsExporter#allSequencesBySpecies(java.lang.String, java.lang.Boolean)
	 */
    @Override
	public List<SimpleFasta> getAllSequencesBySpecies(String speciesDefinition, 
    		Boolean maskSequence) throws SequenceSliceException, CannotParseSpeciesDefinitionException {
		Species s = speciesService.get(speciesDefinition);
    	return convertScaffoldsToFasta(scaffoldRepository.findByChromosomeSpecies(s), maskSequence);
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.ScaffoldsExporter#alSequenceslByChromosome(int, java.lang.Boolean)
	 */
    @Override
	public List<SimpleFasta> getAllSequencesByChromosome(int chrId, 
    		Boolean maskSequence) throws SequenceSliceException {
		Chromosome c = chromosomeRepository.findOne(chrId);
    	return convertScaffoldsToFasta(scaffoldRepository.findByChromosome(c), maskSequence);
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

package org.genomesmanager.services.impl.sequences;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.SequenceSliceException;
import org.genomesmanager.repositories.sequences.PseudomoleculeRepository;
import org.genomesmanager.repositories.sequences.ScaffoldRepository;
import org.genomesmanager.services.sequences.PseudomoleculeExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PseudomoleculeExporter")
public class PseudomoleculeExporterImpl implements PseudomoleculeExporter {
	@Autowired
	private PseudomoleculeRepository pseudomoleculeRepository;
	@Autowired
	private ScaffoldRepository scaffoldRepository;

    @Override
	public Pseudomolecule get(int id, boolean masked) throws SequenceSliceException {
    	Pseudomolecule p = pseudomoleculeRepository.findOne(id);
    	if ( p.isScaffoldDerived() ) {
    		StringBuilder seq;
    		if ( p.isUnplaced() ) {
    			seq = getFromChromosomeUnplaced(p.getChromosome(), masked);
    		}
    		else {
    			seq = getFromChromosome(p.getChromosome(), masked);
    		}
    		p.setSequenceText(seq.toString());
    		p.setLength(seq.length());
    	}
    	return p;
    }

	private StringBuilder getFromChromosome(Chromosome chromosome, boolean masked)
			throws SequenceSliceException {

		List<Scaffold> scafs = scaffoldRepository.findAllPlacedByChromosome(chromosome);
		StringBuilder out = new StringBuilder();
		for (Scaffold scaf:scafs) {
			if (masked) {
				out.append(scaf.getMaskedSequence());
			}
			else {
				out.append(scaf.getSequenceText());
			}
			out.append(Pseudomolecule.SCAFFOLDS_SPACER());
		}
		out.delete(out.length() - 100, out.length() + 1);
		return out;
	}

	private StringBuilder getFromChromosomeUnplaced(Chromosome chromosome, boolean masked)
			throws SequenceSliceException {

		List<Scaffold> scafs = scaffoldRepository.findAllUnplacedByChromosome(chromosome);
		StringBuilder out = new StringBuilder();
		if ( scafs.size() == 0)
			return out;
		for (Scaffold scaf:scafs) {
			if (masked) {
				out.append(scaf.getMaskedSequence());
			}
			else {
				out.append(scaf.getSequenceText());
			}
			out.append(Pseudomolecule.SCAFFOLDS_SPACER());
		}
		out.delete(out.length() - Pseudomolecule.SCAFFOLDS_SPACER_SIZE, out.length() + 1);
		return out;
	}


}

package org.genomesmanager.services.impl.snps;

import org.genomesmanager.domain.entities.Individual;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Snp;
import org.genomesmanager.domain.entities.Variety;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.repositories.snps.SnpRepository;
import org.genomesmanager.repositories.species.IndividualRepository;
import org.genomesmanager.repositories.species.VarietyRepository;
import org.genomesmanager.services.snp.SnpsImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("SnpsImporter")
@Scope("prototype")
public class SnpsImporterImpl implements SnpsImporter {
	private List<Snp> snps = new ArrayList<Snp>();
	private List<Individual> individuals = new ArrayList<Individual>(); 
	private List<String> errors = new ArrayList<String>();
	private List<String> warnings = new ArrayList<String>();
	@Autowired
	private VarietyRepository varietyRepository;
	@Autowired
	private SequenceRepository sequenceRepository;
	@Autowired
	private IndividualRepository individualRepository;
	@Autowired
	private SnpRepository snpRepository;
	
	public SnpsImporterImpl() {
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.snps.SnpsImporter#getSnps()
	 */
	@Override
	public List<Snp> getSnps() {
		return snps;
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.snps.SnpsImporter#getIndividuals()
	 */
	@Override
	public List<Individual> getIndividuals() {
		return individuals;
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.snps.SnpsImporter#setIndividuals(java.util.List)
	 */
	@Override
	public void setIndividuals(List<Integer> ids) {
		for (Integer id:ids) {
			individuals.add(individualRepository.findOne(id));
		}
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.snps.SnpsImporter#getErrors()
	 */
	@Override
	public List<String> getErrors() {
		return errors;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.snps.SnpsImporter#getWarnings()
	 */
	@Override
	public List<String> getWarnings() {
		return warnings;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.snps.SnpsImporter#reset()
	 */
	@Override
	public void reset() {
		snps = new ArrayList<Snp>();
		individuals = new ArrayList<Individual>();
		errors = new ArrayList<String>();
		warnings = new ArrayList<String>();
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.snps.SnpsImporter#buildIndividuals(java.util.List, java.lang.String)
	 */
	@Override
	public void buildIndividuals(List<String> varieties, 
			String descr) {
		for (String v:varieties) {
			Variety var = varietyRepository.findByName(v).get(0);
			Individual i = new Individual();
			i.setVariety(var);
			i.setDescription(descr);
			individuals.add(i);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.snps.SnpsImporter#parseMipsSnps(java.util.List)
	 */
	@Override
	public void parseMipsSnps(List<String> lines) {
		System.out.println("Lines: " + lines.size());
		int lineN = 1;
		for (String line:lines) {
			String[] elems = line.split("\t+");
			String scaffoldName = elems[0];
			Sequence seq = sequenceRepository.findLatest(scaffoldName);
			if ( seq == null ) {
				errors.add(lineN + "\t" + line + "\t" + "Scaffold " + scaffoldName + " not found");
				continue;
			}
			int pos = Integer.parseInt(elems[1]);
			String referenceNucl = elems[2];
			for (int i = 3 ; i < elems.length; i++ ) {
				String reseq = elems[i];
				if (! reseq.equals("?")) {
					Snp snp = new Snp();
					snp.setIndividual(individuals.get(i - 3));
					snp.setPos(pos);
					snp.setSequence(seq);
					snp.setReference(referenceNucl);
					snp.setReseq(reseq);
					snps.add(snp);
				}
			}
			if ( lineN%1000 == 0 ) {
				System.out.println("Done " + lineN + " of " + lines.size());
			}
			lineN++;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.snps.SnpsImporter#save()
	 */
	@Override
	public void save() {
		for (Individual i:individuals) {
			individualRepository.save(i);
		}
		for (Snp snp:snps) {
			snpRepository.save(snp);
		}
	}
	
}

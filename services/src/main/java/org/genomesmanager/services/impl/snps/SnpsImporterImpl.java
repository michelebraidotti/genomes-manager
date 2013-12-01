package org.genomesmanager.services.impl.snps;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.Individual;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Snp;
import org.genomesmanager.domain.entities.Variety;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.sequences.SequenceRepoException;
import org.genomesmanager.repositories.snps.SnpRepository;
import org.genomesmanager.repositories.species.IndividualRepository;
import org.genomesmanager.repositories.species.VarietyRepository;
import org.genomesmanager.repositories.species.VarietyRepoException;
import org.genomesmanager.services.snp.SnpsImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("SnpsImporter")
@Scope("prototype")
public class SnpsImporterImpl implements SnpsImporter {
	private List<Snp> snps = new ArrayList<Snp>();
	private List<Individual> individuals = new ArrayList<Individual>(); 
	private List<String> errors = new ArrayList<String>();
	private List<String> warnings = new ArrayList<String>();
	@Autowired
	private VarietyRepository varietyRepo;
	@Autowired
	private SequenceRepo seqRepo;
	@Autowired
	private IndividualRepository indRepo;
	@Autowired
	private SnpRepository snpRepo;
	
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
			individuals.add(indRepo.get(id));
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
			String descr) throws VarietyRepoException {
		for (String v:varieties) {
			Variety var = varietyRepo.get(v);
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
	public void parseMipsSnps(List<String> lines) throws SequenceRepoException {		
		System.out.println("Lines: " + lines.size());
		int lineN = 1;
		for (String line:lines) {
			String[] elems = line.split("\t+");
			String scaffoldName = elems[0];
			Sequence seq = seqRepo.getLatest(scaffoldName);
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
			if ( i.getId() == null || i.getId() == 0 ) {
				indRepo.insert(i);
			}
		}
		for (Snp snp:snps) {
			if ( snp != null ) {
				if ( snp.getId() == 0 ) {
					snpRepo.insert(snp);
				}
				else {
					snpRepo.update(snp);
				}
			}
		}
	}
	
}

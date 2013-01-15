package org.genomesmanager.services.impl.genes;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.common.parsers.Gff3LineParser;
import org.genomesmanager.common.parsers.Gff3LineParserException;
import org.genomesmanager.domain.entities.Rna;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.repositories.genes.RnaRepo;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.sequences.SequenceRepoException;
import org.genomesmanager.services.genes.RnasImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("RnasImporter")
@Scope("prototype")
public class RnasImporterImpl implements RnasImporter {
	private List<Rna> rnas = new ArrayList<Rna>();
	private List<String> errors = new ArrayList<String>();
	private List<String> warnings = new ArrayList<String>();
	@Autowired
	private RnaRepo rnaRepo;
	@Autowired
	private SequenceRepo sequenceRepo;
	
    public RnasImporterImpl() {
    }
    
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.RnaImporter#getRnas()
	 */
	@Override
	public List<Rna> getRnas() {
		return rnas;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.RnaImporter#getErrors()
	 */
	@Override
	public List<String> getErrors() {
		return errors;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.RnaImporter#getWarnings()
	 */
	@Override
	public List<String> getWarnings() {
		return warnings;
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.RnaImporter#reset()
	 */
	@Override
	public void reset() {
		rnas = new ArrayList<Rna>();
		errors = new ArrayList<String>();
		warnings = new ArrayList<String>();
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.RnaImporter#parseMipsGenePredictionGff3(java.util.List)
	 */
	@Override
	public void parseMipsGenePredictionGff3(List<String> lines) {
    	int lineN = 0;
    	Sequence seq = null;
    	Gff3LineParser gff3 = new Gff3LineParser();
    	for ( String line: lines ) {
    		lineN++;
    		try {
				gff3.parse(line);
				if ( gff3.getType().contains("RNA") && ! gff3.getType().equals("mRNA") ) {
					if ( seq == null || ! seq.humanName().equals(gff3.getSeqId()) ) {
		    			seq = sequenceRepo.getLatest(gff3.getSeqId());
		    		}
					Rna rna = new Rna();
					rna.setSequence(seq);
					rna.setX(gff3.getStart());
		    		rna.setY(gff3.getEnd());
		    		rna.setStrandness(gff3.getStrand());
		    		rna.setName(gff3.getAttribId());
		    		rna.setRnaName(gff3.getAttributes().getProperty("Name"));
		    		rna.setType(gff3.getType());
		    		if ( gff3.getAttributes().getProperty("Parent") != null ) {
		    			String parent = gff3.getAttributes().getProperty("Parent");
		    			for (Rna r:rnas) {
		    				if ( r.getName().equals(parent) ) {
		    					rna.setParent(r);
		    				}
		    			}
		    			if (rna.getParent() == null) {
		    				warnings.add(lineN + "\t" + line + "\t" + "Can't find parent");
		    			}
		    		}
		    		rnas.add(rna);
	    		}
	    		else {
	    			// Skip line;
	    		}
    		}
			catch (Gff3LineParserException e) {
    			errors.add(lineN + "\t" + line + "\t" + "Gff3LineParserException: " + e.getMessage());
			} 
    		catch (SequenceRepoException e) {
    			errors.add(lineN + "\t" + line + "\t" + "Error while looking for sequence " + 
						gff3.getSeqId() + ": " + e.getMessage());
			}
    	}
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.RnaImporter#save()
	 */
	@Override
	public void save() {
		for (Rna r:rnas) {
			if ( r.getId() == 0 ) {
				rnaRepo.insert(r);
			}
			else {
				rnaRepo.update(r);
			}
		}
	}
 
    
}

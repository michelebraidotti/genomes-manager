package org.genomesmanager.services.impl.repeats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.genomesmanager.common.parsers.Gff3LineParser;
import org.genomesmanager.common.parsers.Gff3LineParserException;
import org.genomesmanager.domain.entities.*;
import org.genomesmanager.repositories.repeats.RepeatRepository;
import org.genomesmanager.repositories.repeats.RepeatsClassificationRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.services.repeats.RepeatsImporter;
import org.genomesmanager.services.repeats.RepeatsImporterException;
import org.genomesmanager.services.repeats.RepeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service("RepeatsImporter")
@Scope("prototype")
public class RepeatsImporterImpl implements RepeatsImporter {
	private List<Repeat> repeats = new ArrayList<Repeat>();
	private List<String> wrongLines = new ArrayList<String>();
	private List<String> warningLines = new ArrayList<String>();
	@Autowired
	private RepeatRepository repeatRepo;
	@Autowired
	private RepeatsClassificationRepository repeatsClassRepo;
	@Autowired
	private SequenceRepository sequenceRepo;
	@Autowired
	private RepeatsService repeatsService;

	
    @Override
	public void parseAgiGff3(List<String> lines) {
    	int lineN = 0;
    	Sequence seq = null;
    	Gff3LineParser gff3 = new Gff3LineParser();
    	for ( String line: lines ) {
    		lineN++;
    		try {
    			gff3.parse(line);
	    		Repeat rep;
	    		Properties props = gff3.getAttributes();			 
	    		if ( gff3.getAttribId() != null && ! gff3.getAttribId().equals("0") ) {
	    			rep = repeatRepo.findOne(Integer.parseInt(gff3.getAttribId()));
	    			if ( props.getProperty("family") != null && ! props.getProperty("family").equals("") ) {
	    				warningLines.add(lineN + "\t" + line + "\t" + "Family attrib will be ignored");
	    			}
	    			if ( props.getProperty("superfamily") != null && ! props.getProperty("superfamily").equals("") ) {
	    				warningLines.add(lineN + "\t" + line + "\t" + "Superfamily attrib will be ignored");
	    			}
	    			if ( props.getProperty("rorder") != null && ! props.getProperty("rorder").equals("") ) {
	    				warningLines.add(lineN + "\t" + line + "\t" + "Repeat order attrib (roder) will be ignored");
	    			}
	    		}
	    		else {

					RepeatsClassification repClass = null;
					try {
						repClass = RepeatsClassification.generate(gff3.getType(), gff3.getAttributesString());
					} catch (RepeatsClassificationException e) {
						e.printStackTrace();
					}
					rep = Repeat.getNew(repClass);
				}
        		if ( seq == null || ! seq.humanName().equals(gff3.getSeqId()) ) {
					try {
						seq = sequenceRepo.findLatest(gff3.getSeqId());
					}
					catch (NoResultException e) {
						wrongLines.add(lineN + "\t" + line + "\t" + "Error while looking for sequence " +
								gff3.getSeqId() + ": No sequence found.");
					}
        		}
	    		rep.setSequence(seq);
	    		rep.setX(gff3.getStart());
	    		rep.setY(gff3.getEnd());
	    		rep.setStrandness(gff3.getStrand());
	    		if ( gff3.getAttributes() != null && ! gff3.getAttributes().equals("")) {
	    			rep.setAttributes(props);
	    		}
	    		rep.validate();
	    		if ( rep.getId() == 0 ) {
		    		repeatRepo.validatePosition(rep);
    			}
	    		repeats.add(rep);
    		}
    		catch ( Gff3LineParserException | RepeatException | NumberFormatException | OutOfBoundsException | IntervalFeatureException e) {
    			wrongLines.add(lineN + "\t" + line + "\t" + e.getClass().getName() + ": " + e.getMessage());
			}
		}
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsImporter#getRepeatsSize()
	 */
	@Override
	public int getRepeatsSize() {
		return repeats.size();
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsImporter#getRepeatsList()
	 */
	@Override
	public List<Repeat> getRepeatsList() {
    	return repeats;
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsImporter#getWrongLines()
	 */
    @Override
	public List<String> getWrongLines() {
    	return wrongLines;
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsImporter#getWarningLines()
	 */
    @Override
	public List<String> getWarningLines() {
		return warningLines;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsImporter#reset()
	 */
	@Override
	public void reset() {
    	repeats = new ArrayList<Repeat>();
    	wrongLines = new ArrayList<String>();
    	warningLines = new ArrayList<String>();
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsImporter#saveList()
	 */
    @Override
	public void saveList() throws RepeatsImporterException {  	
    	for ( Repeat rep:repeats ) {
			try {
				repeatsClassRepo.save(rep.getRepeatsClassification());
				repeatsService.save(rep);
			} catch (RepeatException | OutOfBoundsException | IntervalFeatureException e) {
				throw new RepeatsImporterException(e.getClass().getName() + " " + e.getMessage());
			}
		}
    }
    
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsImporter#findDuplicates()
	 */
	@Override
	public List<String> findDuplicates() {
		HashMap<String, Integer> repCounter = new HashMap<String, Integer>(); 
		for ( Repeat rep:repeats) {
			String key = rep.getSequence().toString() + "-" + rep.getX() + "-" + rep.getY();
			if ( repCounter.get(key) == null ) {
				repCounter.put(key, 1);
			}
			else {
				repCounter.put(key, repCounter.get(key) + 1);
			}
		}
		List<String> duplicates = new ArrayList<String>();
		for(String key:repCounter.keySet()) {
			if ( repCounter.get(key) > 1 ) {
				duplicates.add(key);
			}
		}
		return duplicates;
	}
    
}

package org.genomesmanager.services.impl.repeats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.genomesmanager.common.parsers.Gff3LineParser;
import org.genomesmanager.common.parsers.Gff3LineParserException;
import org.genomesmanager.domain.entities.IntervalFeatureException;
import org.genomesmanager.domain.entities.OutOfBoundsException;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatException;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.repositories.repeats.RepeatRepo;
import org.genomesmanager.repositories.repeats.RepeatRepoException;
import org.genomesmanager.repositories.repeats.RepeatsClassificationRepo;
import org.genomesmanager.repositories.repeats.RepeatsClassificationRepoException;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.sequences.SequenceRepoException;
import org.genomesmanager.services.repeats.RepeatsImporter;
import org.genomesmanager.services.repeats.RepeatsImporterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("RepeatsImporter")
@Scope("prototype")
public class RepeatsImporterImpl implements RepeatsImporter {
	private List<Repeat> repeats = new ArrayList<Repeat>();
	private List<String> wrongLines = new ArrayList<String>();
	private List<String> warningLines = new ArrayList<String>();
	@Autowired
	private RepeatRepo repeatRepo;
	@Autowired
	private RepeatsClassificationRepo repeatsClassRepo;
	@Autowired
	private SequenceRepo sequenceRepo;

	

    public RepeatsImporterImpl() {}
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsImporter#parseAgiGff3(java.util.List)
	 */
    @Override
	public void parseAgiGff3(List<String> lines) {
    	int lineN = 0;
    	Sequence seq = null;
    	Gff3LineParser gff3 = new Gff3LineParser();
    	for ( String line: lines ) {
    		lineN++;
    		try {
    			gff3.parse(line);
	    		Repeat rep = new Repeat();
	    		Properties props = gff3.getAttributes();			 
	    		if ( gff3.getAttribId() != null && ! gff3.getAttribId().equals("0") ) {
	    			rep = repeatRepo.get(Integer.parseInt(gff3.getAttribId()));
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
	    			RepeatsClassification repClass = repeatsClassRepo.generate(gff3.getType(), gff3.getAttributesString());
					rep = repeatRepo.getNew(repClass);
				}
        		if ( seq == null || ! seq.humanName().equals(gff3.getSeqId()) ) {
        			seq = sequenceRepo.getLatest(gff3.getSeqId());
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
    		catch (Gff3LineParserException e) {
    			wrongLines.add(lineN + "\t" + line + "\t" + "Gff3LineParserException: " + e.getMessage());
			}
    		catch (RepeatRepoException e) {
				wrongLines.add(lineN + "\t" + line + "\t" + "RepeatEAException: " + e.getMessage());
			} 
			catch (RepeatsClassificationRepoException e) {
				wrongLines.add(lineN + "\t" + line + "\t" + "RepeatsClassificationRepoException: " +e.getMessage());
			}
			catch (NumberFormatException e) {
				wrongLines.add(lineN + "\t" + line + "\t" + "NumberFormatException: " + e.getMessage());
			} 
			catch (OutOfBoundsException e) {
				wrongLines.add(lineN + "\t" + line + "\t" + "OutOfBoundsException: " + e.getMessage());	    				
			}
    		catch ( IntervalFeatureException ex ) {
    			wrongLines.add(lineN + "\t" + line + "\t" + "IntervalFeatureException: " + ex.getMessage());
    		}
    		catch (RepeatException e) {
				wrongLines.add(lineN + "\t" + line + "\t" + "RepeatException: " + e.getMessage());
			} 
    		catch (SequenceRepoException e) {
				wrongLines.add(lineN + "\t" + line + "\t" + "Error while looking for sequence " + 
						gff3.getSeqId() + ": " + e.getMessage() + " - " + e.getMessage());
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
    		if ( rep.getId() != 0 ) {
    			try {
					repeatRepo.update(rep);
				} 
    			catch (RepeatRepoException e) {
	    			throw new RepeatsImporterException("RepeatEAException " + e.getMessage());
				}
    		}
    		else {
	    		try {
	    			repeatsClassRepo.insertIfNotExists(rep.getRepeatsClassification());
	    			repeatRepo.insert(rep);
	    		}
	    		catch (RepeatRepoException e) {
	    			throw new RepeatsImporterException("RepeatEAException " + e.getMessage());
	    		} 
	    		catch (RepeatsClassificationRepoException e) {
	    			throw new RepeatsImporterException("RepeatsClassificationException " + 
	    					e.getMessage());
				}
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

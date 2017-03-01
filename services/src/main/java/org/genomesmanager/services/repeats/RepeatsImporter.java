package org.genomesmanager.services.repeats;

import org.genomesmanager.bioprograms.parsers.Gff3LineParser;
import org.genomesmanager.bioprograms.parsers.Gff3LineParserException;
import org.genomesmanager.domain.entities.*;
import org.genomesmanager.repositories.repeats.RepeatRepository;
import org.genomesmanager.repositories.repeats.RepeatsClassificationRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

@Service("RepeatsImporter")
@Scope("prototype")
public class RepeatsImporter {
	private List<Repeat> repeats = new ArrayList<Repeat>();
	private List<String> wrongLines = new ArrayList<String>();
	private List<String> warningLines = new ArrayList<String>();
	@Autowired
	private RepeatRepository repeatRepository;
	@Autowired
	private RepeatsClassificationRepository repeatsClassificationRepository;
	@Autowired
	private SequenceRepository sequenceRepository;
	@Autowired
	private RepeatsService repeatsService;


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
	    			rep = repeatRepository.findOne(Integer.parseInt(gff3.getAttribId()));
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

					RepeatsClassification repClass = RepeatsClassification.generate(gff3.getType(), gff3.getAttributesString());
					rep = Repeat.getNew(repClass);
				}
        		if ( seq == null || ! seq.humanName().equals(gff3.getSeqId()) ) {
					try {
						seq = sequenceRepository.findLatest(gff3.getSeqId());
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
		    		repeatRepository.validatePosition(rep);
    			}
	    		repeats.add(rep);
    		}
    		catch ( RepeatsClassificationException | Gff3LineParserException | RepeatException | NumberFormatException | OutOfBoundsException | IntervalFeatureException e) {
    			wrongLines.add(lineN + "\t" + line + "\t" + e.getClass().getName() + ": " + e.getMessage());
			}
		}
    }

	public int getRepeatsSize() {
		return repeats.size();
	}

	public List<Repeat> getRepeatsList() {
    	return repeats;
    }
    
    public List<String> getWrongLines() {
    	return wrongLines;
    }
    
    public List<String> getWarningLines() {
		return warningLines;
	}

	public void reset() {
    	repeats = new ArrayList<Repeat>();
    	wrongLines = new ArrayList<String>();
    	warningLines = new ArrayList<String>();
    }
    
    public void saveList() throws RepeatsImporterException {
    	for ( Repeat rep:repeats ) {
			try {
				repeatsClassificationRepository.save(rep.getRepeatsClassification());
				repeatsService.save(rep);
			} catch (RepeatException | OutOfBoundsException | IntervalFeatureException e) {
				throw new RepeatsImporterException(e.getClass().getName() + " " + e.getMessage());
			}
		}
    }
    
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

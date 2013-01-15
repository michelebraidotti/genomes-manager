package org.genomesmanager.services.impl.genes;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.common.formats.AgiExportType;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Rna;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.genes.RnasList;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.sequences.SequenceRepoException;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.genomesmanager.services.genes.RnasExporter;
import org.genomesmanager.services.genes.RnasExporterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("RnasExporter")
@Scope("prototype")
public class RnasExporterImpl implements RnasExporter  {
    private List<Rna> rnas;
    private List<String> fileContent = null;
    @Autowired
    private RnasList rnasList;	
    @Autowired
	private SpeciesRepo speciesRepo;
    @Autowired
	private SequenceRepo seqRepo;
    
    public RnasExporterImpl() {
    }
    
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.RnasExporter#getFileContent()
	 */
	@Override
	public List<String> getFileContent() {
		return fileContent;
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.RnasExporter#setRnasList(org.genomesmanager.domain.entities.Chromosome)
	 */
	@Override
	public void setRnasList(Chromosome chr) {
		rnas = rnasList.getAllByChromosome(chr.getId());
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.RnasExporter#setRnasList(org.genomesmanager.domain.entities.Species)
	 */
	@Override
	public void setRnasList(Species sp) {
		rnas = rnasList.getAllBySpecies(sp.getId());
		
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.RnasExporter#setRepeatsListBySpecies(java.lang.String)
	 */
	@Override
	public void setRnasList(String speciesDefinition) throws SpeciesRepoException {
		Species sp = speciesRepo.get(speciesDefinition);
		rnas = rnasList.getAllBySpecies(sp.getId());
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.RnasExporter#setFileContent(org.genomesmanager.common.formats.AgiExportType, java.lang.Boolean)
	 */
	@Override
	public void setFileContent(AgiExportType expType, 
			Boolean usingPseudomolCoordinates) throws RnasExporterException {
		if ( expType == null ) {
    		throw new RnasExporterException("No export type defined");
    	}
		if ( rnas == null || rnas.size() == 0 ) { 
			throw new RnasExporterException("No genes found");
		}
		
    	if (expType.equals(AgiExportType.FASTA)) {
    		throw new RnasExporterException("Not yet implemented");
    	}
    	else if (expType.equals(AgiExportType.GFF3)) {
    		exportGff3(rnas, false, usingPseudomolCoordinates);
    	}
    	else if (expType.equals(AgiExportType.GFF3PLUS)) {
    		exportGff3(rnas, true, usingPseudomolCoordinates);
    	}
    	else {
    		throw new RnasExporterException("Export type " + expType.getLabel() + 
    				" not found");
    	}
	}


	private void exportGff3(List<Rna> rnas, boolean extraInfo,
			Boolean usingPseudomolCoordinates) {
		fileContent = new ArrayList<String>();
    	fileContent.add("##gff-version 3\n");
    	for (Rna rna:rnas) {
    		if ( extraInfo && usingPseudomolCoordinates ) {
				try {
					Scaffold s = seqRepo.getScaffold(rna.getSequence());
					fileContent.add(
						rna.toGff3WithPseudomolCoordinatesLine(s.getPseudomolecule().getName(), s.getPseudomolOffset()) + 
						rna.extraAnnot() + "\n");
				} 
				catch (SequenceRepoException e) {
					e.printStackTrace();
				}
    		}
    		else if ( ! extraInfo && usingPseudomolCoordinates ) {
				try {
					Scaffold s = seqRepo.getScaffold(rna.getSequence());
					fileContent.add(
    					rna.toGff3WithPseudomolCoordinatesLine(s.getPseudomolecule().getName(), s.getPseudomolOffset()) + "\n");
				} 
				catch (SequenceRepoException e) {
					e.printStackTrace();
				}
    		}
    		else if ( extraInfo && ! usingPseudomolCoordinates ) {
    			fileContent.add(rna.toGff3Line() + rna.extraAnnot() + "\n");
    		}
    		else if ( ! extraInfo && ! usingPseudomolCoordinates ) {
    			fileContent.add(rna.toGff3Line() + "\n");
    		}
    		
    	}
	}
	
}

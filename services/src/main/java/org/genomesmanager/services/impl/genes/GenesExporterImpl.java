package org.genomesmanager.services.impl.genes;

import org.genomesmanager.common.formats.AgiExportType;
import org.genomesmanager.domain.dtos.CannotParseSpeciesDefinitionException;
import org.genomesmanager.domain.entities.*;
import org.genomesmanager.repositories.genes.GeneRepository;
import org.genomesmanager.repositories.sequences.ScaffoldRepository;
import org.genomesmanager.services.genes.GenesExporter;
import org.genomesmanager.services.genes.GenesExporterException;
import org.genomesmanager.services.species.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("GenesExporter")
@Scope("prototype")
public class GenesExporterImpl implements GenesExporter {
	@Autowired
	private GeneRepository geneRepository;
	@Autowired
	private SpeciesService speciesService;
	@Autowired
	private ScaffoldRepository scaffoldRepository;
    private List<Gene> genes;
    private List<String> fileContent = null;

	public GenesExporterImpl() {
    }
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.GenesExporter#getFileContent()
	 */
	@Override
	public List<String> getFileContent() {
		return fileContent;
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.GenesExporter#setGenesList(org.genomesmanager.domain.entities.Chromosome)
	 */
	@Override
	public void setGenesList(Chromosome chr) {
		genes = geneRepository.findBySequenceChromosome(chr);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.GenesExporter#setGenesList(org.genomesmanager.domain.entities.Species)
	 */
	@Override
	public void setGenesList(Species sp) {
		genes = geneRepository.findBySequenceChromosomeSpecies(sp);
		
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.GenesExporter#loadRepeatsListBySpecies(java.lang.String)
	 */
	@Override
	public void setGenesList(String speciesDefinition) throws CannotParseSpeciesDefinitionException {
		Species sp = speciesService.get(speciesDefinition);
		genes = geneRepository.findBySequenceChromosomeSpecies(sp);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.GenesExporter#setFileContent(org.genomesmanager.common.formats.AgiExportType, java.lang.Boolean)
	 */
	@Override
	public void setFileContent(AgiExportType expType, 
			Boolean usingPseudomolCoordinates) throws GenesExporterException {
		if ( expType == null ) {
    		throw new GenesExporterException("No export type defined");
    	}
		if ( genes == null || genes.size() == 0 ) { 
			throw new GenesExporterException("No genes found");
		}
    	if (expType.equals(AgiExportType.FASTA)) {
    		throw new GenesExporterException("Not yet implemented");
//    		try {
//				exportFasta(genes);
//			} 
//    		catch (SequenceSliceException e) {
//				throw new GenesExporterException("SequenceSlicerException" + 
//						e.getMessage());
//			}
    	}
    	else if (expType.equals(AgiExportType.GFF3)) {
    		exportGff3(genes, false, usingPseudomolCoordinates);
    	}
    	else if (expType.equals(AgiExportType.GFF3PLUS)) {
    		exportGff3(genes, true, usingPseudomolCoordinates);
    	}
    	else {
    		throw new GenesExporterException("Export type " + expType.getLabel() + 
    				" not found");
    	}
	}


	private void exportGff3(List<Gene> genes, boolean extraInfo,
			Boolean usingPseudomolCoordinates) {
		fileContent = new ArrayList<String>();
    	fileContent.add("##gff-version 3\n");
    	for (Gene gene:genes) {
    		if ( extraInfo && usingPseudomolCoordinates ) {

				Scaffold s = scaffoldRepository.findOne(gene.getSequence().getId());
				fileContent.add(
					gene.toGff3WithPseudomolCoordinatesLine(s.getPseudomolecule().getName(), s.getPseudomolOffset()) +
					gene.extraAnnot() + "\n");
				addExonsGff3(gene, extraInfo, s);
    		}
    		else if ( ! extraInfo && usingPseudomolCoordinates ) {

				Scaffold s = scaffoldRepository.findOne(gene.getSequence().getId());
				fileContent.add(
					gene.toGff3WithPseudomolCoordinatesLine(s.getPseudomolecule().getName(), s.getPseudomolOffset()) + "\n");
				addExonsGff3(gene, extraInfo, s);
    		}
    		else if ( extraInfo && ! usingPseudomolCoordinates ) {
    			fileContent.add(gene.toGff3Line() + gene.extraAnnot() + "\n");
    			addExonsGff3(gene, extraInfo);
    		}
    		else if ( ! extraInfo && ! usingPseudomolCoordinates ) {
    			fileContent.add(gene.toGff3Line() + "\n");
    			addExonsGff3(gene, extraInfo);
    		}
    		
    	}
	}


	private void addExonsGff3(Gene gene, boolean extraInfo) {
		for (Mrna mrna:gene.getMrnas()) {
			if ( extraInfo ) {
				fileContent.add(mrna.toGff3Line() + mrna.extraAnnot() + "\n");
			}
			else {
				fileContent.add(mrna.toGff3Line() + "\n");
			}
			for (Exon exon:mrna.getExons() ) {
				if ( extraInfo ) {
					fileContent.add(exon.toGff3Line() + exon.extraAnnot() + "\n");
	    		}
	    		else {
	    			fileContent.add(exon.toGff3Line() + "\n");
	    		}
			}
		}
	}
	
	private void addExonsGff3(Gene gene, boolean extraInfo, Scaffold s) {
		for (Mrna mrna:gene.getMrnas()) {
			if ( extraInfo ) {
				fileContent.add(mrna.toGff3WithPseudomolCoordinatesLine(s.getPseudomolecule().getName(), s.getPseudomolOffset()) + 
						mrna.extraAnnot() + "\n");
			}
			else {
				fileContent.add(mrna.toGff3WithPseudomolCoordinatesLine(s.getPseudomolecule().getName(), s.getPseudomolOffset()) + "\n");
			}
			for (Exon exon:mrna.getExons() ) {
				if ( extraInfo ) {
					fileContent.add(
							exon.toGff3WithPseudomolCoordinatesLine(s.getPseudomolecule().getName(), s.getPseudomolOffset()) + 
							exon.extraAnnot() + "\n");
	    		}
	    		else {
	    			fileContent.add(
	    					exon.toGff3WithPseudomolCoordinatesLine(s.getPseudomolecule().getName(), s.getPseudomolOffset()) + "\n");
	    		}
			}
		}
	}
	
}

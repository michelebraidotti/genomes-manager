package org.genomesmanager.services.impl.genes;

import org.genomesmanager.bioprograms.parsers.Gff3LineParser;
import org.genomesmanager.bioprograms.parsers.Gff3LineParserException;
import org.genomesmanager.domain.entities.Exon;
import org.genomesmanager.domain.entities.Gene;
import org.genomesmanager.domain.entities.Mrna;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.repositories.genes.GeneRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.services.genes.GenesImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("GenesImporter")
@Scope("prototype")
public class GenesImporterImpl implements GenesImporter {
	private List<Gene> genes = new ArrayList<Gene>();
	private List<String> errors = new ArrayList<String>();
	private List<String> warnings = new ArrayList<String>();
	@Autowired
	private SequenceRepository sequenceRepository;
	@Autowired
	private GeneRepository geneRepo;

	public GenesImporterImpl() {
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.GenesImporter#getGenes()
	 */
	@Override
	public List<Gene> getGenes() {
		return genes;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.GenesImporter#getErrors()
	 */
	@Override
	public List<String> getErrors() {
		return errors;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.GenesImporter#getWarnings()
	 */
	@Override
	public List<String> getWarnings() {
		return warnings;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.GenesImporter#reset()
	 */
	@Override
	public void reset() {
		genes = new ArrayList<Gene>();
		errors = new ArrayList<String>();
		warnings = new ArrayList<String>();
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.GenesImporter#parseMipsGenePredictionGff3(java.util.List)
	 */
	@Override
	public void parseMipsGenePredictionGff3(List<String> lines) {
		int lineN = 0;
		Sequence seq = null;
		Gff3LineParser gff3 = new Gff3LineParser();
		for (String line : lines) {
			lineN++;
			try {
				gff3.parse(line);
				if (gff3.getType().equals("gene")) {
					if (seq == null || !seq.humanName().equals(gff3.getSeqId())) {
						seq = sequenceRepository.findLatest(gff3.getSeqId());
					}
					if ( seq == null) {
						errors.add(lineN + "\t" + line + "\t"
								+ "Error while looking for sequence '" + gff3.getSeqId() + "', sequence not found.");
					}
					else {
						Gene gene = new Gene();
						gene.setSequence(seq);
						gene.setX(gff3.getStart());
						gene.setY(gff3.getEnd());
						gene.setStrandness(gff3.getStrand());
						gene.setName(gff3.getAttribId());
						genes.add(gene);
					}
				}
				else if (gff3.getType().equals("mRNA")) {
					Mrna mrna = new Mrna();
					mrna.setX(gff3.getStart());
					mrna.setY(gff3.getEnd());
					mrna.setStrandness(gff3.getStrand());
					mrna.setName(gff3.getAttribId());
					if ( gff3.getAttributes().getProperty("Description") != null ) {
						mrna.setDescription(gff3.getAttributes().getProperty("Description"));
					}
					for (Gene g : genes) {
						if (g.getName().equals(gff3.getAttribParent())) {
							mrna.setGene(g);
							if (g.getMrnas() == null) {
								g.setMrnas(new ArrayList<Mrna>());
							}
							g.getMrnas().add(mrna);
							break;
						}
					}
				}
				else if (gff3.getType().equals("exon")) {
					Exon exon = new Exon();
					exon.setX(gff3.getStart());
					exon.setY(gff3.getEnd());
					exon.setStrandness(gff3.getStrand());
					exon.setName(gff3.getAttribId());
					for (Gene g : genes) {
						for (Mrna m:g.getMrnas()) {
							if (m.getName().equals(gff3.getAttribParent())) {
								exon.setMrna(m);
								if (m.getExons() == null) {
									m.setExons(new ArrayList<Exon>());
								}
								m.getExons().add(exon);
								break;
							}
						}
					}

				} 
				else {
					warnings.add(lineN + "\t" + line + "\t"
							+ "Line skipped: unrecognized gff3 type.");
				}
			} 
			catch (Gff3LineParserException e) {
				errors.add(lineN + "\t" + line + "\t"
						+ "Gff3LineParserException: " + e.getMessage());
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.genes.GenesImporter#save()
	 */
	@Override
	public void save() {
		for (Gene g : genes) {
			geneRepo.save(g);
		}
	}

}

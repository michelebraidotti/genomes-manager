package org.genomesmanager.services.sequences;

import org.genomesmanager.formats.ScaffoldInfo;
import org.genomesmanager.formats.ScaffoldInfoException;
import org.genomesmanager.formats.SimpleFasta;
import org.genomesmanager.parsers.FastaLinesToSimpleFasta;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.ScaffoldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("ScaffoldsImporter")
@Scope("prototype")
@Transactional
public class ScaffoldsImporter {
	@Autowired
	private ChromosomeRepository chromosomeRepository;
	@Autowired
	private ScaffoldRepository scaffoldRepository;

	private List<Scaffold> scaffolds = new ArrayList<Scaffold>();
	private List<String> wrongLines = new ArrayList<String>();
	private List<String> warningLines = new ArrayList<String>();

	public void importScaffoldsWithInfo(List<ScaffoldInfo> scaffoldsinfo, List<SimpleFasta> fastas, Species sp)
			throws ScaffoldsImporterException {
		reset();
		HashMap<String, ScaffoldInfo> scaffsPosition = new HashMap<String, ScaffoldInfo>();
		for (ScaffoldInfo info : scaffoldsinfo)
			scaffsPosition.put(info.getName(), info);
		for (SimpleFasta fasta : fastas) {
			ScaffoldInfo info = scaffsPosition.get(fasta.getId());
			if (info != null) {
				Scaffold s = new Scaffold();
				Chromosome chr = chromosomeRepository.findByChromosomeNumberAndSpecies(info.getChr(), sp);
				if (chr == null) {
					warningLines.add("Chromosome " + info.getChr() + ", "
							+ sp.toString() + " not found");
					continue;
				}
				s.setName(info.getName());
				s.setSequenceText(fasta.getSequence());
				s.setLength(fasta.getSequence().length());
				s.setOrder(info.getOrder());
				if (info.getOrder() == 0) {
					s.setIsUnplaced(true);
				} else {
					s.setIsUnplaced(false);
				}
				s.setChromosome(chr);
				s.setVersion("1");
				if (isDuplicated(s)) {
					wrongLines.add(fasta.getId() + "\tDuplicated");
				} else {
					// System.out.println("Importing " + s.getName());
					if (s.getLength() == 0) {
						warningLines.add(fasta.getId()
								+ "\tSequence length is 0");
					}
					scaffolds.add(s);
				}
			} else {
				wrongLines.add("Fasta id " + fasta.getId()
						+ " not found in manifest");
			}
		}
		
	}

	public void importScaffolds(List<String> manifest, List<String> fastaLines,
			Species sp) throws ScaffoldsImporterException {
		List<SimpleFasta> fastas = FastaLinesToSimpleFasta.GetSimpleFastas(fastaLines);
		List<ScaffoldInfo> infos = new ArrayList<ScaffoldInfo>();
		for (String line : manifest) {
			try {
				infos.add(new ScaffoldInfo(line));
			} catch (ScaffoldInfoException e) {
				// swallow it
			}
		}
		importScaffoldsWithInfo(infos, fastas, sp);
	}

	public void importScaffolds(int chrId, String version,
			List<String> fastaLines) throws ScaffoldsImporterException {
		Chromosome chr;
		chr = chromosomeRepository.findOne(chrId);
		List<SimpleFasta> fastas = FastaLinesToSimpleFasta
				.GetSimpleFastas(fastaLines);
		for (SimpleFasta fasta : fastas) {
			Scaffold s = null;
			try {
				s = fastaToScaffold(fasta.getId(), fasta.getSequence());
				if (s == null) {
					wrongLines.add(fasta.getId()
							+ "\tUnmanaged error, scaffold is null");
				} else {
					s.setChromosome(chr);
					s.setVersion(version);
					if (isDuplicated(s)) {
						wrongLines.add(fasta.getId() + "\tDuplicated");
					} else {
						// System.out.println("Importing " + s.getName());
						if (s.getLength() == 0) {
							warningLines.add(fasta.getId()
									+ "\tSequence length is 0");
						}
						scaffolds.add(s);
					}
				}
			} catch (ScaffoldsImporterException e) {
				wrongLines.add(fasta.getId() + "\t" + e.getMessage());
			}
			// TODO create the virtual pseudomol for the given
			// chr if not exists
		}
	}

	private boolean isDuplicated(Scaffold s) {
		List<Scaffold> existing = scaffoldRepository.findByName(s.descr());
		if ( existing == null ) {
			return false;
		}
		if ( existing.size() == 0) {
			return false;
		}
		return existing.get(0).equals(s);

	}

	public void reset() {
		scaffolds = new ArrayList<>();
		wrongLines = new ArrayList<>();
		warningLines = new ArrayList<>();
	}

	public int countScaffolds() {
		return scaffolds.size();
	}

	public List<Scaffold> getScaffolds() {
		return scaffolds;
	}

	public List<String> getWrongLines() {
		return wrongLines;
	}

	public List<String> getWarningLines() {
		return warningLines;
	}

	public void save() throws ScaffoldsImporterException {
		if (scaffolds.size() > 0) {
			for (Scaffold s : scaffolds) {
				scaffoldRepository.save(s);
			}
		}
	}

	private Scaffold fastaToScaffold(String id, String sequence)
			throws ScaffoldsImporterException {
		// Program recognize lines like these
		// >Oglab10_0016 O. glaberrima chr10 anchored Scaffold0016 derived from ...
		//   ^^^     ^^^
		//   |||      |||
		// splitId[0] splitId[1]
		// >Oglab10_unplaced030 O. glaberrima unanchored scaffold derived from ...
		//   ^^^        ^^^
		//   |||        |||
		// SplitName[0] SplitName[1]
		Scaffold scaf = new Scaffold();
		String[] splitId = id.split("\\s+");
		String[] splitName = splitId[0].split("\\_");
		int order = 0;
		if (splitName.length != 2) {
			throw new ScaffoldsImporterException("Error parsing scaffold name");
		}
		if (splitName.length > 1 && !splitName[1].contains("unplaced")) {
			try {
				order = Integer.parseInt(splitName[1]);
			} catch (NumberFormatException e) {
				throw new ScaffoldsImporterException(
						"Error parsing scaffold order '" + splitName[1] + "'");
			}
		}
		String name = splitId[0].replace(">", "");
		scaf.setSequenceText(sequence);
		scaf.setLength(sequence.length());
		scaf.setName(name);
		scaf.setOrder(order);
		return scaf;
	}

}

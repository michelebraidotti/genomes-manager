package org.genomesmanager.services.impl.sequences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.genomesmanager.common.formats.ScaffoldInfo;
import org.genomesmanager.common.formats.SimpleFasta;
import org.genomesmanager.common.parsers.FastaLinesToSimpleFasta;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.sequences.ChromosomeRepo;
import org.genomesmanager.repositories.sequences.ChromosomeRepoException;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.sequences.SequenceRepoException;
import org.genomesmanager.services.sequences.ScaffoldsImporter;
import org.genomesmanager.services.sequences.ScaffoldsImporterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("ScaffoldsImporter")
@Scope("prototype")
public class ScaffoldsImporterImpl implements ScaffoldsImporter {
	@Autowired
	private ChromosomeRepo chromosomeRepo;
	@Autowired
	private SequenceRepo sequenceRepo;
	private List<Scaffold> scaffolds = new ArrayList<Scaffold>();
	private List<String> wrongLines = new ArrayList<String>();
	private List<String> warningLines = new ArrayList<String>();

	public ScaffoldsImporterImpl() {
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.ScaffoldsImporter#importScaffolds(java.util.List, java.util.List, org.genomesmanager.domain.entities.Species)
	 */
	@Override
	public void importScaffolds(List<String> manifest, List<String> fastaLines,
			Species sp) throws ScaffoldsImporterException {
		List<SimpleFasta> fastas = FastaLinesToSimpleFasta.GetSimpleFastas(fastaLines);
		HashMap<String, ScaffoldInfo> scaffsPosition = new HashMap<String, ScaffoldInfo>();
		for (String line : manifest) {
			ScaffoldInfo info = new ScaffoldInfo(line);
			scaffsPosition.put(info.getName(), info);
		}
		for (SimpleFasta fasta : fastas) {
			ScaffoldInfo info = scaffsPosition.get(fasta.getId());
			if (info != null) {
				Scaffold s = new Scaffold();
				Chromosome chr = chromosomeRepo.get(info.getChr(), sp);
				if (chr == null) {
					warningLines.add("Chromosome " + info.getChr() + ", "
							+ sp.toString() + " not found");
					continue;
				}
				s.setName(info.getName());
				s.setSequence(fasta.getSequence());
				s.setLength(fasta.getSequence().length());
				s.setOrder(info.getOrder());
				if (info.getOrder() == 0) {
					s.setIsUnplaced(true);
				} else {
					s.setIsUnplaced(false);
				}
				s.setChromosome(chr);
				s.setScaffVersion("1");
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

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.ScaffoldsImporter#importScaffolds(int, java.lang.String, java.util.List)
	 */
	@Override
	public void importScaffolds(int chrId, String version,
			List<String> fastaLines) throws ScaffoldsImporterException {
		Chromosome chr;
		try {
			chr = chromosomeRepo.get(chrId);
		} catch (ChromosomeRepoException e) {
			throw new ScaffoldsImporterException(e.getMessage());
		}
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
					s.setScaffVersion(version);
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
		Scaffold current = null;
		try {
			current = (Scaffold) sequenceRepo.get(s.toString());
			return current.equals(s);
		} catch (SequenceRepoException e) {
			System.out.println("isDuplicated analisis: " + e.getMessage() + " (which should be fine)");
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.ScaffoldsImporter#reset()
	 */
	@Override
	public void reset() {
		scaffolds = new ArrayList<Scaffold>();
		wrongLines = new ArrayList<String>();
		warningLines = new ArrayList<String>();
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.ScaffoldsImporter#countScaffolds()
	 */
	@Override
	public int countScaffolds() {
		return scaffolds.size();
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.ScaffoldsImporter#getScaffolds()
	 */
	@Override
	public List<Scaffold> getScaffolds() {
		return scaffolds;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.ScaffoldsImporter#getWrongLines()
	 */
	@Override
	public List<String> getWrongLines() {
		return wrongLines;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.ScaffoldsImporter#getWarningLines()
	 */
	@Override
	public List<String> getWarningLines() {
		return warningLines;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.sequences.ScaffoldsImporter#save()
	 */
	@Override
	public void save() throws ScaffoldsImporterException {
		if (scaffolds.size() > 0) {
			for (Scaffold s : scaffolds) {
				sequenceRepo.insert(s);
			}
		}
	}

	private Scaffold fastaToScaffold(String id, String sequence)
			throws ScaffoldsImporterException {
		// Program recognize lines like these
		// >Oglab10_0016 O. glaberrima chr10 anchored Scaffold0016 derived from ...
		// ^^^ ^^^
		// ||| |||
		// splitId[0] splitId[1]
		// >Oglab10_unplaced030 O. glaberrima unanchored scaffold derived from ...
		// ^^^ ^^^
		// ||| |||
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
		scaf.setSequence(sequence);
		scaf.setLength(sequence.length());
		scaf.setName(name);
		scaf.setOrder(order);
		return scaf;
	}

}

package org.genomesmanager.repositories.repeats;

import java.util.List;

import org.genomesmanager.domain.entities.LtrRepeat;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsOrder;
import org.genomesmanager.domain.entities.Species;

public interface RepeatRepositoryCustom {

	public abstract List<Repeat> getAllBySequence(int seqId);

	public abstract List<Repeat> getAllBySequence(int seqId,
			RepeatsOrder repOrder);

	public abstract List<Repeat> getAllByChromosome(int chrId,
			RepeatsOrder repOrder);

	public abstract List<Repeat> getAllBySequence(int seqId,
			RepeatsClassification repClass);

	public abstract List<Repeat> getAllInRange(int seqId, int start, int end);

	public abstract List<LtrRepeat> getAllLtr(int seqId);

	public abstract List<LtrRepeat> getAllLtrInRange(int seqId, int start,
			int end);

	public abstract List<Repeat> getAllBySequence(int seqId,
			RepeatsOrder repType, String superFamily);

	public abstract List<Repeat> getAllBySpecies(Species sp);

	public abstract List<Repeat> getAllByChromosome(int chrId);

	public abstract List<Repeat> getAllByChromosome(int chrId,
			RepeatsClassification repClass);

	public abstract List<Repeat> getAllByChromosome(int chrId,
			RepeatsOrder repOrd, String superFamily);
}
